package io.github.cats1337;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class PickUpListener extends ListenerMain{

    public PickUpListener(PluginMain plugin) {
        super(plugin);
    }

    // Item Pickup Event
    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!yorn) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        // Ender Pearl Check
        if (event.getItem().getItemStack().getType() == Material.ENDER_PEARL) {
            Player player = (Player) event.getEntity();
            int enderPearlCount = 0;
            int epPickUp = item.getAmount();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.ENDER_PEARL) {
                    enderPearlCount += itemStack.getAmount();
                }
            }
            if (enderPearlCount < MAX_ENDER_PEARLS) {
                if (enderPearlCount + epPickUp > MAX_ENDER_PEARLS) {
                    int excess = enderPearlCount + epPickUp - MAX_ENDER_PEARLS;
                    event.getItem().setItemStack(new ItemStack(Material.ENDER_PEARL, epPickUp - excess));
                    player.getWorld().dropItemNaturally(player.getLocation(),
                            new ItemStack(Material.ENDER_PEARL, excess));
                }
            } else {
                event.setCancelled(true);
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + CANT_CARRY + MAX_ENDER_PEARLS + EPEARL + PERIOD);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }

        // Cobweb Check
        if (event.getItem().getItemStack().getType() == Material.COBWEB) {
            Player player = (Player) event.getEntity();
            int cobwebCount = 0;
            int cwPickUp = item.getAmount();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.COBWEB) {
                    cobwebCount += itemStack.getAmount();
                }
            }
            if (cobwebCount < MAX_COBWEBS) {
                if (cobwebCount + cwPickUp > MAX_COBWEBS) {
                    int excess = cobwebCount + cwPickUp - MAX_COBWEBS;
                    event.getItem().setItemStack(new ItemStack(Material.COBWEB, cwPickUp - excess));
                    player.getWorld().dropItemNaturally(player.getLocation(),
                            new ItemStack(Material.COBWEB, excess));
                }
            } else {
                event.setCancelled(true);
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + CANT_CARRY + MAX_COBWEBS + CWEB + PERIOD);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }

        // Notch Apple Check
        if (event.getItem().getItemStack().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            Player player = (Player) event.getEntity();
            int notchCount = 0;
            int naPickUp = item.getAmount();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    notchCount += itemStack.getAmount();
                }
            }
            if (notchCount < MAX_NOTCH_APPLES) {
                if (notchCount + naPickUp > MAX_NOTCH_APPLES) {
                    int excess = notchCount + naPickUp - MAX_NOTCH_APPLES;
                    event.getItem().setItemStack(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, naPickUp - excess));
                    player.getWorld().dropItemNaturally(player.getLocation(),
                            new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, excess));
                }
            } else {
                event.setCancelled(true);
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + CANT_CARRY + MAX_NOTCH_APPLES + NOTCH + PERIOD);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }

        // Totem Check
        if (event.getItem().getItemStack().getType() == Material.TOTEM_OF_UNDYING) {
            Player player = (Player) event.getEntity();
            int totemCount = 0;
            int toPickUp = item.getAmount();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.TOTEM_OF_UNDYING) {
                    totemCount += itemStack.getAmount();
                }
            }
            if (totemCount < MAX_GAPPLES) {
                if (totemCount + toPickUp > MAX_GAPPLES) {
                    int excess = totemCount + toPickUp - MAX_GAPPLES;
                    event.getItem().setItemStack(new ItemStack(Material.TOTEM_OF_UNDYING, toPickUp - excess));
                    player.getWorld().dropItemNaturally(player.getLocation(),
                            new ItemStack(Material.TOTEM_OF_UNDYING, excess));
                }
            } else {
                event.setCancelled(true);
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + CANT_CARRY + MAX_GAPPLES + TOTEM + PERIOD);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }

        // Golden Apple Check
        if (event.getItem().getItemStack().getType() == Material.GOLDEN_APPLE) {
            Player player = (Player) event.getEntity();
            int gappleCount = 0;
            int gaPickUp = item.getAmount();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() == Material.GOLDEN_APPLE) {
                    gappleCount += itemStack.getAmount();
                }
            }
            if (gappleCount < MAX_GAPPLES) {
                if (gappleCount + gaPickUp > MAX_GAPPLES) {
                    int excess = gappleCount + gaPickUp - MAX_GAPPLES;
                    event.getItem().setItemStack(new ItemStack(Material.GOLDEN_APPLE, gaPickUp - excess));
                    player.getWorld().dropItemNaturally(player.getLocation(),
                            new ItemStack(Material.GOLDEN_APPLE, excess));
                }
            } else {
                event.setCancelled(true);
                if (!lastMessageTime.containsKey(player.getUniqueId()) ||
                        System.currentTimeMillis() - lastMessageTime.get(player.getUniqueId()) >= MESSAGE_COOLDOWN) {
                    if (plugin.isNotifyEnabled(player.getUniqueId())) {
                        player.sendMessage(PS + CANT_CARRY + MAX_GAPPLES + GAPP + PERIOD);
                    }
                    lastMessageTime.put(player.getUniqueId(), System.currentTimeMillis());
                }
            }
        }

        // Convert slow falling arrows to normal arrows
        if (event.getItem().getItemStack().getType() == Material.TIPPED_ARROW) {
            PotionMeta meta = (PotionMeta) event.getItem().getItemStack().getItemMeta();
            assert meta != null;
            if (meta.getBasePotionData().getType() == PotionType.SLOW_FALLING) {
                event.getItem().setItemStack(new ItemStack(Material.ARROW, event.getItem().getItemStack().getAmount()));

            }
        }

    }
}
