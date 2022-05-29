package fr.gravencyg.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gravencyg.CYG;
import fr.gravencyg.model.GameConfig;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.CRank;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CYGDataManager {

    private CYG main;

    private Map<String, Storage> storages;

    public CYGDataManager(CYG main) {
        this.main = main;
        this.storages = new HashMap<>();

        loadStorage();
    }

    public void loadStorage() {
        File folder = new File("plugins/CYG/storage/");
        File [] files = folder.listFiles();
        for (int i = 0; i < files.length; i++){
            if (files[i].isFile()){ //this line weeds out other directories/folders
                Storage storageData = (Storage) loadJson(Storage.class, files[i]);
                storages.put(storageData.getDisplayName(), storageData);
            }
        }

        System.out.println(storages.size() + " profiles chargés");
    }

    public Storage getStorageByUUID(String name) {
        String fullName = name.split("#")[0];
        for(Map.Entry<String, Storage> storage : storages.entrySet())
        {
            if (storage.getValue().getUUID().equalsIgnoreCase(fullName))
            {
                return storage.getValue();
            }
        }
        return new Storage();
    }

    public void createProfileIfNotExist(Player player) {

        System.out.println("Create new profile for " + player.getName() + " uuid: " + player.getUniqueId().toString());

        // create folder if not exist
        File folder = new File("plugins/CYG/storage/");
        if (!folder.exists()) folder.mkdir();

        // check if file already exist
        File file = new File("plugins/CYG/storage/" + player.getUniqueId().toString() + ".json");

        if (!file.exists()){
            Storage storage = new Storage(player);
            this.storages.put(player.getName(), storage);
            this.save(player);
        }

        Storage storage = getStorageByUUID(player.getUniqueId().toString());
        storage.setPseudo(player);
        this.save(player);

    }

    private void saveJson(Object object, Type type, File out) {

        OutputStream outputStream = null;
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting()
                .create();
        try {
            outputStream = new FileOutputStream(out);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            gson.toJson(object, type, bufferedWriter);
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }

    }


    private Object loadJson(Type type, File file) {
        Object jsonData = null;

        InputStream inputStream = null;
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting()
                .create();
        try {
            inputStream = new FileInputStream(file);
            InputStreamReader streamReader;
            streamReader = new InputStreamReader(inputStream, "UTF-8");

            jsonData = gson.fromJson(streamReader, type);
            streamReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return jsonData;
    }

    public CRank getRank(Player player) {
        CRank rank = CRank.JOUEUR;

        for(CRank r : CRank.values())
        {
            if(r.name().toLowerCase().equalsIgnoreCase(storages.get(player.getName()).getRank()))
            {
                rank = r;
            }
        }

        return rank;
    }

    public void addLocation(Player player, String name, Location location) {
        Storage playerStorage = storages.get(player.getName());
        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            if(playerStorage.getPlot(player.getWorld().getName()).checkLocationExist(name)) {
                player.sendMessage("\n\nZone §e"+name+"§r éxistante ! Tape /locations remove §e"+name+"§e\n\n");
                return;
            }

            playerStorage.getPlot(player.getWorld().getName()).addLocation(
              name,
              LocationUtils.fromLocToStringComplete(location)
            );
        }
        save(player);
        player.sendMessage("\n\nNouvelle zone §e[§r"+name+"§e] §aajoutée§r en " + LocationUtils.fromLocToString2(location));

    }

    public void addMessage(Player player, String name, String messageContent) {
        Storage playerStorage = storages.get(player.getName());
        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            if(playerStorage.getPlot(player.getWorld().getName()).checkMessages(name)) {
                player.sendMessage("\n\nMessage §e"+name+"§r éxistant ! Tape /messages remove §e"+name+"§e\n\n");
                return;
            }

            playerStorage.getPlot(player.getWorld().getName()).addMessage(
                    name,
                    messageContent
            );
        }
        save(player);
        player.sendMessage("\n\nNouveau message §e[§r"+name+"§e] §aajoutée§r ! \n§r" + messageContent.replace("&","§"));

    }


    public void addLocationSet(Player player, Location location, String locName) {
        Storage playerStorage = storages.get(player.getName());
        if(playerStorage.getPlot(player.getWorld().getName()) != null) {
            playerStorage.getPlot(player.getWorld().getName()).addLocationSet(LocationUtils.fromLocToString(location), locName);
        }
        save(player);
    }

    public void addLocationSet(String ownerUUID, Location location, String locName) {
        Storage playerStorage = getStorageByUUID(ownerUUID);
        if(playerStorage.getPlot(location.getWorld().getName()) != null) {
            playerStorage.getPlot(location.getWorld().getName()).addLocationSet(LocationUtils.fromLocToString(location), locName);
        }
        save(playerStorage.getDisplayName(), ownerUUID);

    }

    public void addMessageSet(Player player, Location location, String message) {
        Storage playerStorage = storages.get(player.getName());
        if(playerStorage.getPlot(player.getWorld().getName()) != null) {
            playerStorage.getPlot(player.getWorld().getName()).addMessageSet(LocationUtils.fromLocToString(location), message);
        }
        save(player);

    }

    public void addMessageSet(String string, Location location, String message) {
        Storage playerStorage = getStorageByUUID(string);
        if(playerStorage.getPlot(location.getWorld().getName()) != null) {
            playerStorage.getPlot(location.getWorld().getName()).addMessageSet(LocationUtils.fromLocToString(location), message);
        }
        save(playerStorage.getDisplayName(), string);

    }

    public void removeLocation(Player player, String locationName) {
        Storage playerStorage = storages.get(player.getName());
        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            if(!playerStorage.getPlot(player.getWorld().getName()).checkLocationExist(locationName)) {
                player.sendMessage("\n\nZone §e"+locationName+"§r inéxistante");
                return;
            }

            playerStorage.getPlot(player.getWorld().getName()).removeLocation(locationName);
        }
        save(player);
        player.sendMessage("\n\nSuppression de la zone §c[§r"+locationName+"§c] !");

    }

    public void removeMessage(Player player, String messageName) {
        Storage playerStorage = storages.get(player.getName());
        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            if(!playerStorage.getPlot(player.getWorld().getName()).checkMessages(messageName)) {
                player.sendMessage("\n\nMessage §e"+messageName+"§r inéxistant");
                return;
            }

            playerStorage.getPlot(player.getWorld().getName()).removeMessage(messageName);
        }
        save(player);
        player.sendMessage("\n\nSuppression du message §c[§r"+messageName+"§c] !");

    }

    public void removeAllLocations(Player player) {
        Storage playerStorage = storages.get(player.getName());

        if(playerStorage.getPlot(player.getWorld().getName()) != null) {
            playerStorage.getPlot(player.getWorld().getName()).getLocationsSet().clear();
            playerStorage.getPlot(player.getWorld().getName()).getLocations().clear();
        }
        save(player);
        player.sendMessage("\n\n§9Toutes les locations ont été supprimées !");

    }

    public void removeAllMessages(Player player) {
        Storage playerStorage = storages.get(player.getName());

        if(playerStorage.getPlot(player.getWorld().getName()) != null) {
            playerStorage.getPlot(player.getWorld().getName()).getMessageSet().clear();
            playerStorage.getPlot(player.getWorld().getName()).getMessages().clear();
        }
        save(player);
        player.sendMessage("\n\n§9Tout les messages ont été supprimés !");

    }

    public void updateEvent(Player player, String key, String value) {
        getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName()).updateEvent(key, value);
        save(player);
    }

    public void removeEvent(Player player, Block block) {
        List<String> eventToRemove = new ArrayList<>();

       for (Map.Entry<String, String> event : storages.get(player.getName()).getPlot(player.getWorld().getName()).getEvents().entrySet()) {
           if (event.getValue().equalsIgnoreCase(LocationUtils.fromLocToString(block.getLocation())))
           {
               player.sendMessage(ChatColor.GRAY+"Evenement " + ChatColor.BLUE+ event.getKey() + "()" + ChatColor.BLUE +" supprimé§r du bloc en " +LocationUtils.fromLocToString(block.getLocation()));
               eventToRemove.add(event.getKey());

               main.getHologramManager().removeHologram(block);
               storages.get(player.getName()).getPlot(player.getWorld().getName()).removeEvent(event.getKey());
           }
       }

       eventToRemove.forEach(storages.get(player.getName()).getPlot(player.getWorld().getName()).getEvents()::remove);
    }

    public void removeEvent(Player player, String uuid, Block block) {
        List<String> eventToRemove = new ArrayList<>();

        for (Map.Entry<String, String> event : getStorageByUUID(uuid).getPlot(player.getWorld().getName()).getEvents().entrySet()) {
            if (event.getValue().equalsIgnoreCase(LocationUtils.fromLocToString(block.getLocation())))
            {
                player.sendMessage(ChatColor.GRAY+"Evenement " + ChatColor.BLUE+ event.getKey() + "()" + ChatColor.BLUE +" supprimé§r du bloc en " +LocationUtils.fromLocToString(block.getLocation()));
                eventToRemove.add(event.getKey());

                main.getHologramManager().removeHologram(block);
                getStorageByUUID(uuid).getPlot(player.getWorld().getName()).removeEvent(event.getKey());
            }
        }

        eventToRemove.forEach(storages.get(player.getName()).getPlot(player.getWorld().getName()).getEvents()::remove);
    }

    public void save(Player player) {
        File file = new File("plugins/CYG/storage/" + player.getUniqueId().toString() + ".json");
        saveJson(storages.get(player.getName()), Storage.class, file);
    }


    public void save(String name, String uuid) {
        File file = new File("plugins/CYG/storage/" + uuid + ".json");
        saveJson(storages.get(name), Storage.class, file);
    }


    public Map<String, Storage> getStorages() {
        return storages;
    }

    public Plot getPlotFromWorldLocationOf(Player player) {
        Plot plot = new Plot(player);

        for (Map.Entry<String, Storage> storage : storages.entrySet()) {
            if(player.getWorld().getName().contains(storage.getValue().getUUID()))
            {
                return storage.getValue().getPlot(player.getWorld().getName());
            }
        }

        return plot;
    }

    public int getLevel(String name) {
        return getStorages().get(name).getLevel();
    }

    public int getTrophys(String name) {
        return getStorages().get(name).getTrophys();
    }

    public GameConfig getConfig(String name) { return getStorages().get(name).getConfig(name); }


    public void setSpawn(Player player) {
        storages.get(player.getName()).getPlot(player.getWorld().getName()).setSpawn(LocationUtils.fromLocToStringComplete(player.getLocation()));
        save(player);
    }

    public void setGameName(Player player, String gameName) {
        storages.get(player.getName()).getPlot(player.getWorld().getName()).getConfig().setName(gameName);
        save(player);
    }

    public void setCode(Player player) {
        storages.get(player.getName()).getPlot(player.getWorld().getName()).setCode(LocationUtils.fromLocToStringComplete(player.getLocation()));
        save(player);
    }

    public void addTrophy(Player player, int amount) {
        Storage storage = storages.get(player.getName());
        storage.addTrophy(amount);
        save(player);
        refreshLevel(player.getName());
    }

    public void setLevel(String pseudo, String newLevel) {
        int newLevelI = Integer.parseInt(newLevel);
        int trophys = CLevel.values()[newLevelI-1].getTrophy();

        Storage storage = storages.get(pseudo);
        storage.setTrophy(trophys);

        refreshLevel(pseudo);
    }

    public int getBorderSize(Player player) {
        return CLevel.findLevelByTrophy(getTrophys(player.getName())).getPlotSize().getBorderSize();
    }

    public int getMaxPlotPerPlayer(Player player) {
        CRank rank = getRank(player);
        int level = getLevel(player.getName());
        int maxPlots = 1;

        System.out.println(level);
        if(level >= 10) maxPlots = 2;
        if(rank == CRank.PREMIUM || rank.isMod()) maxPlots = 3;

        return maxPlots;
    }

    public void refreshLevel(String pseudo) {
        Storage storage = storages.get(pseudo);
        int trophys = storage.getTrophys();
        CLevel lvl = CLevel.findLevelByTrophy(trophys);

        storage.setLevel(lvl.getRealLevel());
        save(pseudo, main.getDataManager().getStorages().get(pseudo).getUUID());

        if(Bukkit.getPlayer(pseudo) != null) {
            Player player = Bukkit.getPlayer(pseudo);
            main.getScoreboardManager().updatePlayerLevel(player);
            main.getScoreboardManager().updatePlayerTrophy(player);
        }
    }

    public Location getSpawn(Player player) { return storages.get(player.getName()).getPlot(player.getWorld().getName()).getSpawn(player.getUniqueId().toString()); }

    public Location getSpawn(String name, String uuid) { return storages.get(name).getPlot(uuid).getSpawn(uuid); }

    public Location getCode(Player player) { return storages.get(player.getName()).getPlot(player.getWorld().getName()).getCode(player.getUniqueId().toString()); }



}
