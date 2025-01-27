package gay.menkissing.nether_bags
package screens

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType}

object NetherBagsScreenHandlerTypes:
  def register[T <: AbstractContainerMenu](id: String, factory: MenuType.MenuSupplier[T]): MenuType[T] =
    register(ResourceLocation(NetherBags.modid, id), factory)
  
  def register[T <: AbstractContainerMenu](id: ResourceLocation, factory: MenuType.MenuSupplier[T]): MenuType[T] =
    Registry.register(BuiltInRegistries.MENU, id, new MenuType[T](factory, FeatureFlags.VANILLA_SET))

  val netherPouch = register("nether_bag", NetherPouchScreenHandler.apply)
  
  def init(): Unit = ()