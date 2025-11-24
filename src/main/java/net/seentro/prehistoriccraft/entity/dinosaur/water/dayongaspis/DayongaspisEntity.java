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

    // === Attributes ===
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 10D);
    }

    // === AI Goals ===
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new FishSwimToBottomGoal(this));
        this.goalSelector.addGoal(3, new FishSwimGoal(this));
    }

    // === Animations ===
    private final RawAnimation SWIM = RawAnimation.begin().thenPlay("swim");
    private final RawAnimation IDLE = RawAnimation.begin().thenPlay("idle");
    private final RawAnimation FLOP = RawAnimation.begin().thenPlay("flop");

    // Track current animation to avoid restarting the same one every tick
    private String currentAnimation = "";

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        // 5-tick blend time between animation changes
        controllerRegistrar.add(new AnimationController<>(this, "controller", 5, this::animationController));
    }

    private PlayState animationController(AnimationState<DayongaspisEntity> state) {
        var controller = state.getController();
        String newAnim;

        // Decide which animation should play
        if (!this.isInWater()) {
            newAnim = "flop";
        } else if (state.isMoving()) {
            newAnim = "swim";
        } else {
            newAnim = "idle";
        }

        // Only change if it differs from the current one
        if (!newAnim.equals(currentAnimation)) {
            currentAnimation = newAnim;
            switch (newAnim) {
                case "flop" -> controller.setAnimation(FLOP);
                case "swim" -> controller.setAnimation(SWIM);
                default -> controller.setAnimation(IDLE);
            }
        }

        return PlayState.CONTINUE;
    }

    // === GeckoLib/Entity Overrides ===
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
