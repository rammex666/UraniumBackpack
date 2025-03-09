package fr.rammex.uraniumbackpack.version;

import fr.rammex.uraniumbackpack.UraniumBackpack;
import fr.rammex.uraniumbackpack.version.v1_8.commands.TestCommand;

public class VersionHandler_1_8 implements VersionHandler {

    @Override
    public void initialize() {
        loadCommands();
    }

    private void loadCommands() {
        UraniumBackpack.instance.getCommand("test").setExecutor(new TestCommand());
    }
}
