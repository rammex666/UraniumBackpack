package fr.rammex.uraniumbackpack;

import fr.rammex.uraniumbackpack.database.sqlite.SQLiteManager;
import fr.rammex.uraniumbackpack.version.VersionHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class UraniumBackpack extends JavaPlugin {

    private VersionHandler versionHandler;
    public static UraniumBackpack instance;

    @Override
    public void onEnable() {
        instance = this;

        //JAVA VERSION
        setupVersionHandler();

        //CONFIG.YML
        saveDefaultConfig();

        //DATABASE
        getDatabaseManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupVersionHandler() {
        String version = Bukkit.getBukkitVersion();
        getLogger().info("Detected Bukkit version: " + version);
        String javaVersion = System.getProperty("java.version");
        getLogger().info("Detected Java version: " + javaVersion);

        try {
            if (javaVersion.startsWith("1.8")) {
                versionHandler = (VersionHandler) Class.forName("fr.rammex.uraniumbackpack.version.VersionHandler_1_8").newInstance();
            } else if (javaVersion.startsWith("21")) {
                versionHandler = (VersionHandler) Class.forName("fr.rammex.uraniumbackpack.version.VersionHandler_21_0").newInstance();
            } else {
                getLogger().severe("Unsupported Java version: " + javaVersion);
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            versionHandler.initialize();
        } catch (Exception e) {
            getLogger().severe("Failed to initialize version handler: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void getDatabaseManager() {
        if(getConfig().getBoolean("database.sqlite.enabled")) {
            SQLiteManager sqLiteManager = new SQLiteManager("ubackpack", new File(getDataFolder(), "data.db"));
            sqLiteManager.load();
        }
    }
}
