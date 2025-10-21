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

@Debug(export = true)
@Mixin(TurbineStageBlockEntity.class)
public abstract class TurbineStageBlockEntityMixin extends GeneratingKineticBlockEntity implements ISteamPressureChange, ITurbineStageBlockEntityMixin {


    public LerpedFloat visualSpeed = LerpedFloat.linear();
    public float angle;

    public TurbineStageBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(2);
    }

    protected void readFl(CompoundTag compound, boolean clientPacket) {
        if (clientPacket)
            visualSpeed.chase(getGeneratedSpeed(), 1 / 64f, LerpedFloat.Chaser.EXP);
    }

    public void tickFl() {

        if (!level.isClientSide)
            return;

        float targetSpeed = getSpeed();
        visualSpeed.updateChaseTarget(targetSpeed);
        visualSpeed.tickChaser();
        angle += visualSpeed.getValue() * 3 / 10f;
        angle %= 360;
    }


    @Override
    public LerpedFloat getVisualSpeed() {
        return visualSpeed;
    }

    @Override
    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public float getAngle() {
        return angle;
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
