package com.bikerboys.createmoreburnerscompat.mixin;

import com.rae.crowns.content.thermodynamics.*;
import com.simibubi.create.content.processing.burner.*;
import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel.SEETHING;
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
            case SMOULDERING -> 500F;
            case FADING -> 600F;
            case KINDLED -> 1200F;
            case SEETHING -> 1600F;
            default -> 300f;
        };
    }

    @Override
    public void addTemperature(float v) {

    }
}
