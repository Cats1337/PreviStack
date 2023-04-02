package io.github.cats1337;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PreviData {
    private final PreviMain plugin;
    private final FileConfiguration config;
    private final File file;

    private final Map<UUID, Boolean> notifyStatuses;

    public PreviData(PreviMain plugin) {
        this.plugin = plugin;
        this.notifyStatuses = new HashMap<>();

        // Create the 'PreviData' folder if it doesn't exist
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        // Load the configuration file
        this.file = new File(dataFolder, "psnotify.yml");
        this.config = YamlConfiguration.loadConfiguration(file);

        load();
    }

    public void load() {
        if (!file.exists()) {
            return;
        }

        for (String uuidString : config.getKeys(false)) {
            UUID uniqueId = UUID.fromString(uuidString);
            boolean notifyEnabled = config.getBoolean(uuidString);
            notifyStatuses.put(uniqueId, notifyEnabled);
        }
    }

    public void save() {
        for (Map.Entry<UUID, Boolean> entry : notifyStatuses.entrySet()) {
            config.set(entry.getKey().toString(), entry.getValue());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save psnotify.yml");
            e.printStackTrace();
        }
    }

    public boolean getNotifyStatus(UUID uniqueId) {
        return notifyStatuses.getOrDefault(uniqueId, true);
    }

    public void setNotifyStatus(UUID uniqueId, boolean status) {
        notifyStatuses.put(uniqueId, status);
        save();
    }
}
