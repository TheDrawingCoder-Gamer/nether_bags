package gay.menkissing.nether_bags.items

import gay.menkissing.nether_bags.NetherBags
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.{ResourceKey, ResourceLocation}
import net.minecraft.world.item.{CreativeModeTabs, Item}

object NetherBagsItems:
  def register(item: Item, id: String): Item =
    val ident = ResourceLocation(NetherBags.modid, id)
    Registry.register(BuiltInRegistries.ITEM, ident, item)

  val netherBag = register(new ItemNetherPouch(), "nether_bag")

  def init(): Unit =
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                   .register(group => group.accept(netherBag))