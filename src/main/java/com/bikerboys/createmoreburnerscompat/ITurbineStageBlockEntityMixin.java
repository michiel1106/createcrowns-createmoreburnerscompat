package com.bikerboys.createmoreburnerscompat;


import net.createmod.catnip.animation.*;
import org.spongepowered.asm.mixin.*;



public interface ITurbineStageBlockEntityMixin {
    @Unique
    public LerpedFloat morecompat$visualSpeed = LerpedFloat.linear();
    @Unique
    public float angle = 0;


    public default LerpedFloat morecompat$getVisualSpeed() {
        return morecompat$visualSpeed;
    }

    public void morecompat$setAngle(float angle1);

    public float morecompat$getAngle();

}
