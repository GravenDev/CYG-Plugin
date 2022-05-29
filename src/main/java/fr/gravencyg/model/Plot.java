package fr.gravencyg.model;

import fr.gravencyg.utils.CEvent;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.gravencyg.utils.LocationUtils.defaultSpawn;

public class Plot {

    private String name;

    private Map<String, String> events = new HashMap<>();

    private Map<String, String> locations = new HashMap<>();

    private Map<String, String> locationsSet = new HashMap<>();

    private Map<String, String> messages = new HashMap<>();

    private Map<String, String> messagesSet = new HashMap<>();

    private Map<String, String> variables = new HashMap<>();

    private List<String> friends = new ArrayList<>();

    private List<String> blacklisted = new ArrayList<>();

    private String spawn = defaultSpawn();
    private String code = defaultSpawn();

    private GameConfig gameConfig;

    private VerifyGameConfig verifyGameConfig;

    public Plot(Player player) {
        this.gameConfig = new GameConfig(player);
        this.verifyGameConfig = new VerifyGameConfig();

        for(CEvent event : CEvent.values()) {
            this.events.put(event.getEventName(), null);
        }
    }

    public void updateEvent(String key, String value) {
        if(this.events.containsKey(key))
        {
            this.events.remove(key);
        }

        this.events.put(key, value);
    }

    public void addLocationSet(String key, String value) { locationsSet.put(key, value); }

    public void addMessageSet(String key, String value) { messagesSet.put(key, value); }

    public void addLocation(String key, String value) { locations.put(key, value); }

    public void addVariable(String key, String value) { variables.put(key, value); }

    public void addMessage(String key, String value) { messages.put(key, value); }

    public void removeLocation(String key) { locations.remove(key); }

    public void removeMessage(String key) { messages.remove(key); }

    public boolean checkLocationExist(String key) { return locations.containsKey(key); }

    public boolean checkMessages(String key) { return messages.containsKey(key); }

    public boolean checkVariables(String key) {
        if(variables == null) variables = new HashMap<>();
        return variables.containsKey(key); }

    public void removeEvent(String key) {
        this.events.remove(key);
    }

    public void removeVariable(String key) {
        this.variables.remove(key);
    }

    public Map<String, String> getEvents() {
        return events;
    }

    public Map<String, String> getLocations() { return locations; }

    public Map<String, String> getLocationsSet() { return locationsSet; }

    public Map<String, String> getMessageSet() { return messagesSet; }

    public String getName() { return gameConfig.getGameName(); }

    public GameConfig getConfig() { return gameConfig; }

    public VerifyGameConfig getVerifyConfig() { return verifyGameConfig; }

    public boolean hasEvent(String key) { return events.containsKey(key); }

    public List<String> getFriends() { return friends; }

    public void setSpawn(String spawn) { this.spawn = spawn; }

    public void setCode(String code) { this.code = code; }

    public Location getSpawn(String uuid) {
        if(spawn == null)
        {
            return LocationUtils.fromStringToLoc(uuid, LocationUtils.defaultSpawn());
        }

        return LocationUtils.fromStringToLocComplete(uuid, spawn);

    }

    public Location getCode(String uuid) {
        if(code == null)
        {
            return LocationUtils.fromStringToLoc(uuid, LocationUtils.defaultSpawn());
        }

        return LocationUtils.fromStringToLocComplete(uuid, code);

    }

    public Map<String, String> getMessages() { return messages; }

    public Map<String, String> getVariables() { return variables; }


}
