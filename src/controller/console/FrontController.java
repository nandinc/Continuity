package controller.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import controller.console.exception.InvalidArgumentException;

import debug.Logger;

import model.PubSub;

/**
 * A felhasználó parancsait kezelő felület
 * 
 * @responsibility Felhasználói parancsok fogadása, értelemezése, továbbítása
 */
public class FrontController {
    
    /**
     * Bemeneti parancsok ismétlésének engedélyezése
     */
    private boolean echoCommands = false;
    
    /**
     * Használható parancsok
     */
    private Map<String, Command> commands = new HashMap<String, Command>();
    
    /**
     * Inicializálás
     * 
     * @param pubSub kommunikációs csatorna
     */
    public FrontController(PubSub pubSub) {
        Command move = new Move();
        Command tick = new Tick();
        Command loadMap = new LoadMap();
        Command viewportSwitch = new ViewportSwitch();
        Command moveFrame = new MoveFrame();
        Command timer = new Timer();
        
        move.setPubSub(pubSub);
        tick.setPubSub(pubSub);
        loadMap.setPubSub(pubSub);
        viewportSwitch.setPubSub(pubSub);
        moveFrame.setPubSub(pubSub);
        timer.setPubSub(pubSub);
        
        commands.put("move", move);
        commands.put("tick", tick);
        commands.put("loadMap", loadMap);
        commands.put("viewportSwitch", viewportSwitch);
        commands.put("moveFrame", moveFrame);
        commands.put("timerStart", timer);
        commands.put("timerStop", timer);
    }
    
    /**
     * Parancsok olvasásának indítása a sztandard bemenetről
     */
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
    
    /**
     * A kapott parancs végrehajtása
     * 
     * @param commandString bejövő parancs
     */
    private void execute(String commandString) {
        if (commandString.compareTo("echoCommands true") == 0) {
            echoCommands = true;
        } else if (commandString.compareTo("echoCommands false") == 0) {
            echoCommands = false;
        }
        
        if (echoCommands == true) {
            System.out.println(commandString);
        }
        
        if (commandString.compareTo("echoCommands true") == 0 || commandString.compareTo("echoCommands false") == 0) {
            // Do nothing, we've already done our job
        }
        else if (commandString.compareTo("exit") == 0) {
            System.exit(0);
        } else {
            String[] args = commandString.split(" ");
            String commandName = args[0];
            
            if (commands.containsKey(commandName)) {
                Command command = commands.get(commandName);
                try {
                    command.execute(args);
                } catch (InvalidArgumentException e) {
                    System.out.print("Invalid command: ");
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Unknown command");
            }
        }
        
        Logger.flush();
    }
}
