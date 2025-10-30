package com.bikerboys.createmoreburnerscompat.rendering;

import java.util.function.Consumer;

import com.bikerboys.createmoreburnerscompat.*;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.rae.crowns.content.thermodynamics.turbine.TurbineStageBlockEntity;
import com.rae.crowns.init.client.PartialModelInit;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;

import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;

public class CustomFanVisual extends KineticBlockEntityVisual<TurbineStageBlockEntity> implements SimpleDynamicVisual {

	protected final RotatingInstance shaft;
	protected final TransformedInstance fan;
	protected float lastAngle = Float.NaN;

	protected final Matrix4f baseTransform = new Matrix4f();

	public CustomFanVisual(VisualizationContext context, TurbineStageBlockEntity blockEntity, float partialTick) {
		super(context, blockEntity, partialTick);



			var axis = rotationAxis();
			shaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.SHAFT)).createInstance();

			shaft.setup(CustomFanVisual.this.blockEntity).setPosition(getVisualPosition()).rotateToFace(axis).setChanged();

			fan = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(PartialModelInit.TURBINE_STAGE)).createInstance();


			Direction align = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);

			fan.translate(getVisualPosition()).center().rotate(new Quaternionf().rotateTo(0, 0, 1, align.getStepX(), align.getStepY(), align.getStepZ()));

			baseTransform.set(fan.pose);

			animate(((ITurbineStageBlockEntityMixin) blockEntity).morecompat$getAngle());

	}

	@Override
	public void beginFrame(DynamicVisual.Context ctx) {

		float partialTicks = ctx.partialTick();

		float speed = ((ITurbineStageBlockEntityMixin) blockEntity).morecompat$getVisualSpeed().getValue(partialTicks) * 3 / 10f;
		float angle = ((ITurbineStageBlockEntityMixin) blockEntity).morecompat$getAngle() + speed * partialTicks;

		if (Math.abs(angle - lastAngle) < 0.001)
			return;

		animate(angle);

		lastAngle = angle;
	}

	private void animate(float angle) {
		fan.setTransform(baseTransform).rotateZ(AngleHelper.rad(angle)).uncenter().setChanged();
	}

	@Override
	public void update(float pt) {
		shaft.setup(blockEntity).setChanged();
	}

	@Override
	public void updateLight(float partialTick) {
		relight(shaft, fan);
	}

	@Override
	protected void _delete() {
		shaft.delete();
		fan.delete();
	}

	@Override
	public void collectCrumblingInstances(Consumer<Instance> consumer) {
		consumer.accept(shaft);
		consumer.accept(fan);
	}
}
