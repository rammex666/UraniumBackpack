package fr.rammex.uraniumbackpack.version;

import fr.rammex.uraniumbackpack.UraniumBackpack;
import fr.rammex.uraniumbackpack.version.core.commands.UraniumBackpackCommand;
import fr.rammex.uraniumbackpack.version.core.events.UBackPackPlayerListener;
import org.bukkit.Bukkit;

public class VersionHandler_1_8 implements VersionHandler {

    @Override
    public void initialize() {
        loadCommands();
        loadEvents();
        System.out.println("1.8");
    }

    private void loadCommands() {
        UraniumBackpack.instance.getCommand("uraniumbackpack").setExecutor(new UraniumBackpackCommand());
    }

    private void loadEvents() {
        Bukkit.getPluginManager().registerEvents(new UBackPackPlayerListener(), UraniumBackpack.instance);
    }
}
