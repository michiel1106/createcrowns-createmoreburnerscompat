package com.bikerboys.createmoreburnerscompat.mixin;

import com.rae.crowns.content.thermodynamics.IHaveTemperature;
import com.simibubi.create.content.processing.burner.*;
import net.dragonegg.moreburners.content.block.entity.BaseBurnerBlockEntity;
import net.dragonegg.moreburners.content.block.entity.ElectricBurnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.*;
import org.spongepowered.asm.mixin.*;

import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;


@Debug(export = true)
@Mixin(ElectricBurnerBlockEntity.class)
public abstract class ElectricBurnerBlockEntityMixin extends BaseBurnerBlockEntity implements IHaveTemperature {
    public ElectricBurnerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }



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
        ElectricBurnerBlockEntity entity = (ElectricBurnerBlockEntity)(Object)this;

        BlazeBurnerBlock.HeatLevel value = entity.getBlockState().getValue(BlazeBurnerBlock.HEAT_LEVEL);

        return switch (value){
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
