## Nether Bags

Bags for [nether chest](https://modrinth.com/mod/nether-chest)

Craft it by surrounding a blaze powder with 4 leather and 4 nether bricks

![recipe](media/crafting_recipe.png)

(recipe rendered by EMI)

You can then bind this bag to a nether chest by sneak right-clicking on it.
You have to do this or else the bag won't open (no getting cheap nether bags!)

Only works with v5.0 on 1.20.1 maybe I will bother to do more 1.20 versions LATER:tm:

Needs krysztal's language scala3.1.0+scala.3.6.2

It's configurable as well, it matches Nether Chest configs, but you can also configure whether
an unbound bag can be opened to the default channel (default false, it kind of ruins balance).
I'm not your mom or anything, but I suggest that if you do this you make the recipe more expensive
(maybe by requiring a nether star like the original)

You can also config the behavior of the bag - you can make it match the normal nether chest,
and disable the binding feature (otherwise you'd be able to duplicate items).
(default false, again really ruins balance). Again probably change the recipe.

## Adding this mod to a world

This mod is compatible with nether chest worlds with one exception:
Shulker Box channels will be inaccessible (this is to prevent world save errors/book bans).
You also can't tune to Nether Bag channels either.

You can reenable tuning to Shulker Boxes with the "Allow tuning to containers" option.
(This option also affects any modded items that can't go inside shulker boxes -
if it can't go inside a Shulker box by default you can't tune to it)

You can also enable tuning to Nether Bag channels with the "Allow tuning to bags" option.

This is default false for a reason: players can very easily nest the bag and channel enough to where the world will have trouble
saving, or where it can become an item that causes a chunk ban.