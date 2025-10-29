package com.bikerboys.createmoreburnerscompat.mixin;


import com.bikerboys.createmoreburnerscompat.rendering.*;
import com.rae.colony_api.multiblock.*;
import com.rae.crowns.*;
import com.rae.crowns.content.thermodynamics.turbine.*;
import com.rae.crowns.init.misc.*;
import static com.rae.crowns.init.misc.BlockInit.TURBINE_STAGE_STRUCTURE;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.builders.*;
import com.tterrag.registrate.util.entry.*;
import net.minecraft.world.level.block.state.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = BlockEntityInit.class, remap = false)
public class MixinBlockEntityInit {



    @Shadow @Final
    public static final BlockEntityEntry<TurbineStageBlockEntity> TURBINE_STAGE = CROWNS.REGISTRATE
            .blockEntity("turbine_stage", TurbineStageBlockEntity::new)
            .visual(() -> CustomFanVisual::new, false)
            .validBlock(BlockInit.TURBINE_STAGE)
            .renderer(() -> TurbineStageRenderer::new)
            .register();


}
