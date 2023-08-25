package io.github.cats1337;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemUseListener extends ListenerMain{

    public ItemUseListener(PluginMain plugin) {
        super(plugin);
    }

    // On item use
    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack[] contents = player.getInventory().getContents();
            int pearlCount = 0;
            int cobwebCount = 0;
            int notchAppleCount = 0;
            int totemCount = 0;
            int gappleCount = 0;
            for (ItemStack item : contents) {
                if (item != null && item.getType() == Material.ENDER_PEARL) {
                    pearlCount += item.getAmount();
                }
                if (item != null && item.getType() == Material.COBWEB) {
                    cobwebCount += item.getAmount();
                }
                if (item != null && item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    notchAppleCount += item.getAmount();
                }
                if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                    totemCount += item.getAmount();
                }
                if (item != null && item.getType() == Material.GOLDEN_APPLE) {
                    gappleCount += item.getAmount();
                }
            }

            // Check for ender pearls
            if (pearlCount > MAX_ENDER_PEARLS) {
                int excessPearls = pearlCount - MAX_ENDER_PEARLS;
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + TOO_MANY + EPEARL + EXCLAIM + EXCESS_DROP);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }

                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getType() == Material.ENDER_PEARL) {
                        if (item.getAmount() <= excessPearls) {
                            player.getInventory().removeItem(item);
                            excessPearls -= item.getAmount();
                            player.getWorld().dropItemNaturally(player.getLocation(), item);
                        } else if (excessPearls > 0) {
                            ItemStack newItem = item.clone();
                            newItem.setAmount(excessPearls);
                            player.getInventory().removeItem(newItem);
                            excessPearls = 0;
                            player.getWorld().dropItemNaturally(player.getLocation(), newItem);
                            item.setAmount(item.getAmount() - excessPearls);
                            player.getInventory().setItem(i, item);
                            break;
                        } else {
                            break;
                        }
                    }
                }
                player.updateInventory();
            }

            // Check for cobwebs
            if (cobwebCount > MAX_COBWEBS) {
                int excessCobwebs = cobwebCount - MAX_COBWEBS;
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + TOO_MANY + CWEB + EXCLAIM + EXCESS_DROP);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getType() == Material.COBWEB) {
                        if (item.getAmount() <= excessCobwebs) {
                            player.getInventory().removeItem(item);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.COBWEB, excessCobwebs));
                            excessCobwebs -= item.getAmount();
                        } else {
                            item.setAmount(item.getAmount() - excessCobwebs);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.COBWEB, excessCobwebs));
                            player.getInventory().setItem(i, item);
                            break;
                        }
                    }
                }
                player.updateInventory();
            }

            // Check for notch apples
            if (notchAppleCount > MAX_NOTCH_APPLES) {
                int excessNotchApples = notchAppleCount - MAX_NOTCH_APPLES;
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + TOO_MANY + NOTCH + EXCLAIM + EXCESS_DROP);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                        if (item.getAmount() <= excessNotchApples) {
                            player.getInventory().removeItem(item);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, excessNotchApples));
                            excessNotchApples -= item.getAmount();
                        } else {
                            item.setAmount(item.getAmount() - excessNotchApples);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, excessNotchApples));
                            player.getInventory().setItem(i, item);
                            break;
                        }
                    }
                }
                player.updateInventory();
            }

            // Check for Totems
            if (totemCount > MAX_TOTEMS) {
                int excessTotems = totemCount - MAX_TOTEMS;
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + TOO_MANY + GAPP + EXCLAIM + EXCESS_DROP);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                        if (item.getAmount() <= excessTotems) {
                            player.getInventory().removeItem(item);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.TOTEM_OF_UNDYING, excessTotems));
                            excessTotems -= item.getAmount();
                        } else {
                            item.setAmount(item.getAmount() - excessTotems);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.TOTEM_OF_UNDYING, excessTotems));
                            player.getInventory().setItem(i, item);
                            break;
                        }
                    }
                }
                player.updateInventory();
            }

            // Check for golden apples
            if (gappleCount > MAX_GAPPLES) {
                int excessGapples = gappleCount - MAX_GAPPLES;
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + TOO_MANY + GAPP + EXCLAIM + EXCESS_DROP);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getType() == Material.GOLDEN_APPLE) {
                        if (item.getAmount() <= excessGapples) {
                            player.getInventory().removeItem(item);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.GOLDEN_APPLE, excessGapples));
                            excessGapples -= item.getAmount();
                        } else {
                            item.setAmount(item.getAmount() - excessGapples);
                            player.getWorld().dropItemNaturally(player.getLocation(),
                                    new ItemStack(Material.GOLDEN_APPLE, excessGapples));
                            player.getInventory().setItem(i, item);
                            break;
                        }
                    }
                }
                player.updateInventory();
            }

        }
    }
}
