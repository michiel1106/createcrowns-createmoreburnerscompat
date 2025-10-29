package com.bikerboys.createmoreburnerscompat.mixin;

import com.rae.crowns.content.thermodynamics.conduction.IHaveTemperature;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.dragonegg.moreburners.content.block.entity.BaseBurnerBlockEntity;
import net.dragonegg.moreburners.content.block.entity.EmberBurnerBlockEntity;
import net.dragonegg.moreburners.content.block.entity.HeatConverterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = HeatConverterBlockEntity.class, remap = false)
public abstract class HeatConverterBlockEntityMixin implements IHaveTemperature {


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
        return switch (getHeatLevelFromBlock()){
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

