package com.bikerboys.createmoreburnerscompat.mixin;


import com.drmangotea.tfmg.content.machinery.misc.firebox.*;
import com.rae.crowns.content.fields.util.*;
import com.rae.crowns.content.thermodynamics.IHaveTemperature;
import com.simibubi.create.content.processing.burner.*;
import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel.*;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.block.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

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