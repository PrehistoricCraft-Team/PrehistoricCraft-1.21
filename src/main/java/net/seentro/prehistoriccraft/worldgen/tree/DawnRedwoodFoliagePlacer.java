package net.seentro.prehistoriccraft.worldgen.tree;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.seentro.prehistoriccraft.registry.PrehistoricFoliagePlacerTypes;

public class DawnRedwoodFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<DawnRedwoodFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            foliagePlacerInstance -> foliagePlacerParts(foliagePlacerInstance)
                    .and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(foliagePlacer -> foliagePlacer.height))
                    .apply(foliagePlacerInstance, DawnRedwoodFoliagePlacer::new));

    protected final IntProvider height;

    public DawnRedwoodFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return PrehistoricFoliagePlacerTypes.DAWN_REDWOOD_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos blockpos = attachment.pos();
        int currentRadius = 0;
        int targetRadius = 3;
        int rowsToHold = 1;

        for (int verticalOffset = offset; verticalOffset >= -foliageHeight; verticalOffset--) {
            this.placeLeavesRow(level, blockSetter, random, config, blockpos, currentRadius, verticalOffset, attachment.doubleTrunk());
            if (currentRadius >= targetRadius) {
                if (rowsToHold > 0) {
                    rowsToHold--;
                    continue;
                }
                currentRadius--;
                targetRadius--;
            } else {
                currentRadius++;
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return Math.max(4, height - this.height.sample(random));
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && range > 0;
    }
}
