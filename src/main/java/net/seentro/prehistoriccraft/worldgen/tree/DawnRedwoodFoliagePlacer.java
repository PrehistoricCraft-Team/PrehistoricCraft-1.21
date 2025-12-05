package net.seentro.prehistoriccraft.worldgen.tree;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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
    protected void createFoliage(
            LevelSimulatedReader level,
            FoliageSetter blockSetter,
            RandomSource random,
            TreeConfiguration config,
            int maxFreeTreeHeight,
            FoliageAttachment attachment,
            int foliageHeight,
            int foliageRadius,
            int offset
    ) {
        BlockPos center = attachment.pos();

        if (foliageHeight <= 1) {
            this.placeLeavesRow(level, blockSetter, random, config,
                    center, 1, offset, attachment.doubleTrunk());
            return;
        }

        int minHeight = this.height.getMinValue();
        int maxHeight = this.height.getMaxValue();
        float t = (float) (foliageHeight - minHeight) / (float) (maxHeight - minHeight);
        t = Mth.clamp(t, 0.0F, 1.0F);
        float fMaxRadius = 6.0F + t * (8.0F - 6.0F);
        int maxRadius = Mth.clamp(Math.round(fMaxRadius), 1, 8);

        int boundary = (foliageHeight * 2) / 3;
        int lastLayer = foliageHeight - 1;

        for (int layer = 0; layer < foliageHeight; layer++) {
            int dy = offset - layer;
            int radius;

            if (layer <= boundary) {
                float progress = (float) layer / (float) boundary;
                float fRadius = 1.0F + progress * (maxRadius - 1.0F);
                radius = Math.round(fRadius);
            } else {
                float progress = (float) (layer - boundary) / (float) (lastLayer - boundary);
                int shrink = Math.round(progress * (maxRadius / 3.0F));
                radius = maxRadius - shrink;
                radius = Math.max(2, radius);
            }

            radius = Math.min(radius, maxRadius);

            this.placeLeavesRow(level, blockSetter, random, config,
                    center, radius, dy, attachment.doubleTrunk());
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return this.height.sample(random);
    }


    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && range > 0;
    }
}
