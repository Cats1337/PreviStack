package io.github.cats1337;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
// import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreviListener implements Listener {
    private final PreviMain plugin;

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([0-9a-fA-F]){6}");

    public static boolean yorn = true; // yorn = yes or no

    private final int MAX_ENDER_PEARLS = 8;
    private final int MAX_COBWEBS = 16;
    private final int MAX_NOTCH_APPLES = 4;
    private final int MAX_GAPPLES = 12;
    private final int MAX_TOTEMS = 3;

    public static final String PS = colorizeHex("&#3494E6[&#7163EEP&#AE31F7S&#EB00FF] ");

    private final String TOO_MANY = colorizeHex("&#CB2D3EY&#CF2F3Eo&#D3323Eu &#D7343Eh&#DB373Ea&#DF393Ev&#E33B3Ee &#E73E3Et&#EB403Eo&#EF423Eo &#F3453Em&#F7473Ea&#FB4A3En&#FF4C3Ey");
    private final String CANT_CARRY = colorizeHex("&#CB2D3EY&#CD2E3Eo&#D0303Eu &#D2313Ec&#D4333Ea&#D7343En&#D9353En&#DC373Eo&#DE383Et &#E03A3Ec&#E33B3Ea&#E53D3Er&#E73E3Er&#EA3F3Ey &#EC413Em&#EE423Eo&#F1443Er&#F3453Ee &#F6463Et&#F8483Eh&#FA493Ea&#FD4B3En &#FF4C3E&l");

    private final String EXCESS_DROP = colorizeHex(" &8Excess Dropped.");
    private final String EXCLAIM = colorizeHex("&c!");
    private final String PERIOD = colorizeHex("&c.");

    private final String EPEARL = colorizeHex(" &#00FFE0E&#18E6E3n&#2FCCE6d&#47B3E9e&#5E99ECr &#7680F0P&#8D66F3e&#A54DF6a&#BC33F9r&#D41AFCl&#EB00FFs");
    private final String CWEB = colorizeHex(" &#C4CED2C&#B7C1C4o&#AAB3B7b&#9EA6A9w&#91989Be&#848B8Eb&#777D80s");
    private final String NOTCH = colorizeHex(" &#FFE259N&#FFDC58o&#FFD657t&#FFD057c&#FFCA56h &#FFC555A&#FFBF54p&#FFB953p&#FFB353l&#FFAD52e&#FFA751s");
    private final String TOTEM = colorizeHex(" &#D1A75DT&#C1A95Bo&#B1AA59t&#A1AC58e&#91AD56m &#81AF54o&#71B052f &#60B251U&#50B34Fn&#40B54Dd&#30B64By&#20B84Ai&#10B948n&#00BB46g");
    private final String GAPP = colorizeHex(" &#F3904FG&#F49A48o&#F5A441l&#F6AE39d&#F7B832e&#F8C22Bn &#FACD24A&#FBD71Dp&#FCE116p&#FDEB0El&#FEF507e&#FFFF00s");

    private final long MESSAGE_COOLDOWN = 10000; // 10 seconds in milliseconds
    private final HashMap<UUID, Long> lastMessageTime = new HashMap<>();

    public PreviListener(PreviMain plugin) {
        this.plugin = plugin;
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String colorizeHex(String str) {
        Matcher matcher = HEX_PATTERN.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            str = str.replace(group, ChatColor.of(group.substring(1)).toString());
        }
        return colorize(str);
    }

    // Item Pickup Event
    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (yorn == false) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        // Ender Pearl Check
        if (event.getItem().getItemStack().getType() == Material.ENDER_PEARL && item != null) {
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
        if (event.getItem().getItemStack().getType() == Material.COBWEB && item != null) {
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
        if (event.getItem().getItemStack().getType() == Material.ENCHANTED_GOLDEN_APPLE && item != null) {
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
        if (event.getItem().getItemStack().getType() == Material.TOTEM_OF_UNDYING && item != null) {
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
        if (event.getItem().getItemStack().getType() == Material.GOLDEN_APPLE && item != null) {
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
    }

    // Inventory Close Event
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (yorn == false) {
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

            // Check for godlen apples
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
