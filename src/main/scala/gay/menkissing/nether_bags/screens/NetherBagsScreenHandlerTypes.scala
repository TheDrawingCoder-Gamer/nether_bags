package gay.menkissing.nether_bags
package screens

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType}

object NetherBagsScreenHandlerTypes:
  def register[T <: AbstractContainerMenu](id: String, factory: (Int, Inventory, FriendlyByteBuf) => T): MenuType[T] =
    register(ResourceLocation(NetherBags.modid, id), factory)
  
  def register[T <: AbstractContainerMenu](id: ResourceLocation, factory: (Int, Inventory, FriendlyByteBuf) => T): MenuType[T] =
    Registry.register(BuiltInRegistries.MENU, id, new ExtendedScreenHandlerType[T](factory.apply))

  val netherPouch = register("nether_bag", NetherPouchScreenHandler.fromNetwork)

  def init(): Unit = ()