package controller.console;

import model.DIRECTION;
import controller.console.exception.InvalidArgumentException;

public class Move extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        DIRECTION direction = readDirection(args, 1);
        getPubSub().emit("controller:move", direction);
    }

}
