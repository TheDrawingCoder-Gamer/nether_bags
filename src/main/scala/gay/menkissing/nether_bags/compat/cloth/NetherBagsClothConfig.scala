package gay.menkissing.nether_bags.compat.cloth

import gay.menkissing.nether_bags.NetherBags
import gay.menkissing.nether_bags.config.NetherBagsConfig
import me.shedaniel.autoconfig.{AutoConfig, ConfigData}
import me.shedaniel.autoconfig.annotation.{Config, ConfigEntry}
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer

@Config(name = NetherBags.modid)
class NetherBagsClothConfig extends NetherBagsConfig, ConfigData:
  @ConfigEntry.Gui.Tooltip(count = 2)
  var allowUsingUnboundBagsField: Boolean = super.allowUsingUnboundBags

  @ConfigEntry.Gui.Tooltip(count = 2)
  var allowChangingItemField: Boolean = super.allowChangingItem


  override def allowUsingUnboundBags: Boolean = allowUsingUnboundBagsField

  override def allowChangingItem: Boolean = allowChangingItemField

object NetherBagsClothConfig:
  val instance = AutoConfig.register(classOf[NetherBagsClothConfig], (i, j) => GsonConfigSerializer(i, j)).getConfig
