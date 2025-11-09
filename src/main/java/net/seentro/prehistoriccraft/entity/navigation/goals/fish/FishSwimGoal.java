package net.seentro.prehistoriccraft.entity.navigation.goals.fish;

import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FishSwimGoal extends RandomSwimmingGoal {
    public FishSwimGoal(AbstractFish fish) {
        super(fish, 1.0, 40);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return BehaviorUtils.getRandomSwimmablePos(this.mob, 10, 1);
    }
}
