package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import model.exception.MapErrorException;
import model.exception.MapNotFoundException;

/**
 * Azonosító alapján Pályákat szolgáltat.
 * Létrehozza a pályát, majd az azonosító alapján feltölti
 * a megfelelő objektumokkal.
 * 
 * @responsibility Felelős a pályák létrehozásáért, bennük a keretek és az elemek elhelyezéséért.
 */
public class MapFactory {

    /**
     * A String -> FrameItem fordítást segítő szótár
     */
    private java.util.Map<String, Class<? extends FrameItem>> itemNameToClass = new HashMap<String, Class<? extends FrameItem>>();

    /**
     * Szótár inicializálása
     */
    public MapFactory() {
        // init itemNameToClass field
        // this logic could be replaced by a more dynamic resource
        // loading, but I like to KISS
        itemNameToClass.put("Door", Door.class);
        itemNameToClass.put("Key", Key.class);
        itemNameToClass.put("Platform", Platform.class);
        itemNameToClass.put("Stickman", Stickman.class);
    }

    /**
     * Létrehozza a megadott azonosítójú pályát
     * és feltölti elemekkel.
     * 
     * @param mapId Pálya azonosítója
     * @param pubSub PubSub minden elemnek beállítandó kommunikációs csatorna
     * @throws MapNotFoundException Ha nem található a megadott azonosítójú pálya
     */
    public Map getMap(int mapId, PubSub pubSub) throws MapNotFoundException {
        // open map stream
        InputStream mapStream = getMapStream(mapId);
        BufferedReader mapReader = new BufferedReader(new InputStreamReader(mapStream));

        // create map
        Map map = new Map();
        map.setPubSub(pubSub);

        // populate map via opened file
        String line;
        try {
            while ((line = mapReader.readLine()) != null) {
                FrameItem item = getItemByString(line);
                item.setPubSub(pubSub);
                map.addItem(item);
            }
        } catch (IOException e) {
            System.err.println("Error while reading map file");
            e.printStackTrace();
        } catch (MapErrorException e) {
            System.err.println("Error while reading map file");
            e.printStackTrace();
        }

        map.fillFrameGap();
        return map;
    }

    /**
     * Egy InputStream-et nyit a megadott pálya azonosító által
     * kijelölt pálya fájlra
     * 
     * @see http://docs.oracle.com/javase/1.4.2/docs/guide/resources/resources.html
     * @param mapId Pálya azonosítója
     * @return Pályát olvasó bemeneti folyam
     * @throws MapNotFoundException
     */
    private InputStream getMapStream(Integer mapId) throws MapNotFoundException {
        String mapFileName = "/resources/maps/map_" + mapId.toString();
        InputStream mapStream = ClassLoader.class.getResourceAsStream(mapFileName);

        if (mapStream == null) {
            System.err.println(mapFileName);
            throw new MapNotFoundException();
        }

        return mapStream;
    }

    /**
     * A kapott String alapján előállít egy megfelelő FrameItem-et. 
     */
    private FrameItem getItemByString(String itemDescriber) throws MapErrorException {
        String[] parameters = itemDescriber.split("\t");

        if (itemNameToClass.containsKey(parameters[0]) == false) {
            throw new MapErrorException("No item found for name: '" + parameters[0] + "'");
        }

        Class<? extends FrameItem> itemClass = itemNameToClass.get(parameters[0]);
        FrameItem item = null;
        try {
            // if items may receive additional arguments
            // next to area arguments, they may be passed in here
            // this logic is omitted because I like to KISS
            item = itemClass.newInstance();
        } catch (InstantiationException e) {
            throw new MapErrorException(e);
        } catch (IllegalAccessException e) {
            throw new MapErrorException(e);
        }

        Area itemArea = new Area();
        try {
            if (parameters.length >= 2) {
                // has x coordinate
                itemArea.setX(Integer.parseInt(parameters[1]));
            }

            if (parameters.length >= 3) {
                // has y coordinate
                itemArea.setY(Integer.parseInt(parameters[2]));
            }

            if (parameters.length >= 4) {
                // has width
                itemArea.setWidth(Integer.parseInt(parameters[3]));
            }

            if (parameters.length >= 5) {
                // has x coordinate
                itemArea.setHeight(Integer.parseInt(parameters[4]));
            }
            
            if (parameters.length >= 6) {
                item.setAdditionalParameters(parameters);
            }

        } catch (NumberFormatException e) {
            throw new MapErrorException(e);
        }

        item.setArea(itemArea);

        return item;
    }
}
