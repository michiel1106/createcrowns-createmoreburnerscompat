package com.bikerboys.createmoreburnerscompat.mixin;


import com.bikerboys.createmoreburnerscompat.rendering.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.rae.crowns.*;
import com.rae.crowns.content.thermodynamics.turbine.*;
import com.rae.crowns.init.client.*;
import com.rae.crowns.init.misc.*;
import static com.rae.crowns.init.misc.BlockInit.TURBINE_STAGE_STRUCTURE;
import com.simibubi.create.content.kinetics.base.*;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.builders.*;
import com.tterrag.registrate.util.entry.*;
import com.tterrag.registrate.util.nullness.*;
import dev.engine_room.flywheel.lib.visualization.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = BlockEntityInit.class, remap = false)
public class MixinBlockEntityInit {



    /*
    @Shadow @Final
    public static final BlockEntityEntry<TurbineStageBlockEntity> TURBINE_STAGE = CROWNS.REGISTRATE
            .blockEntity("turbine_stage", TurbineStageBlockEntity::new)
            .visual(() -> CustomFanVisual::new, false)
            .validBlock(BlockInit.TURBINE_STAGE)
            .renderer(() -> TurbineStageRenderer::new)
            .register();


     */

    /*
    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tterrag/registrate/builders/BlockEntityBuilder;renderer(Lcom/tterrag/registrate/util/nullness/NonNullSupplier;)Lcom/tterrag/registrate/builders/BlockEntityBuilder;",
                    ordinal = 0
            )
    )
    private static <T extends BlockEntity> BlockEntityBuilder<T, ?> wrapTurbineStageRenderer(
            BlockEntityBuilder<T, ?> builder,
            NonNullSupplier<?> original,
            Operation<BlockEntityBuilder<T, ?>> op
    ) {

        if (builder instanceof CreateBlockEntityBuilder<T, ?> blockEntityBuilder) {
            CreateBlockEntityBuilder<T, ?> visual = blockEntityBuilder.visual(() -> (de, d, fl) -> new CustomFanVisual(de, (TurbineStageBlockEntity) d, fl), false);

            return op.call(visual, original);

        }


        return op.call(builder, original);
    }



     */



    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/foundation/data/CreateBlockEntityBuilder;visual(Lcom/tterrag/registrate/util/nullness/NonNullSupplier;)Lcom/simibubi/create/foundation/data/CreateBlockEntityBuilder;"
            )
    )
    private static CreateBlockEntityBuilder<?, ?> wrapTurbineStageVisual(
            CreateBlockEntityBuilder<?, ?> builder,
            NonNullSupplier<?> original,
            Operation<CreateBlockEntityBuilder<?, ?>> op
    ) {
        if ("turbine_stage".equals(builder.getName())) {
            NonNullSupplier<SimpleBlockEntityVisualizer.Factory> replacement =
                    () -> CustomFanVisual::new;

            return op.call(builder, replacement);
        }

        return op.call(builder, original);
    }

}


