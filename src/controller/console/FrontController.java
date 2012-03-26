package controller.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.DIRECTION;
import model.PubSub;

public class FrontController {
    
    private PubSub pubSub;
    
    public FrontController(PubSub pubSub) {
        this.pubSub = pubSub;
    }
    
    public void start() {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);
        
        String line;
        
        try {
            while ((line = br.readLine()) != null) {
                execute(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void execute(String command) {
        if (command.compareTo("move left") == 0) {
            pubSub.emit("controller:move", DIRECTION.LEFT);
        } else if (command.compareTo("move right") == 0) {
            pubSub.emit("controller:move", DIRECTION.RIGHT);
        } else if (command.compareTo("tick") == 0) {
            pubSub.emit("tick", null);
        }
        
        
    }
}
