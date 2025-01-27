package gay.menkissing.nether_bags.mixins;

import dev.kir.netherchest.inventory.NetherChestInventoryView;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherChestInventoryView.class)
public class NetherChestInventoryViewMixin {
    @Inject(method = "stillValid(Lnet/minecraft/world/entity/player/Player;)Z", at = @At("HEAD"), cancellable = true)
    private void stillValidMixin(Player player, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}
