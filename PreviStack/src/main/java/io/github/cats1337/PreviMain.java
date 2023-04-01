package io.github.cats1337;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PreviMain extends JavaPlugin implements Listener {
    static final Logger LOGGER = Logger.getLogger("previstack");
    private PreviData previData;

    @Override
    public void onEnable() {
        LOGGER.info("PreviStack is enabled!");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PreviListener(this), this);
        getCommand("psnotify").setExecutor(new PreviCommands(this));

        previData = new PreviData(this);
        previData.load();
    }

    @Override
    public void onDisable() {
        LOGGER.info("PreviStack is disabled!");
        previData.save();
    }

    public boolean isNotifyEnabled(UUID uniqueId) {
        return previData.getNotifyStatus(uniqueId);
    }

    public void setNotifyEnabled(UUID uniqueId, boolean b) {
        previData.setNotifyStatus(uniqueId, b);
    }
}
