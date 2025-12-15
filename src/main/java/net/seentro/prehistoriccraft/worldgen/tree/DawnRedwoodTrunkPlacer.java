package net.seentro.prehistoriccraft.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.seentro.prehistoriccraft.registry.PrehistoricTrunkPlacerTypes;

import java.util.List;
import java.util.function.BiConsumer;

public class DawnRedwoodTrunkPlacer extends TrunkPlacer {
        
        public static final MapCodec<DawnRedwoodTrunkPlacer> CODEC =
        RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.intRange(0, 60) 
                                .fieldOf("base_height")
                                .forGetter(p -> p.baseHeight),

                        Codec.intRange(0, 20)
                                .fieldOf("height_rand_a")
                                .forGetter(p -> p.heightRandA),

                        Codec.intRange(0, 20)
                                .fieldOf("height_rand_b")
                                .forGetter(p -> p.heightRandB)
                ).apply(instance, DawnRedwoodTrunkPlacer::new)
        );

        
        public DawnRedwoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
                super(baseHeight, heightRandA, heightRandB);
        }
        
    @Override
    protected TrunkPlacerType<?> type() {
        return PrehistoricTrunkPlacerTypes.DAWN_REDWOOD_TRUNK_PLACER.get();
    }
        
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int freeTreeHeight,
            BlockPos startPos,
            TreeConfiguration config
    ) {
        int height = this.getTreeHeight(random);
        int radius = 2;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < radius; x++) {
                for (int z = 0; z < radius; z++) {
                    pos.set(
                            startPos.getX() + x,
                            startPos.getY() + y,
                            startPos.getZ() + z
                    );

                    blockSetter.accept(
                            pos,
                            config.trunkProvider.getState(random, pos)
                    );
                }
            }
        }

        BlockPos foliagePos = startPos.above(height);

        return List.of(
                new FoliagePlacer.FoliageAttachment(
                        foliagePos,
                        0,
                        radius > 1
                )
        );
    }
}
