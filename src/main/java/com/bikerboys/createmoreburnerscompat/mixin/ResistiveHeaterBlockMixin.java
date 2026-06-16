package com.bikerboys.createmoreburnerscompat.mixin;


import com.george_vi.electroenergetics.content.resistive_heater.*;
import com.rae.crowns.content.thermodynamics.*;
import com.simibubi.create.content.processing.burner.*;
import org.spongepowered.asm.mixin.*;

@Mixin(ResistiveHeaterBlockEntity.class)
public abstract class ResistiveHeaterBlockMixin implements IHaveTemperature {

    @Shadow
    public abstract BlazeBurnerBlock.HeatLevel calculateHeatLevel(float heat);

    @Shadow
    public float heat;

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
        return switch (calculateHeatLevel(heat)){
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
