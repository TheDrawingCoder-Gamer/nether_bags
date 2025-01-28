package gay.menkissing.nether_bags.items

import net.minecraft.world.item.{Item, ItemStack, Rarity}
import dev.kir.netherchest.NetherChest
import dev.kir.netherchest.block.NetherChestBlock
import dev.kir.netherchest.block.entity.NetherChestBlockEntity
import dev.kir.netherchest.inventory.NetherChestInventory
import dev.kir.netherchest.screen.NetherChestScreenHandler
import gay.menkissing.nether_bags.NetherBags
import gay.menkissing.nether_bags.screens.NetherPouchScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.nbt.{CompoundTag, Tag}
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.{InteractionHand, InteractionResult, InteractionResultHolder, SimpleMenuProvider}
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.inventory.{AbstractContainerMenu, ChestMenu}

import scala.jdk.OptionConverters.*

class ItemNetherPouch extends Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)):
  override def useOn(useOnContext: UseOnContext): InteractionResult =
    val stack = useOnContext.getItemInHand
    val world = useOnContext.getLevel
    if world.isClientSide then
      return InteractionResult.PASS
    if NetherBags.config.allowChangingItem then
      return InteractionResult.PASS
    val player = useOnContext.getPlayer
    val tile   = world.getBlockEntity(useOnContext.getClickedPos)
    tile match
      case chest: NetherChestBlockEntity if player != null && player.isCrouching =>
        val resTag = CompoundTag()
        chest.getKey.save(resTag)
        stack.getOrCreateTag().put("key", resTag)
        InteractionResult.SUCCESS
      case _ => InteractionResult.PASS
  override def use(level: Level, player: Player, interactionHand: InteractionHand): InteractionResultHolder[ItemStack] = {
    val stack = player.getItemInHand(interactionHand)
    if player.isCrouching then
      return InteractionResultHolder.pass(stack)


    if !level.isClientSide then
      val tag = stack.getTag

      if !NetherBags.config.allowChangingItem && !NetherBags.config.allowUsingUnboundBags && (tag == null || !tag.contains("key", Tag.TAG_COMPOUND)) then
        val serverPlayer = player.asInstanceOf[ServerPlayer]
        serverPlayer.sendSystemMessage(Component.translatable("message.nether_bags.unbound"), true)
        return InteractionResultHolder.fail(stack)

      val item = if NetherChest.getConfig.enableMultichannelMode && tag != null && tag.contains("key", Tag.TAG_COMPOUND) then ItemStack.of(tag.getCompound("key")) else ItemStack.EMPTY
      val inventory = NetherChestInventory.viewOf(level, item).toScala
      inventory match
        case Some(inv) =>

          if NetherChest.getConfig.enableMultichannelMode then
            player.openMenu(new ExtendedScreenHandlerFactory {
              override def writeScreenOpeningData(serverPlayer: ServerPlayer, friendlyByteBuf: FriendlyByteBuf): Unit =
                friendlyByteBuf.writeBoolean(interactionHand == InteractionHand.MAIN_HAND)
                friendlyByteBuf.writeBoolean(!NetherBags.config.allowChangingItem)

              override def getDisplayName: Component =
                stack.getHoverName

              override def createMenu(i: Int, pInv: Inventory, player: Player): AbstractContainerMenu =
                new NetherPouchScreenHandler(i, pInv, inv, stack, !NetherBags.config.allowChangingItem)
            })
          else
            player.openMenu(new SimpleMenuProvider((i, playerInv, _) => ChestMenu.threeRows(i, playerInv, inv), stack.getHoverName))
        case _ => ()

    InteractionResultHolder.success(stack)





  }