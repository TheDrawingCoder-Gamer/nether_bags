package gay.menkissing.nether_bags.mixins;

import dev.kir.netherchest.config.NetherChestConfig;
import gay.menkissing.nether_bags.NetherBags$;
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
        if (!NetherBags$.MODULE$.config().allowTuningToBags() && loc.equals(new ResourceLocation("nether_bags", "nether_bag"))) {
            cir.setReturnValue(false);
            return;
        }
        var item = BuiltInRegistries.ITEM.get(loc);
        if (!NetherBags$.MODULE$.config().allowTuningToContainers() && !item.canFitInsideContainerItems()) {
            cir.setReturnValue(false);
        }
    }
}
