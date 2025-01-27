package gay.menkissing.nether_bags.compat.modmenu

import com.terraformersmc.modmenu.api.{ConfigScreenFactory, ModMenuApi}
import gay.menkissing.nether_bags.compat.cloth.NetherBagsClothConfig
import me.shedaniel.autoconfig.AutoConfig
import net.fabricmc.api.{EnvType, Environment}
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screens.Screen


@Environment(EnvType.CLIENT)
object ModMenuCompat extends ModMenuApi:
  val isClothLoaded = FabricLoader.getInstance().isModLoaded("cloth-config")

  override def getModConfigScreenFactory: ConfigScreenFactory[?] =
    if !isClothLoaded then
      return parent => null

    new ConfigScreenFactory[Screen]:
      override def create(parent: Screen): Screen =
        AutoConfig.getConfigScreen(classOf[NetherBagsClothConfig], parent).get()
