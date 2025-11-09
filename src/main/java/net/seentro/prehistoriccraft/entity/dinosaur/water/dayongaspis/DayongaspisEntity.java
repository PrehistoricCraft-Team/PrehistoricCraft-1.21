package net.seentro.prehistoriccraft.entity.dinosaur.water.dayongaspis;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.seentro.prehistoriccraft.entity.navigation.goals.fish.FishSwimGoal;
import net.seentro.prehistoriccraft.entity.navigation.goals.fish.FishSwimToBottomGoal;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DayongaspisEntity extends AbstractFish implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public DayongaspisEntity(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 10D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new FishSwimToBottomGoal(this));
        this.goalSelector.addGoal(3, new FishSwimGoal(this));
    }

    private final RawAnimation SWIM = RawAnimation.begin().thenPlay("swim");
    private final RawAnimation IDLE = RawAnimation.begin().thenPlay("idle");
    private final RawAnimation FLOP = RawAnimation.begin().thenPlay("flop");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, this::animationController));
    }

    private PlayState animationController(AnimationState<DayongaspisEntity> animationState) {
        if (!this.isInWater()) {
            animationState.setAnimation(FLOP);
            return PlayState.CONTINUE;
        }

        if (animationState.isMoving()) {
            animationState.setAnimation(SWIM);
            return PlayState.CONTINUE;
        }

        animationState.setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(PrehistoricItems.DAYONGASPIS_BUCKET.get());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }
}
