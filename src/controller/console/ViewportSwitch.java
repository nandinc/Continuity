package controller.console;

import controller.console.exception.InvalidArgumentException;

public class ViewportSwitch extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        getPubSub().emit("controller:viewportSwitch", null);
    }

}
