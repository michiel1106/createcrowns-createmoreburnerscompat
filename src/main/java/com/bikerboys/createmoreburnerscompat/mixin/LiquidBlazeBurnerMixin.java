package com.bikerboys.createmoreburnerscompat.mixin;


import com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerBlock;
import com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerBlockEntity;
import com.rae.crowns.content.thermodynamics.IHaveTemperature;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import org.spongepowered.asm.mixin.*;

@Debug(export = true)
@Mixin(LiquidBlazeBurnerBlockEntity.class)
public abstract class LiquidBlazeBurnerMixin implements IHaveTemperature {
    @Shadow public abstract BlazeBurnerBlock.HeatLevel getHeatLevelFromBlock();

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


        return switch (this.getHeatLevelFromBlock()){
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
