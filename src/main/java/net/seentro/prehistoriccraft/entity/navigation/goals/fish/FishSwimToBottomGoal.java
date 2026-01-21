package net.seentro.prehistoriccraft.entity.navigation.goals.fish;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FishSwimToBottomGoal extends Goal {
    private final AbstractFish fish;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected int interval;
    private final boolean checkNoActionTime;

    public FishSwimToBottomGoal(AbstractFish fish) {
        this(fish, 120, true);
    }

    public FishSwimToBottomGoal(AbstractFish fish, int interval, boolean checkNoActionTime) {
        this.fish = fish;
        this.checkNoActionTime = checkNoActionTime;
        this.interval = interval;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.fish.isVehicle()) {
            return false;
        } else {
            if (this.checkNoActionTime && this.fish.getNoActionTime() >= 100) {
                return false;
            }

            if (this.fish.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                return false;
            }
        }

        Vec3 vec3 = this.getPosition();
        if (vec3 == null) {
            return false;
        } else {
            this.wantedX = vec3.x;
            this.wantedY = vec3.y;
            this.wantedZ = vec3.z;
            return true;
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        Vec3 position = this.fish.position();

        return new Vec3(position.x, findSeafloorYPosition(this.fish) + 0.5D, position.z);
    }

    public boolean canContinueToUse() {
        return !this.fish.getNavigation().isDone() && !this.fish.isVehicle();
    }

    public void start() {
        this.fish.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 0.1D);
    }

    public void stop() {
        this.fish.getNavigation().stop();
        super.stop();
    }

    private double findSeafloorYPosition(AbstractFish fish) {
        BlockPos pos = new BlockPos((int) fish.position().x, (int) fish.position().y, (int) fish.position().z);
        while (fish.level().getBlockState(pos.below()) == Blocks.WATER.defaultBlockState()) {
            pos = pos.below();
        }

        return pos.getY();
    }
}
