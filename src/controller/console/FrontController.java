package controller.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.DIRECTION;
import model.PubSub;

public class FrontController {
    
    private PubSub pubSub;
    
    private boolean echoCommands = false;
    
    public FrontController(PubSub pubSub) {
        this.pubSub = pubSub;
    }
    
    public void start() {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);
        
        String line;
        
        System.out.print("> ");
        
        try {
            while ((line = br.readLine()) != null) {
                execute(line);
                System.out.print("> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void execute(String command) {
        if (echoCommands == true) {
            System.out.println(command);
        }
        
        if (command.compareTo("move left") == 0) {
            pubSub.emit("controller:move", DIRECTION.LEFT);
        } else if (command.compareTo("move right") == 0) {
            pubSub.emit("controller:move", DIRECTION.RIGHT);
        } else if (command.compareTo("tick") == 0) {
            pubSub.emit("tick", null);
        } else if (command.startsWith("tick")) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(command);
            m.find();

            int tickCount = Integer.parseInt(m.group());
            
            for (int i = 0; i < tickCount; i++) {
                pubSub.emit("tick", null);
            }
        } else if (command.startsWith("loadMap")) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(command);
            m.find();

            int mapId = Integer.parseInt(m.group());
            
            pubSub.emit("loadMap", mapId);
        } else if (command.compareTo("viewportSwitch") == 0) {
            pubSub.emit("viewportSwitch", null);
        } else if (command.compareTo("moveFrame up") == 0) {
            pubSub.emit("moveFrame", DIRECTION.UP);
        } else if (command.compareTo("moveFrame right") == 0) {
            pubSub.emit("moveFrame", DIRECTION.RIGHT);
        } else if (command.compareTo("moveFrame down") == 0) {
            pubSub.emit("moveFrame", DIRECTION.DOWN);
        } else if (command.compareTo("moveFrame left") == 0) {
            pubSub.emit("moveFrame", DIRECTION.LEFT);
        } else if (command.compareTo("echoCommands true") == 0) {
            echoCommands = true;
        } else if (command.compareTo("echoCommands false") == 0) {
            echoCommands = false;
        } else {
            System.out.println("Unknown command");
        }
        
        
    }
}
