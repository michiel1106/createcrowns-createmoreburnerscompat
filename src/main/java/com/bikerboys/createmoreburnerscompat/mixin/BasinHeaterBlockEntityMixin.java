package com.bikerboys.createmoreburnerscompat.mixin;

import com.rae.crowns.content.thermodynamics.conduction.*;
import com.simibubi.create.content.processing.burner.*;
import org.patryk3211.powergrid.electricity.basinheater.*;
import org.spongepowered.asm.mixin.*;

@Mixin(BasinHeaterBlockEntity.class)
public class BasinHeaterBlockEntityMixin implements IHaveTemperature {
    @Shadow
    private BlazeBurnerBlock.HeatLevel state;

    @Override
    public float getThermalCapacity() {
        return 1000;
    }

    @Override
    public float getThermalConductivity() {
        return 100000;
    }

    @Override
    public float getTemperature() {
        return switch (state){
            case NONE -> 300f;
            case SMOULDERING -> 500F;
            case FADING -> 900F;
            case KINDLED -> 1800F;
            case SEETHING -> 3000F;
        };
    }

    @Override
    public void addTemperature(float v) {

    }
}
