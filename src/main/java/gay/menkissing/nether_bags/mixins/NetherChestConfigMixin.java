package gay.menkissing.nether_bags.mixins;

import dev.kir.netherchest.config.NetherChestConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(NetherChestConfig.class)
public interface NetherChestConfigMixin {


    @Inject(method = "isValidChannel(Lnet/minecraft/resources/ResourceLocation;)Z", at = @At("HEAD"), cancellable = true)
    default void isValidChannel(ResourceLocation loc, CallbackInfoReturnable<Boolean> cir) {
        var item = BuiltInRegistries.ITEM.get(loc);
        if (!item.canFitInsideContainerItems()) {
            cir.setReturnValue(false);
        }
    }
}
