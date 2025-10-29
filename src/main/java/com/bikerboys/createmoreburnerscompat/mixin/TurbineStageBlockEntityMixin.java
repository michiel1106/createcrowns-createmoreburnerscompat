package com.bikerboys.createmoreburnerscompat.mixin;


import com.bikerboys.createmoreburnerscompat.*;
import com.rae.crowns.content.thermodynamics.turbine.*;
import com.simibubi.create.content.kinetics.base.*;
import net.createmod.catnip.animation.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = TurbineStageBlockEntity.class, remap = false)
public abstract class TurbineStageBlockEntityMixin extends GeneratingKineticBlockEntity implements ISteamPressureChange, ITurbineStageBlockEntityMixin {


    @Unique
    public LerpedFloat morecompat$visualSpeed = LerpedFloat.linear();
    @Unique
    public float morecompat$angle;

    public TurbineStageBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(2);
    }

    protected void readFl(CompoundTag compound, boolean clientPacket) {
        if (clientPacket)
            morecompat$visualSpeed.chase(getGeneratedSpeed(), 1 / 64f, LerpedFloat.Chaser.EXP);
    }

    public void tickFl() {

        if (!level.isClientSide)
            return;

        float targetSpeed = getSpeed();
        morecompat$visualSpeed.updateChaseTarget(targetSpeed);
        morecompat$visualSpeed.tickChaser();
        morecompat$angle += morecompat$visualSpeed.getValue() * 3 / 10f;
        morecompat$angle %= 360;
    }


    @Override
    public LerpedFloat morecompat$getVisualSpeed() {
        return morecompat$visualSpeed;
    }

    @Override
    public void morecompat$setAngle(float angle) {
        this.morecompat$angle = angle;
    }

    @Override
    public float morecompat$getAngle() {
        return morecompat$angle;
    }



    @Override
    public void tick() {
        super.tick();
        tickFl();
    }


    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        readFl(compound, clientPacket);
    }
}
