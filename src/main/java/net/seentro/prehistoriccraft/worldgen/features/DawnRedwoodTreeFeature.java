package net.seentro.prehistoriccraft.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

import java.util.Optional;

public class DawnRedwoodTreeFeature extends Feature<NoneFeatureConfiguration> {
    public DawnRedwoodTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        ServerLevelAccessor level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        StructureTemplateManager manager = level.getLevel().getStructureManager();
        Optional<StructureTemplate> templateOpt = manager.get(ResourceLocation.fromNamespaceAndPath("prehistoriccraft", "small_tree"));

        if (templateOpt.isEmpty()) return false;
        StructureTemplate template = templateOpt.get();

        BlockPos halfSize = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        BlockPos placePos = pos.offset(-halfSize.getX(), 0, -halfSize.getZ());

        Vec3i size = template.getSize();

        BlockState state1 = level.getBlockState(pos);
        BlockState state2 = level.getBlockState(pos.above());
        BlockState state3 = level.getBlockState(pos.above(2));
        BlockState state4 = level.getBlockState(pos.above(3));

        breakWholeSaplingFromBottomSilently((ServerLevel) level, pos);

        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    if (!level.isEmptyBlock(placePos.offset(x, y, z)) && !(level.getBlockState(placePos.offset(x, y, z)).canBeReplaced())) {
                        placeAllMySaplingsYayy((ServerLevel) level, pos, state1, state2, state3, state4);
                        return false;
                    }
                }
            }
        }

        StructurePlaceSettings settings = new StructurePlaceSettings();
        template.placeInWorld(level, placePos, placePos, settings, random, Block.UPDATE_ALL);

        return true;
    }

    // Destroy the whole sapling silently
    private void breakWholeSaplingFromBottomSilently(ServerLevel level, BlockPos pos) {
        // Find the top-most block of the plant
        BlockPos topPos = pos;
        while (level.getBlockState(topPos.above()).getBlock() == PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get()) {
            topPos = topPos.above();
        }

        // Walk down and break the whole sapling on the way
        BlockPos currentPos = topPos;
        while (level.getBlockState(currentPos).getBlock() == PrehistoricBlocks.DAWN_REDWOOD_SAPLING.get()) {
            level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
            currentPos = currentPos.below();
        }

        // Break the final block last
        level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
    }

    private void placeAllMySaplingsYayy(ServerLevel level, BlockPos pos, BlockState state1 ,BlockState state2, BlockState state3, BlockState state4) {
        level.setBlock(pos, state1, Block.UPDATE_CLIENTS);
        level.setBlock(pos.above(), state2, Block.UPDATE_CLIENTS);
        level.setBlock(pos.above(2), state3, Block.UPDATE_CLIENTS);
        level.setBlock(pos.above(3), state4, Block.UPDATE_ALL);
    }
}
