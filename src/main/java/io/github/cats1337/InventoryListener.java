package io.github.cats1337;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener extends ListenerMain{

    public InventoryListener(PluginMain plugin) {
        super(plugin);
    }

    // Inventory Close Event
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!yorn) {
            return;
        }

        Player player = (Player) event.getPlayer();
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

        // Ender Pearl Check
        if (pearlCount > MAX_ENDER_PEARLS) {
            int excessPearls = pearlCount - MAX_ENDER_PEARLS;
            if (plugin.isNotifyEnabled(player.getUniqueId())) {
                player.sendMessage(PS + TOO_MANY + EPEARL + EXCLAIM + EXCESS_DROP);
            }
            // Remove excess pearls from inventory and drop them on the ground
            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && item.getType() == Material.ENDER_PEARL) {
                    if (item.getAmount() <= excessPearls) { // Remove entire stack
                        player.getInventory().removeItem(item); // Remove item from inventory
                        excessPearls -= item.getAmount(); // Subtract amount of item from excess
                        player.getWorld().dropItemNaturally(player.getLocation(), item); // Drop item on ground
                    } else if (excessPearls > 0) { // Remove excess from stack
                        ItemStack newItem = item.clone(); // Create new item stack
                        newItem.setAmount(excessPearls); // Set amount of new item stack
                        player.getInventory().removeItem(newItem); // Remove item from inventory
                        excessPearls = 0; // Set excess to 0
                        player.getWorld().dropItemNaturally(player.getLocation(), newItem); // Drop item on ground
                        item.setAmount(item.getAmount() - excessPearls); // Subtract excess from original item stack
                        player.getInventory().setItem(i, item); // Set original item stack in inventory
                        break; // Break out of loop
                    } else {
                        break;
                    }
                }
            }
        }

        // Cobweb Check
        if (cobwebCount > MAX_COBWEBS) {
            int excessCobwebs = cobwebCount - MAX_COBWEBS;
            if (plugin.isNotifyEnabled(player.getUniqueId())) {
                player.sendMessage(PS + TOO_MANY + CWEB + EXCLAIM + EXCESS_DROP);
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
        }

        // Notch Apple Check
        if (notchAppleCount > MAX_NOTCH_APPLES) {
            int excessNotchApples = notchAppleCount - MAX_NOTCH_APPLES;
            if (plugin.isNotifyEnabled(player.getUniqueId())) {
                player.sendMessage(PS + TOO_MANY + NOTCH + EXCLAIM + EXCESS_DROP);
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
        }

        // Totem Check
        if (totemCount > MAX_TOTEMS) {
            int excessTotems = totemCount - MAX_TOTEMS;
            if (plugin.isNotifyEnabled(player.getUniqueId())) {
                player.sendMessage(PS + TOO_MANY + TOTEM + EXCLAIM + EXCESS_DROP);
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
        }

        // Gapple Check
        if (gappleCount > MAX_GAPPLES) {
            int excessGapples = gappleCount - MAX_GAPPLES;
            if (plugin.isNotifyEnabled(player.getUniqueId())) {
                player.sendMessage(PS + TOO_MANY + GAPP + EXCLAIM + EXCESS_DROP);
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
        }

    }
}
