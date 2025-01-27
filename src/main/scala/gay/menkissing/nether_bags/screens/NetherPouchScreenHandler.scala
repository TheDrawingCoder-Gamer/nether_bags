package gay.menkissing.nether_bags.screens

import dev.kir.netherchest.screen.NetherChestChannelSlot
import gay.menkissing.nether_bags.screens.NetherPouchScreenHandler.isClient
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.{Container, InteractionHand, SimpleContainer}
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, Slot}
import net.minecraft.world.inventory.AbstractContainerMenu.checkContainerSize
import net.minecraft.world.item.ItemStack

object NetherPouchScreenHandler:
  val rows = 3
  val columns = 9
  val containerSize = rows * columns + 1

  def fromNetwork(syncId: Int, playerInv: Inventory, buf: FriendlyByteBuf): NetherPouchScreenHandler =
    val mainHand = buf.readBoolean()
    val locked = buf.readBoolean()
    val stack = playerInv.player.getItemInHand(if mainHand then InteractionHand.MAIN_HAND else InteractionHand.OFF_HAND)
    new NetherPouchScreenHandler(syncId, playerInv, new SimpleContainer(containerSize), stack, locked)

  def apply(item: ItemStack)(syncId: Int, playerInv: Inventory) =
    new NetherPouchScreenHandler(syncId, playerInv, new SimpleContainer(containerSize), item)

  // make an unlocked one
  def makeUnlocked(item: ItemStack)(syncId: Int, playerInv: Inventory): NetherPouchScreenHandler = {
    new NetherPouchScreenHandler(syncId, playerInv, new SimpleContainer(containerSize), item, false)
  }

  def isClient(inv: Inventory): Boolean =
    inv.player == null || inv.player.level() == null || inv.player.level().isClientSide

// Copy of NetherChestScreenHandler but using mojmaps and with the slot locked
class NetherPouchScreenHandler(syncId: Int, playerInv: Inventory, val container: Container, val pouch: ItemStack, val locked: Boolean = true) extends AbstractContainerMenu(NetherBagsScreenHandlerTypes.netherPouch, syncId):
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
  if locked then
    addSlot(new NetherChestLockedChannelSlot(container, NetherPouchScreenHandler.containerSize - 1, 188, 18))
  else
    addSlot(new NetherChestChannelSlot(container, NetherPouchScreenHandler.containerSize - 1, 188, 18))
  for
    i <- 0 until NetherPouchScreenHandler.rows
    j <- 0 until NetherPouchScreenHandler.columns
  do
    addSlot(new Slot(playerInv, j + i * NetherPouchScreenHandler.columns + 9, 8 + j * 18, 84 + i * 18))

  for i <- 0 until 9 do
    if playerInv.getItem(i) == pouch then
      addSlot(new NetherChestLockedChannelSlot(playerInv, i, 8 + i * 18, 142))
    else
      addSlot(new Slot(playerInv, i, 8 + i * 18, 142))

  override def stillValid(player: Player): Boolean =
    player.getItemInHand(InteractionHand.MAIN_HAND) == pouch || player.getItemInHand(InteractionHand.OFF_HAND) == pouch

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
    if player.level() != null && !player.level().isClientSide then
      var stack = container.getItem(NetherPouchScreenHandler.containerSize - 1)
      if stack == null then
        stack = ItemStack.EMPTY
      val stackTag = CompoundTag()
      stack.save(stackTag)
      pouch.getOrCreateTag().put("key", stackTag)

    container.stopOpen(player)


