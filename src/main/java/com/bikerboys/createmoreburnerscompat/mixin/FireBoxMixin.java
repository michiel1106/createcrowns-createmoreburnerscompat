package com.bikerboys.createmoreburnerscompat.mixin;


import com.drmangotea.tfmg.content.machinery.misc.firebox.*;
import com.rae.crowns.content.thermodynamics.conduction.*;
import com.simibubi.create.content.processing.burner.*;
import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel.*;

import org.spongepowered.asm.mixin.*;

@Mixin(FireboxBlockEntity.class)
public class FireBoxMixin implements IHaveTemperature {


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
        FireboxBlockEntity entity = (FireboxBlockEntity)(Object)this;

        BlazeBurnerBlock.HeatLevel value = entity.getBlockState().getValue(FireboxBlock.HEAT_LEVEL);

        return switch (value) {
            case NONE -> 300f;
            case SMOULDERING -> 900;
            default -> 3000f;
        };
    }

    @Override
    public void addTemperature(float v) {

    }
}