package gay.menkissing.nether_bags.screens
package client

import net.fabricmc.api.{EnvType, Environment}
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.MenuAccess
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType}
import net.minecraft.client.gui.screens.MenuScreens

@Environment(EnvType.CLIENT)
object NetherBagsScreens:
  def register[H <: AbstractContainerMenu, S <: Screen & MenuAccess[H]](kind: MenuType[? <: H], factory: MenuScreens.ScreenConstructor[H, S]): Unit =
    MenuScreens.register(kind, factory)
    
  register(NetherBagsScreenHandlerTypes.netherPouch, NetherPouchScreen.apply)
  
  def initClient(): Unit = ()
