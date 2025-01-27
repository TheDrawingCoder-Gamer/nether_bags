package gay.menkissing.nether_bags

import gay.menkissing.nether_bags.items.NetherBagsItems
import net.fabricmc.api.ModInitializer

object NetherBags extends ModInitializer:
  val modid = "nether_bags"

  // load to force an evaluation of static fields
  NetherBagsItems.init()

  override def onInitialize(): Unit = ()

