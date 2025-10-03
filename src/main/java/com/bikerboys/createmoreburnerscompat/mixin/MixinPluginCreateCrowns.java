package com.bikerboys.createmoreburnerscompat.mixin;


import net.neoforged.fml.*;
import net.neoforged.fml.loading.*;
import net.neoforged.fml.loading.moddiscovery.*;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPluginCreateCrowns implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        ModFileInfo crowns = net.neoforged.fml.loading.FMLLoader.getLoadingModList().getModFileById("crowns");
        ModFileInfo moreburners = FMLLoader.getLoadingModList().getModFileById("moreburners");
        ModFileInfo createaddition = FMLLoader.getLoadingModList().getModFileById("createaddition");
        ModFileInfo thefactorymustgrow = FMLLoader.getLoadingModList().getModFileById("tfmg");

        boolean crownsLoaded = crowns != null;
        boolean moreburnersLoaded = moreburners != null;
        boolean createadditionLoaded = createaddition != null;
        boolean tfmgLoaded = thefactorymustgrow != null;



        // --- More Burners mixins ---
        if (mixinClassName.endsWith("ElectricBurnerBlockEntityMixin")
                || mixinClassName.endsWith("EmberBurnerBlockEntityMixin")
                || mixinClassName.endsWith("HeatConverterBlockEntityMixin")) {
            return moreburnersLoaded && crownsLoaded;
        }

        // --- Create Addition (Liquid Blaze Burner) mixin ---
        if (mixinClassName.endsWith("LiquidBlazeBurnerMixin")) {
            return createadditionLoaded && crownsLoaded;
        }

        if (mixinClassName.endsWith("FireBoxMixin")) {

            System.out.println("LOADING IT UP YAY");
            return tfmgLoaded;
        }


        return false; // default: apply
    }


    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
