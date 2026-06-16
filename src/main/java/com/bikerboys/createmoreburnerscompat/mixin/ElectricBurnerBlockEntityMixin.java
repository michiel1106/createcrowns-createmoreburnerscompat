package com.bikerboys.createmoreburnerscompat.mixin;

import com.rae.crowns.content.fields.util.*;
import com.rae.crowns.content.thermodynamics.IHaveTemperature;
import com.simibubi.create.content.processing.burner.*;
import com.simibubi.create.foundation.blockEntity.*;
import net.dragonegg.moreburners.content.block.entity.BaseBurnerBlockEntity;
import net.dragonegg.moreburners.content.block.entity.ElectricBurnerBlockEntity;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
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


    @Unique
    boolean cROWNS_1_20_1$registrationDone = false;

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    private void onTick(CallbackInfo ci) {
        BlockEntity self = (BlockEntity) (Object) this;
        if (cROWNS_1_20_1$registrationDone) return;

        if (self instanceof IHaveTemperature ht && self.getLevel() instanceof ServerLevel serverLevel) {
            PhysicsWorldData data       = PhysicsSaveManager.get(serverLevel);
            BlockPos         pos        = self.getBlockPos();
            SectionPos       sectionPos = SectionPos.of(pos);
            if (data != null && PhysicsSaveManager.isLoaded(serverLevel.dimension(), sectionPos.asLong())) {
                data.putDynamic(self.getBlockPos(), ht);
                cROWNS_1_20_1$registrationDone = true;
            }
        } else cROWNS_1_20_1$registrationDone = true;
    }


    @Override
    public void setRemoved() {
        super.setRemoved();
        BlockEntity self = (BlockEntity) (Object) this;

        if (self instanceof IHaveTemperature && self.getLevel() instanceof ServerLevel serverLevel) {
            PhysicsWorldData data = PhysicsSaveManager.get(serverLevel);
            if (data != null) {
                data.removeDynamic(self.getBlockPos());
            }
        }

    }

    public float getThermalCapacity() {
        return 1000;
    }

    public float getThermalConductivity() {
        return 100000;
    }

    public float getTemperature() {

        return switch (getHeatLevelFromBlock()){
            case SMOULDERING -> 500F;
            case FADING -> 600F;
            case KINDLED -> 1200F;
            case SEETHING -> 1600F;
            default -> 300f;
        };
    }

    public void addTemperature(float v) {

    }
}
