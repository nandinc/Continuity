package controller.console;

import controller.console.exception.InvalidArgumentException;

public class Timer extends Command {

    @Override
    public void execute(String[] args) throws InvalidArgumentException {
        if (args[0].compareTo("timerStart") == 0) {
            getPubSub().emit("controller:timer", true);
        } else if (args[0].compareTo("timerStop") == 0){
            getPubSub().emit("controller:timer", false);
        } else {
            throw new InvalidArgumentException("Valid timer commands are: timerStart, timerStop");
        }
    }

}
