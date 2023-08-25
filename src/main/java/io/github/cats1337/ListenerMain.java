package io.github.cats1337;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerMain implements Listener {
    final PluginMain plugin;

    public static boolean yorn = true; // yorn = yes or no
    static final Pattern HEX_PATTERN = Pattern.compile("&#([0-9a-fA-F]){6}");

    final int MAX_ENDER_PEARLS = 8;
    final int MAX_COBWEBS = 16;
    final int MAX_NOTCH_APPLES = 4;
    final int MAX_GAPPLES = 12;
    final int MAX_TOTEMS = 3;

    public static final String PS = colorizeHex("&#3494E6[&#7163EEP&#AE31F7S&#EB00FF] ");

    final String TOO_MANY = colorizeHex("&#CB2D3EY&#CF2F3Eo&#D3323Eu &#D7343Eh&#DB373Ea&#DF393Ev&#E33B3Ee &#E73E3Et&#EB403Eo&#EF423Eo &#F3453Em&#F7473Ea&#FB4A3En&#FF4C3Ey");
    final String CANT_CARRY = colorizeHex("&#CB2D3EY&#CD2E3Eo&#D0303Eu &#D2313Ec&#D4333Ea&#D7343En&#D9353En&#DC373Eo&#DE383Et &#E03A3Ec&#E33B3Ea&#E53D3Er&#E73E3Er&#EA3F3Ey &#EC413Em&#EE423Eo&#F1443Er&#F3453Ee &#F6463Et&#F8483Eh&#FA493Ea&#FD4B3En &#FF4C3E&l");

    final String EXCESS_DROP = colorizeHex(" &8Excess Dropped.");
    final String EXCLAIM = colorizeHex("&c!");
    final String PERIOD = colorizeHex("&c.");

    final String EPEARL = colorizeHex(" &#00FFE0E&#18E6E3n&#2FCCE6d&#47B3E9e&#5E99ECr &#7680F0P&#8D66F3e&#A54DF6a&#BC33F9r&#D41AFCl&#EB00FFs");
    final String CWEB = colorizeHex(" &#C4CED2C&#B7C1C4o&#AAB3B7b&#9EA6A9w&#91989Be&#848B8Eb&#777D80s");
    final String NOTCH = colorizeHex(" &#FFE259N&#FFDC58o&#FFD657t&#FFD057c&#FFCA56h &#FFC555A&#FFBF54p&#FFB953p&#FFB353l&#FFAD52e&#FFA751s");
    final String TOTEM = colorizeHex(" &#D1A75DT&#C1A95Bo&#B1AA59t&#A1AC58e&#91AD56m &#81AF54o&#71B052f &#60B251U&#50B34Fn&#40B54Dd&#30B64By&#20B84Ai&#10B948n&#00BB46g");
    final String GAPP = colorizeHex(" &#F3904FG&#F49A48o&#F5A441l&#F6AE39d&#F7B832e&#F8C22Bn &#FACD24A&#FBD71Dp&#FCE116p&#FDEB0El&#FEF507e&#FFFF00s");

    final long MESSAGE_COOLDOWN = 10000; // 10 seconds in milliseconds
    final HashMap<UUID, Long> lastMessageTime = new HashMap<>();

    public ListenerMain(PluginMain plugin) {
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



    // Cooldown on Instant Damage Potions (10 seconds)
}
