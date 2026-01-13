package net.seentro.prehistoriccraft.worldgen.tree;

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
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricFoliagePlacerTypes;

public class DawnRedwoodFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<DawnRedwoodFoliagePlacer> CODEC =
        RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        IntProvider.codec(0, 50)
                                .fieldOf("radius")
                                .forGetter(p -> p.radius),

                        IntProvider.codec(-30, 30)
                                .fieldOf("offset")
                                .forGetter(p -> p.offset),

                        IntProvider.codec(0, 50)
                                .fieldOf("foliage_height")
                                .forGetter(p -> p.height)
                ).apply(instance, DawnRedwoodFoliagePlacer::new)
        );

    protected final IntProvider height;

    public DawnRedwoodFoliagePlacer(
            IntProvider radius,
            IntProvider offset,
            IntProvider height
    ) {
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
        java.util.ArrayList<BlockPos> coneCandidates = new java.util.ArrayList<>();

        if (foliageHeight <= 1) {
            int dy = offset;
            int radius = 1;

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    int ax = Math.abs(x);
                    int az = Math.abs(z);

                    int distSq = ax * ax + az * az;
                    int innerSq = radius * radius;
                    int outerSq = (radius + 1) * (radius + 1);

                    if (distSq <= innerSq || (distSq <= outerSq && random.nextInt(2) == 0)) {
                        BlockPos leafPos = center.offset(x, dy, z);
                        this.tryPlaceLeaf(level, blockSetter, random, config, leafPos);
                        coneCandidates.add(leafPos.below());
                    }
                }
            }
        } else {
            int minHeight = this.height.getMinValue();
            int maxHeight = this.height.getMaxValue();
            float t = (float) (foliageHeight - minHeight) / (float) (maxHeight - minHeight);
            t = Mth.clamp(t, 0.0F, 1.0F);
            int maxRadius = foliageRadius;

            int boundary = Math.round(foliageHeight * 0.8f);
            int lastLayer = foliageHeight - 1;

            float bottomFactor = 0.35f;
            float bottomMinRadiusF = Math.max(2.0f, maxRadius * bottomFactor);

            for (int layer = 0; layer < foliageHeight; layer++) {
                int dy = offset - layer;

                float fRadius;
                if (layer <= boundary) {
                    float progress = (float) layer / (float) boundary;
                    fRadius = 1.0F + progress * (maxRadius - 1.0F);
                } else {
                    float progress = (float) (layer - boundary) / (float) (lastLayer - boundary);
                    fRadius = maxRadius - progress * (maxRadius - bottomMinRadiusF);
                }

                int radius = Mth.clamp(Math.round(fRadius), 1, maxRadius);

                int innerSq = radius * radius;
                int outerSq = (radius + 1) * (radius + 1);

                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        int ax = Math.abs(x);
                        int az = Math.abs(z);

                        if (radius > 1 && ax == radius && az == radius) continue;

                        int distSq = ax * ax + az * az;

                        if (distSq <= innerSq || (distSq <= outerSq && random.nextInt(2) == 0)) {
                            BlockPos leafPos = center.offset(x, dy, z);
                            this.tryPlaceLeaf(level, blockSetter, random, config, leafPos);
                            coneCandidates.add(leafPos.below());
                        }
                    }
                }
            }
        }

        for (BlockPos conePos : coneCandidates) {
            if (random.nextFloat() >= 0.75F) continue;

            if (!level.isStateAtPosition(conePos, s -> s.isAir())) continue;

            if (!level.isStateAtPosition(conePos.above(), s -> s.is(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()))) continue;

            blockSetter.set(conePos, PrehistoricBlocks.DAWN_REDWOOD_CONES.get().defaultBlockState());
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
