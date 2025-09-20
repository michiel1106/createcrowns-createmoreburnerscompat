package com.bikerboys.createmoreburnerscompat.mixin;


import com.rae.crowns.content.thermodynamics.conduction.IHaveTemperature;
import net.dragonegg.moreburners.content.block.entity.BaseBurnerBlockEntity;
import net.dragonegg.moreburners.content.block.entity.EmberBurnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EmberBurnerBlockEntity.class)
public abstract class EmberBurnerBlockEntityMixin extends BaseBurnerBlockEntity implements IHaveTemperature {
    public EmberBurnerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
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
