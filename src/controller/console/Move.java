package controller.console;

import model.DIRECTION;
import controller.console.exception.InvalidArgumentException;

public class Move extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        Integer stickmanId = readInteger(args, 1);
        DIRECTION direction = readDirection(args, 2);
        getPubSub().emit("controller:move:" + stickmanId.toString(), direction);
    }

}
