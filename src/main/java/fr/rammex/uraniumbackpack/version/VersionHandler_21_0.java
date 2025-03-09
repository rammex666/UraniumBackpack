package fr.rammex.uraniumbackpack.version;

import fr.rammex.uraniumbackpack.UraniumBackpack;
import fr.rammex.uraniumbackpack.version.v21.commands.TestCommand;


public class VersionHandler_21_0 implements VersionHandler {
    @Override
    public void initialize() {

    }

    private void loadCommands() {
        UraniumBackpack.instance.getCommand("test").setExecutor(new TestCommand());
    }
}
