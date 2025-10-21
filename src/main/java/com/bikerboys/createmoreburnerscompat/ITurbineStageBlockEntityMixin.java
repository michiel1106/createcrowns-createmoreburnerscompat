package com.bikerboys.createmoreburnerscompat;


import net.createmod.catnip.animation.*;
import org.spongepowered.asm.mixin.*;


public interface ITurbineStageBlockEntityMixin {
    @Unique
    public LerpedFloat visualSpeed = LerpedFloat.linear();
    @Unique
    public float angle = 0;


    public default LerpedFloat getVisualSpeed() {
        return visualSpeed;
    }

    public void setAngle(float angle1);

    public float getAngle();

}
