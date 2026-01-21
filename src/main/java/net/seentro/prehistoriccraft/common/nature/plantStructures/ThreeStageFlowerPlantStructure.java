package net.seentro.prehistoriccraft.common.nature.plantStructures;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;
import net.seentro.prehistoriccraft.worldgen.PrehistoricConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

public class ThreeStageFlowerPlantStructure extends FlowerBlock {
    public static final MapCodec<ThreeStageFlowerPlantStructure> CODEC = RecordCodecBuilder.mapCodec(
            blockInstance -> blockInstance.group(
                            EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects), TreeGrower.CODEC.fieldOf("tree").forGetter(Structure -> Structure.treeGrower), propertiesCodec())
                    .apply(blockInstance, ThreeStageFlowerPlantStructure::new));

    public static final IntegerProperty STAGES = IntegerProperty.create("stages", 1, 3);
    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public static final BooleanProperty TWO_BY_TWO = BooleanProperty.create("two_by_two");

    protected final TreeGrower treeGrower;

    public ThreeStageFlowerPlantStructure(SuspiciousStewEffects effects, TreeGrower treeGrower, Properties properties) {
        super(effects, properties);
        this.treeGrower = treeGrower;
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

    // Randomly tick, growing the tree
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (state.getValue(INVISIBLE)) return;

        if (random.nextInt(30) == 0) {
            this.advanceTree(level, pos, state, random);
        }
    }

    // Grow the tree to the next stage / place tree
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        int stage = state.getValue(STAGES);
        if (stage >= 3) {

            this.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
            return;
        }

        int nextStage = stage + 1;
        int requiredHeight = requiredInvisibleForStage(nextStage);

        for (int i = 1; i <= requiredHeight; i++) {
            BlockState offsetState = level.getBlockState(pos.above(i));

            if (offsetState.getBlock() == this && offsetState.getValue(INVISIBLE)) continue;
            if (offsetState.canBeReplaced()) continue;

            return;
        }

        boolean isTwoByTwo = false;

        if (state.getValue(TWO_BY_TWO)) {
            isTwoByTwo = true;
        } else if (stage == 1) {
            for (int x = 0; x >= -1; x--) {
                for (int z = 0; z >= -1; z--) {
                    if (isTwoByTwo) break;

                    if (isTwoByTwoSapling(state, level, pos, x, z)) {
                        destroyTwoByTwoSaplings(level, pos, x, z);
                        isTwoByTwo = true;
                    }
                }
            }
        }

        BlockState toPlaceState = state.setValue(STAGES, nextStage).setValue(TWO_BY_TWO, isTwoByTwo);
        BlockState invisibleState = toPlaceState.setValue(INVISIBLE, true);

        for (int i = requiredHeight; i >= 1; i--) {
            BlockPos offsetPos = pos.above(i);
            BlockState offsetState = level.getBlockState(offsetPos);

            if (offsetState.getBlock() == this && offsetState.getValue(INVISIBLE) && offsetState.getValue(STAGES) == nextStage)
                continue;

            level.setBlock(offsetPos, invisibleState, Block.UPDATE_CLIENTS);
        }

        level.setBlock(pos, toPlaceState, Block.UPDATE_ALL);
    }

    // Can place on?
    protected boolean mayPlaceOn(BlockState state) {
        return state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
    }

    // Can the tree survive?
    // Need to check if the ground is valid too!
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean isInvisible = state.getValue(INVISIBLE);
        int requiredHeight = requiredInvisibleForStage(state.getValue(STAGES));

        // Can the base sapling block survive on ground?
        BiPredicate<BlockState, BlockPos> canSaplingSurvive = (soilState, soilPos) -> {
            TriState decision = soilState.canSustainPlant(level, soilPos, Direction.UP, state);
            if (!decision.isDefault()) return decision.isTrue();
            return this.mayPlaceOn(soilState);
        };

        // Only execute if we are the base
        if (!isInvisible) {
            if (!canSaplingSurvive.test(level.getBlockState(pos.below()), pos.below())) return false;

            for (int i = 1; i < requiredHeight; i++) {
                BlockState offsetState = level.getBlockState(pos.above(i));

                if (offsetState.getBlock() != this) return false;
                if (!offsetState.getValue(INVISIBLE)) return false;
            }

            return true;
        }

        // Check if all blocks are present below
        // Only runs for the invisible blocks

        for (int i = 0; i <= STAGES.getPossibleValues().size(); i++) {
            BlockPos offsetPos = pos.below(i);
            BlockState offsetState = level.getBlockState(offsetPos);

            if (offsetState.getBlock() != this) return false;

            if (!offsetState.getValue(INVISIBLE)) {
                for (int j = 1; j <= requiredHeight; j++) {
                    BlockState aboveOffsetState = level.getBlockState(offsetPos.above(j));

                    if (aboveOffsetState.getBlock() != this) return false;
                    if (!aboveOffsetState.getValue(INVISIBLE)) return false;
                }

                int yOffset = pos.getY() - offsetPos.getY();
                return yOffset >= 1 && yOffset <= requiredHeight;
            }
        }

        return false;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(STAGES).equals(3) ? state.getShape(level, pos) : Shapes.empty();
    }

    // Prevent dropping the invisible parts
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            preventDropFromBase(level, pos);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    private void preventDropFromBase(Level level, BlockPos pos) {
        BlockPos bottomPos = pos;
        while (level.getBlockState(bottomPos.below()).getBlock() == this) {
            bottomPos = bottomPos.below();
        }

        level.destroyBlock(bottomPos, false);
    }

    public static final SoundType SILENT = new SoundType(-1.0F, 1.0F, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK, SoundEvents.AMETHYST_BLOCK_BREAK);

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(INVISIBLE) ? SILENT : super.getSoundType(state, level, pos, entity);
    }

    @Override
    public MapCodec<? extends FlowerBlock> codec() {
        return CODEC;
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

    /* FROM VANILLA */

    // Places the tree
    public void growTree(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random) {
        ResourceKey<ConfiguredFeature<?, ?>> dawnRedwoodTreeKey = PrehistoricConfiguredFeatures.DAWN_REDWOOD_KEY;
        ResourceKey<ConfiguredFeature<?, ?>> dawnRedwoodBigTreeKey = PrehistoricConfiguredFeatures.DAWN_REDWOOD_BIG_KEY;
        Holder<ConfiguredFeature<?, ?>> dawnRedwoodHolder = level.registryAccess()
                .registryOrThrow(Registries.CONFIGURED_FEATURE)
                .getHolder(state.getValue(TWO_BY_TWO)? dawnRedwoodBigTreeKey : dawnRedwoodTreeKey)
                .orElse(null);
        var event = net.neoforged.neoforge.event.EventHooks.fireBlockGrowFeature(level, random, pos, dawnRedwoodHolder);
        dawnRedwoodHolder = event.getFeature();
        if (event.isCanceled()) return;
        if (dawnRedwoodHolder == null) {
        } else {
            ConfiguredFeature<?, ?> configuredFeature = dawnRedwoodHolder.value();
            BlockState blockState = level.getFluidState(pos).createLegacyBlock();
            level.setBlock(pos, blockState, 4);
            level.setBlock(pos.above(), blockState, 4);
            level.setBlock(pos.above(2), blockState, 4);
            level.setBlock(pos.above(3), blockState, 4);
            if (configuredFeature.place(level, chunkGenerator, random, pos)) {
                if (level.getBlockState(pos) == blockState) {
                    BlockState invisibleState = state.setValue(INVISIBLE, true);
                    level.sendBlockUpdated(pos.above(), invisibleState, blockState, Block.UPDATE_CLIENTS);
                    level.sendBlockUpdated(pos.above(2), invisibleState, blockState, Block.UPDATE_CLIENTS);
                    level.sendBlockUpdated(pos.above(3), invisibleState, blockState, Block.UPDATE_CLIENTS);
                    level.sendBlockUpdated(pos, state, blockState, Block.UPDATE_ALL);
                }

            } else {
                BlockState invisibleState = state.setValue(INVISIBLE, true);
                level.setBlock(pos.above(), invisibleState, Block.UPDATE_CLIENTS);
                level.setBlock(pos.above(2), invisibleState, Block.UPDATE_CLIENTS);
                level.setBlock(pos.above(3), invisibleState, Block.UPDATE_CLIENTS);

                level.setBlock(pos, state, Block.UPDATE_ALL);
            }
        }
    }
}
