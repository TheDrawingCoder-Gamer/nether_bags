package gay.menkissing.nether_bags

import gay.menkissing.nether_bags.config.NetherBagsConfig
import gay.menkissing.nether_bags.items.NetherBagsItems
import gay.menkissing.nether_bags.screens.NetherBagsScreenHandlerTypes
import gay.menkissing.nether_bags.screens.client.NetherBagsScreens
import net.fabricmc.api.{ClientModInitializer, ModInitializer}

object NetherBags extends ModInitializer, ClientModInitializer:
  final val modid = "nether_bags"

  // load to force an evaluation of static fields

  val config: NetherBagsConfig = NetherBagsConfig.DEFAULT

  override def onInitialize(): Unit =
    NetherBagsItems.init()
    NetherBagsScreenHandlerTypes.init()



  override def onInitializeClient(): Unit =
    NetherBagsScreens.initClient()

