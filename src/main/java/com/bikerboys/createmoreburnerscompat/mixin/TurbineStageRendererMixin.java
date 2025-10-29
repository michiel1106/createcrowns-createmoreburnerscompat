package com.bikerboys.createmoreburnerscompat.mixin;


import com.bikerboys.createmoreburnerscompat.*;
import com.mojang.blaze3d.vertex.*;
import com.rae.crowns.content.thermodynamics.turbine.*;
import com.rae.crowns.init.client.*;
import com.simibubi.create.content.kinetics.base.*;
import dev.engine_room.flywheel.api.visualization.*;
import net.createmod.catnip.math.*;
import net.createmod.catnip.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.state.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(value = TurbineStageRenderer.class, remap = false)
public class TurbineStageRendererMixin extends KineticBlockEntityRenderer<TurbineStageBlockEntity> {


    public TurbineStageRendererMixin(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "renderSafe(Lcom/rae/crowns/content/thermodynamics/turbine/TurbineStageBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"), cancellable = true)
    private void renderSafeMixin(TurbineStageBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay, CallbackInfo ci) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        float speed = ((ITurbineStageBlockEntityMixin)be).morecompat$getVisualSpeed().getValue(partialTicks) * 3 / 10f;
        float angle = ((ITurbineStageBlockEntityMixin)be).morecompat$getAngle() + speed * partialTicks;

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());
        renderFlywheel(be, ms, light, blockState, angle, vb);

        ci.cancel();
    }

    private void renderFlywheel(TurbineStageBlockEntity be, PoseStack ms, int light, BlockState blockState, float angle,
                                VertexConsumer vb) {
        SuperByteBuffer wheel = CachedBuffers.block(blockState);
        BlockState state = be.getBlockState();
        Direction direction =  Direction.fromAxisAndDirection(((TurbineStageBlock)state.getBlock()).getRotationAxis(state), Direction.AxisDirection.POSITIVE);
        SuperByteBuffer memoryRoll =
                CachedBuffers.partialFacing(PartialModelInit.TURBINE_STAGE, be.getBlockState(), direction.getOpposite());
        kineticRotationTransform(memoryRoll, be, getRotationAxisOf(be), AngleHelper.rad(angle), light);
        memoryRoll.renderInto(ms, vb);
    }

    @Override
    protected BlockState getRenderedBlockState(TurbineStageBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }


}
