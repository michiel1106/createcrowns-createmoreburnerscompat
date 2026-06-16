package com.bikerboys.createmoreburnerscompat.mixin;


import com.george_vi.electroenergetics.content.resistive_heater.*;
import com.rae.crowns.content.fields.util.*;
import com.rae.crowns.content.thermodynamics.*;
import com.simibubi.create.content.processing.burner.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import org.antarcticgardens.cna.content.heat.heater.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(HeaterBlockEntity.class)
public abstract class HeaterMixin extends BlockEntity implements IHaveTemperature {

    public HeaterMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Unique
    boolean cROWNS_1_20_1$registrationDone = false;


    @Inject(method = "getHeat", at = @At("TAIL"), remap = false)
    private void onTick(CallbackInfoReturnable<Float> cir) {

        HeaterBlockEntity self = (HeaterBlockEntity)(Object)this;

        if (cROWNS_1_20_1$registrationDone) return;

        if (self instanceof IHaveTemperature ht && self.getLevel() instanceof ServerLevel serverLevel) {
            PhysicsWorldData data       = PhysicsSaveManager.get(serverLevel);
            BlockPos         pos        = self.getBlockPos();
            SectionPos sectionPos = SectionPos.of(pos);
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


        HeaterBlockEntity heater = (HeaterBlockEntity)(Object)this;

        BlazeBurnerBlock.HeatLevel strength = heater.getBlockState().getValue(HeaterBlock.STRENGTH);

        return switch (strength){
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
