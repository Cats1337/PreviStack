package io.github.cats1337;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PreviCommands implements CommandExecutor {
    
    private PreviMain plugin;
    public static final String PS = PreviListener.colorizeHex("&#3494E6[&#7163EEP&#AE31F7S&#EB00FF] ");
    
    public PreviCommands(PreviMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("psnotify")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
                return true;
            }
            Player player = (Player) sender;
            if (plugin.isNotifyEnabled(player.getUniqueId())) {
                plugin.setNotifyEnabled(player.getUniqueId(), false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PS + "&8Limit notifications &cdisabled&8."));
            } else {
                plugin.setNotifyEnabled(player.getUniqueId(), true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', PS + "&8Limit notifications &aenabled&8."));
            }
            return true;
        }
        
        if (command.getName().equalsIgnoreCase("psdisable")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage( ChatColor.translateAlternateColorCodes('&', PS + "&8PreviStack &cdisabled&8."));
                PreviListener.yorn = false;
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("psenable")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PS + "&8PreviStack &aenabled&8."));
                PreviListener.yorn = true;
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase("psstatus")) {
            if(!(sender instanceof Player)) {
                if(PreviListener.yorn == true) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PS + "&8PreviStack is currently &aenabled&8."));
                }
                if(PreviListener.yorn == false) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PS + "&8PreviStack is currently &cdisabled&8."));
                }
                return true;
            }
        }
        return true;
    }
}
