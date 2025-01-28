package gay.menkissing.nether_bags.screens
package client

import dev.kir.netherchest.NetherChest
import net.fabricmc.api.{EnvType, Environment}
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.{AbstractContainerScreen, MenuAccess}
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

object NetherPouchScreen:
  val texture: ResourceLocation = NetherChest.locate("textures/gui/container/nether_chest.png")

// Ipn doesn't work? too bad : (
// @IPNIgnore
@Environment(EnvType.CLIENT)
class NetherPouchScreen(handler: NetherPouchScreenHandler, inventory: Inventory, title: Component) extends AbstractContainerScreen[NetherPouchScreenHandler](handler, inventory, title) with MenuAccess[NetherPouchScreenHandler]:
  this.imageWidth = 212
  this.imageHeight = 168
  this.inventoryLabelY = this.imageHeight - 95

  override def render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, f: Float): Unit =
    this.renderBackground(guiGraphics)
    super.render(guiGraphics, mouseX, mouseY, f)
    this.renderTooltip(guiGraphics, mouseX, mouseY)

  override def renderBg(guiGraphics: GuiGraphics, f: Float, i: Int, j: Int): Unit =
    val x = (this.width - this.imageWidth) / 2
    val y = (this.height - this.imageHeight) / 2
    guiGraphics.blit(NetherPouchScreen.texture, x, y, 0, 0, this.imageWidth, this.imageHeight)
