package gay.menkissing.nether_bags.screens

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class NetherChestLockedChannelSlot(inv: Container, idx: Int, x: Int, y: Int) extends Slot(inv, idx, x, y):
  override def mayPlace(stack: ItemStack): Boolean = false

  override def mayPickup(player: Player): Boolean = false
