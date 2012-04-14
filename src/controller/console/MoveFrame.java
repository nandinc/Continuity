package controller.console;

import model.DIRECTION;
import controller.console.exception.InvalidArgumentException;

public class MoveFrame extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        DIRECTION direction = readDirection(args, 1);
        getPubSub().emit("controller:moveFrame", direction);
    }

}
