package controller.console;

import controller.console.exception.InvalidArgumentException;

public class LoadMap extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        int mapId = readInteger(args, 1);
        getPubSub().emit("controller:loadMap", mapId);
    }

}
