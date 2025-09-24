package net.seentro.prehistoriccraft.common.nature.dawnRedwood;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class DawnRedwoodSaplingBlock extends FlowerBlock implements BonemealableBlock, EntityBlock {
    public static final MapCodec<DawnRedwoodSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(
            blockInstance -> blockInstance.group(
                            EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects), propertiesCodec())
                    .apply(blockInstance, DawnRedwoodSaplingBlock::new));

    protected static final float AABB_OFFSET = 3.0F;
    public static final IntegerProperty STAGES = IntegerProperty.create("stages", 1, 3);
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final BooleanProperty TWO_BY_TWO = BooleanProperty.create("two_by_two");

    public DawnRedwoodSaplingBlock(SuspiciousStewEffects effects, Properties properties) {
        super(effects, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(STAGES, 1).setValue(INVISIBLE, false).setValue(TWO_BY_TWO, false));
    }

    private static final VoxelShape STAGE_1 = Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0);
    private static final VoxelShape INVISIBLE_AND_OTHER_STAGES = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);

        if (state.getValue(STAGES).equals(1)) {
            return STAGE_1.move(offset.x, offset.y, offset.z);
        }

        return INVISIBLE_AND_OTHER_STAGES.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGES, INVISIBLE, TWO_BY_TWO);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (state.getValue(INVISIBLE)) return;

        if (random.nextInt(30) == 0) {
            this.advanceTree(level, pos, state, random);
        }
    }

    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        int stage = state.getValue(STAGES);

        if (stage >= 3) {
            StructurePlaceSettings placeSettings = new StructurePlaceSettings();
            placeTree(state.getValue(TWO_BY_TWO), placeSettings, level, pos, random);

            return;
        }

        int nextStage = Math.min(stage + 1, 3);
        int required = requiredInvisibleForStage(nextStage);

        for (int i = 1; i <= required; i++) {
            BlockPos check = pos.above(i);
            BlockState checkState = level.getBlockState(check);

            if (checkState.isAir()) continue;

            if (checkState.getBlock() == this && checkState.getValue(INVISIBLE)) continue;

            return;
        }

        boolean twoByTwoSapling = false;
        if (level.getBlockState(pos).getValue(TWO_BY_TWO)) {
            twoByTwoSapling = true;
        } else if (stage == 1) {
            for (int i = 0; i >= -1; i--) {
                for (int j = 0; j >= -1; j--) {
                    if (twoByTwoSapling) break;

                    if (isTwoByTwoSapling(state, level, pos, i, j)) {
                        destroyTwoByTwoSaplings(level, pos, i, j);
                        twoByTwoSapling = true;
                    }
                }
            }
        }

        BlockState newBase = twoByTwoSapling ? state.setValue(STAGES, nextStage).setValue(TWO_BY_TWO, true) : state.setValue(STAGES, nextStage);
        BlockState invisibleState = newBase.setValue(INVISIBLE, true);

        for (int i = required; i >= 1; i--) {
            BlockPos target = pos.above(i);
            BlockState cur = level.getBlockState(target);

            if (cur.getBlock() == this && cur.getValue(INVISIBLE) && cur.getValue(STAGES) == nextStage) {
                continue;
            }

            level.setBlock(target, invisibleState, Block.UPDATE_CLIENTS);
        }

        level.setBlock(pos, newBase, Block.UPDATE_ALL);
    }

    protected boolean mayPlaceOn(BlockState state) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
    }

    private void placeTree(boolean isTwoByTwoSapling, StructurePlaceSettings placeSettings, ServerLevel level, BlockPos pos, RandomSource random) {
        StructureTemplateManager manager = level.getStructureManager();

        if (isTwoByTwoSapling) {
            boolean canFindLargeTree = manager.get(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "big_tree")).isPresent();
            if (canFindLargeTree) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                manager.get(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "big_tree")).get()
                        .placeInWorld(level, pos.offset(0, 0, 0), pos, placeSettings, random, Block.UPDATE_ALL);
            }
        } else {
            boolean canFindSmallTree = manager.get(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "small_tree")).isPresent();
            if (canFindSmallTree) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                manager.get(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "small_tree")).get()
                        .placeInWorld(level, pos.offset(-1, 0, -1), pos, placeSettings, random, Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        int stage = state.getValue(STAGES);
        boolean invisible = state.getValue(INVISIBLE);
        int required = requiredInvisibleForStage(stage);

        BiPredicate<BlockState, BlockPos> canSaplingSurvive = (soilState, soilPos) -> {
            TriState decision = soilState.canSustainPlant(level, soilPos, Direction.UP, state);
            if (!decision.isDefault()) return decision.isTrue();
            return this.mayPlaceOn(soilState);
        };

        if (!invisible) {
            BlockPos soilPos = pos.below();
            BlockState soilState = level.getBlockState(soilPos);
            if (!canSaplingSurvive.test(soilState, soilPos)) return false;

            for (int i = 1; i <= required; i++) {
                BlockState above = level.getBlockState(pos.above(i));
                if (above.getBlock() != this) return false;
                if (!above.getValue(INVISIBLE)) return false;
            }
            return true;
        }

        BlockPos current = pos;
        for (int depth = 0; depth <= STAGES.getPossibleValues().size(); depth++) {
            BlockPos below = current.below();
            BlockState belowState = level.getBlockState(below);

            if (belowState.getBlock() != this) return false;

            if (!belowState.getValue(INVISIBLE)) {
                BlockPos soilPos = below.below();
                BlockState soilState = level.getBlockState(soilPos);
                if (!canSaplingSurvive.test(soilState, soilPos)) return false;

                for (int i = 1; i <= required; i++) {
                    BlockState above = level.getBlockState(below.above(i));
                    if (above.getBlock() != this) return false;
                    if (!above.getValue(INVISIBLE)) return false;
                }

                int yOffset = pos.getY() - below.getY();
                return yOffset >= 1 && yOffset <= required;
            }

            current = below;
        }

        return false;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(STAGES).equals(3) ? state.getShape(level, pos) : Shapes.empty();
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            if (!state.getValue(INVISIBLE) && !state.canSurvive(level, pos)) {
                breakWholeSapling((ServerLevel) level, pos);
            }
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    //Destroy the whole sapling
    private void breakWholeSapling(ServerLevel level, BlockPos pos) {
        BlockPos top = pos;
        while (level.getBlockState(top.above()).getBlock() == this) {
            top = top.above();
        }

        // Walk down and break all the blocks
        BlockPos cur = top;
        while (level.getBlockState(cur).getBlock() == this) {
            level.destroyBlock(cur, true);
            cur = cur.below();
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            preventDropFromInvisible(level, pos, state, player);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    protected void preventDropFromInvisible(Level level, BlockPos pos, BlockState state, Player player) {
        boolean invisible = state.getValue(INVISIBLE);
        if (invisible) {
            BlockPos cur = pos;
            while (level.getBlockState(cur).getBlock() == this && level.getBlockState(cur).getValue(INVISIBLE)) {
                cur = cur.below();
            }

            BlockState curBlockState = level.getBlockState(cur);

            if (curBlockState.is(state.getBlock()) && !curBlockState.getValue(INVISIBLE)) {
                level.setBlock(cur, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, cur, Block.getId(Blocks.AIR.defaultBlockState()));
            }
        }
    }

    @Override
    public MapCodec<? extends FlowerBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DawnRedwoodSaplingBlockEntity(pos, state);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !state.getValue(INVISIBLE);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return (double) level.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(INVISIBLE)) return;

        this.advanceTree(level, pos, state, random);
    }

    private static int requiredInvisibleForStage(int stage) {
        return switch (stage) {
            case 2 -> 1;
            case 3 -> 3;
            default -> 0;
        };
    }

    private static boolean isTwoByTwoSapling(BlockState state, BlockGetter level, BlockPos pos, int xOffset, int yOffset) {
        Block block = state.getBlock();
        BlockPos offset = pos.offset(xOffset, 0, yOffset);
        BlockPos offsetX = pos.offset(xOffset + 1, 0, yOffset);
        BlockPos offsetY = pos.offset(xOffset, 0, yOffset + 1);
        BlockPos offsetXY = pos.offset(xOffset + 1, 0, yOffset + 1);

        return level.getBlockState(offset).is(block) && level.getBlockState(offset).getValue(STAGES) == 1
                && level.getBlockState(offsetX).is(block) && level.getBlockState(offsetX).getValue(STAGES) == 1
                && level.getBlockState(offsetY).is(block) && level.getBlockState(offsetY).getValue(STAGES) == 1
                && level.getBlockState(offsetXY).is(block) && level.getBlockState(offsetXY).getValue(STAGES) == 1;
    }

    private void destroyTwoByTwoSaplings(ServerLevel level, BlockPos pos, int xOffset, int yOffset) {
        BlockPos offset = pos.offset(xOffset, 0, yOffset);
        BlockPos offsetX = pos.offset(xOffset + 1, 0, yOffset);
        BlockPos offsetY = pos.offset(xOffset, 0, yOffset + 1);
        BlockPos offsetXY = pos.offset(xOffset + 1, 0, yOffset + 1);

        if (offset != pos) level.removeBlock(offset, false);
        if (offsetX != pos) level.removeBlock(offsetX, false);
        if (offsetY != pos) level.removeBlock(offsetY, false);
        if (offsetXY != pos) level.removeBlock(offsetXY, false);
    }
}
