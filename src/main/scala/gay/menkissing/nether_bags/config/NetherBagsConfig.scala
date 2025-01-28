package gay.menkissing.nether_bags.config

import gay.menkissing.nether_bags.compat.cloth.NetherBagsClothConfig
import net.fabricmc.loader.api.FabricLoader

trait NetherBagsConfig:
  def allowUsingUnboundBags: Boolean = false
  // replaces the bind behavior and allows changing channel
  def allowChangingItem: Boolean = false

  def allowTuningToBags: Boolean = false
  
  def allowTuningToContainers: Boolean = false


object NetherBagsConfig:
  val DEFAULT: NetherBagsConfig = if FabricLoader.getInstance().isModLoaded("cloth-config") then NetherBagsClothConfig.instance else new NetherBagsConfig {}


