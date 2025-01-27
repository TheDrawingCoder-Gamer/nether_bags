package gay.menkissing.nether_bags.screens

import gay.menkissing.nether_bags.screens.NetherPouchScreenHandler.isClient
import net.minecraft.world.{Container, SimpleContainer}
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, Slot}
import net.minecraft.world.inventory.AbstractContainerMenu.checkContainerSize
import net.minecraft.world.item.ItemStack

object NetherPouchScreenHandler:
  val rows = 3
  val columns = 9
  val containerSize = rows * columns + 1

  def apply(syncId: Int, playerInv: Inventory) =
    new NetherPouchScreenHandler(syncId, playerInv, new SimpleContainer(containerSize))

  def isClient(inv: Inventory): Boolean =
    inv.player == null || inv.player.level() == null || inv.player.level().isClientSide

// Copy of NetherChestScreenHandler but using mojmaps and with the slot locked
class NetherPouchScreenHandler(syncId: Int, playerInv: Inventory, val container: Container) extends AbstractContainerMenu(NetherBagsScreenHandlerTypes.netherPouch, syncId):
  checkContainerSize(container, NetherPouchScreenHandler.containerSize)

  container.startOpen(playerInv.player)

  for
    i <- 0 until NetherPouchScreenHandler.rows
    j <- 0 until NetherPouchScreenHandler.columns
  do
    addSlot(new Slot(container, j + i * NetherPouchScreenHandler.columns, 8 + j * 18, 18 + i * 18))

  //if isClient(playerInv) then
  //  addSlot(new Slot(container, NetherPouchScreenHandler.containerSize - 1, 188, 18))
  //else
  addSlot(new NetherChestLockedChannelSlot(container, NetherPouchScreenHandler.containerSize - 1, 188, 18))

  for
    i <- 0 until NetherPouchScreenHandler.rows
    j <- 0 until NetherPouchScreenHandler.columns
  do
    addSlot(new Slot(playerInv, j + i * NetherPouchScreenHandler.columns + 9, 8 + j * 18, 84 + i * 18))

  for i <- 0 until 9 do
    addSlot(new Slot(playerInv, i, 8 + i * 18, 142))

  override def stillValid(player: Player): Boolean = true

  override def quickMoveStack(player: Player, i: Int): ItemStack =
    var transferredItemStack = ItemStack.EMPTY
    val slot = this.slots.get(i)
    if slot != null && slot.hasItem then
      val slotStack = slot.getItem
      transferredItemStack = slotStack.copy()
      if i < this.container.getContainerSize then
        if !this.moveItemStackTo(slotStack, this.container.getContainerSize, this.slots.size(), true) then
          return ItemStack.EMPTY
      else
        // always reject a movement into the empty slot
        return ItemStack.EMPTY
    transferredItemStack

  override def removed(player: Player): Unit =
    super.removed(player)
    container.stopOpen(player)


