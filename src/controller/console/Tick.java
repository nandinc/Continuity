package controller.console;

import controller.console.exception.InvalidArgumentException;

public class Tick extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        if (args.length > 1) {
            // read tick count
            int tickCount = readInteger(args, 1);
            for (int i = 0; i < tickCount; i++) {
                getPubSub().emit("tick", null);
            }
        } else {
            // emit a single tick
            getPubSub().emit("tick", null);
        }
    }

}
