package fr.gravencyg.game;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.team.Team;
import fr.gravencyg.model.MaterialAndData;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.model.VerifyGameConfig;
import fr.gravencyg.utils.CEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CYGameManager {

    private CYG main;

    private Map<String, List<CYGame>> games = new HashMap<>();
    private Map<Integer, Storage> verifyGames = new HashMap<>();
    private List<Storage> onLoopEnable = new ArrayList<>();

    public CYGameManager(CYG main)
    {

        this.main = main;

        for(Map.Entry<String, Storage> st : main.getDataManager().getStorages().entrySet())
        {
            createStorageFullData(st.getValue());
        }

    }

    public void createStorageFullData(Storage st) {
        for(Plot plot : st.getPlots()) {
            if (plot != null) {
                VerifyGameConfig verifyGameConfig = plot.getVerifyConfig();
                if (verifyGameConfig.isVerify())
               this.verifyGames.put(verifyGameConfig.getSlot(), st);
            }
        }
    }

    public void create(String ownerName, int gameID){
        Storage storage = main.getDataManager().getStorages().get(ownerName);

        CYGame game = new CYGame(storage, gameID);

        if(!games.containsKey(ownerName))
        {
            games.put(ownerName, new ArrayList<>());
        }

        List<CYGame> g = games.get(ownerName);
        g.add(game);
        games.put(ownerName, g);
    }

    public void createTeam(CYGame game, Team team) {
        game.createTeam(team);
    }

    public void join(Player player, String targetName, int gameId) {
        CYGame game = games.get(targetName).get(gameId);

        if(game == null) {
            player.sendMessage("§cErreur sur le jeu, merci de contacter un modérateur");
            return;
        }

        Plot targetPlot = main.getDataManager().getStorages().get(targetName).getPlots().get(gameId);

        if(!targetPlot.getEvents().containsKey("onJoin")){
            player.sendMessage("§cJeu invalide ! §lle créateur a oublié le onJoin()");

            return;
        }

        if(game.getPlayers().contains(player))
        {
            player.sendMessage("§cTu es déjà dans le jeu !");
            return;
        }

        if(game.getPlayers().size() == game.getMaxPlayers()){
            player.sendMessage("§cLe jeu est full !");
            return;
        }

        player.teleport(targetPlot.getSpawn(game.getStorage().getUUID()+"#" + gameId));
        player.setLevel(0);
        player.setHealth(20);
        player.getEnderChest().clear();
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setFlying(false);
        player.setAllowFlight(false);

        if(game.isState(CYGameState.WAITING) || game.isState(CYGameState.STARTING)) {
            game.addPlayer(player);
            player.setGameMode(GameMode.SURVIVAL);

            main.getScoreboardManager().unload(player);
            main.getScoreboardManager().loadGame(player, game);

            broadcast(game, player.getName() + " a rejoint le jeu !");
            main.getScoreboardManager().updateInGameTotalPlayers(game);

            runEvent(CEvent.JOIN, game, player);
        }

        if(game.isState(CYGameState.PLAYING) || game.isState(CYGameState.FINISH))
        {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    public void death(Player player){
        CYGame game = main.getGameManager().getCurrentGame(player);

        game.removeLife(player);
        player.sendMessage(game.getLife(player) + " vie(s) restante !");

        if(!game.canRespawn(player))
        {
            eliminate(player, game);
        }
        else{
            player.setHealth(20);
            player.setFoodLevel(20);

            for(PotionEffect potionEffect : player.getActivePotionEffects())
            {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    public void quit(Player player) {

        player.teleport(main.spawnLocation);
        player.getInventory().clear();


        for(BossBar bossBarEntry : main.getBarActions())
        {
            if(bossBarEntry.getPlayers().contains(player))
            {
                bossBarEntry.removeAll();
            }
        }

        for(Map.Entry<String, List<CYGame>> g : games.entrySet())
        {
            for(CYGame gm : g.getValue()) {
                if (gm.getPlayers().contains(player)) {
                    gm.eliminatePlayer(player);

                    for (BossBar bossBarEntry : main.getBarActions()) {
                        if (bossBarEntry.getPlayers().contains(player)) {
                            bossBarEntry.removeAll();
                        }
                    }

                    main.getScoreboardManager().unload(player);
                    main.getScoreboardManager().load(player);

                    player.getInventory().clear();
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.setFireTicks(0);

                    player.setGameMode(GameMode.SURVIVAL);

                    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                        player.removePotionEffect(potionEffect.getType());
                    }

                    broadcast(gm, player.getName() + " a quitter le jeu !");
                    main.getScoreboardManager().updateInGameTotalPlayers(gm);
                }

            }
        }
    }

    public void eliminate(Player player, CYGame game) {

        if (game.getPlayers().contains(player))
        {
            game.eliminatePlayer(player);
            main.getScoreboardManager().unload(player);
            main.getScoreboardManager().load(player);

            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);

            for(PotionEffect potionEffect : player.getActivePotionEffects())
            {
                player.removePotionEffect(potionEffect.getType());
            }

            if(main.getFreezeGamePlayers().contains(player)){
                main.getFreezeGamePlayers().remove(player);
            }

            broadcast(game, player.getName() + " est éliminé du jeu !");


            main.getDataManager().addTrophy(player, 3);
            player.sendMessage("§r+ §e3§r Trophés de participation !");
            player.teleport(main.spawnLocation);

            main.getScoreboardManager().updateInGameTotalPlayers(game);

            if(game.getPlayers().size() == 1 || game.getPlayers().size() == 0)
            {
                game.setState(CYGameState.FINISH);
                main.getGameManager().stop(game);
            }
        }
    }

    public void broadcast(CYGame game, String message) {
        for(Player player : game.getPlayers())
        {
            player.sendMessage("§7[§e"+game.getName()+"§7]§r " + message);
        }
    }

    public void playLoop() {
        for(Map.Entry<String, List<CYGame>> g : main.getGameManager().getGames().entrySet())
        {
            for(CYGame game : g.getValue())
            {
                if(!onLoopEnable.contains(game.getStorage()) && game.isState(CYGameState.PLAYING)) {
                    if(game.getPlayers().size() == 0) continue;
                    runEvent(CEvent.LOOP, game, game.getPlayers().get(0));
                    onLoopEnable.add(game.getStorage());
                }
            }
        }
    }

    public void regenMap(CYGame game) {
        for(Block placed : game.getPlacedBlocks())
        {
            placed.setType(Material.AIR);
        }

        for(Map.Entry<Location, MaterialAndData> breaked : game.getBreakBlocks().entrySet())
        {
            breaked.getKey().getBlock().setType(breaked.getValue().getMaterial());
            //breaked.getKey().getBlock().setData(breaked.getValue().getData());

        }

        game.clearRegenSave();
    }

    public void runEvent(CEvent event, CYGame game, Player player, Block block, Material material) {
        String ownerPlotUUId = game.getStorage().getUUID();

        if(game.getStorage().getPlot(player.getWorld().getName()).hasEvent(event.getEventName())) {
            main.getCompilerManager().runEvent(player, ownerPlotUUId + "#" + game.getGameId(), event.getEventName(), false, event.isAffectingAllPlayers(), block, material);
        }
    }

    public void runEvent(CEvent event, Storage storage, Player player) {
        String ownerPlotUUId = storage.getUUID();

        if(storage.getPlot(player.getWorld().getName()).hasEvent(event.getEventName())) {
            main.getCompilerManager().runEvent(player, ownerPlotUUId, event.getEventName(), false, event.isAffectingAllPlayers(), null, Material.LAPIS_BLOCK);
        }
    }

    public void runEvent(CEvent event, CYGame game, Player player) {
        runEvent(event, game, player, null, Material.LAPIS_BLOCK);
    }

    public void runBlockEvent(CEvent event, CYGame game, Player player, Block block) {
        runEvent(event, game, player, block, block.getType());
    }

    public boolean hasPendingGame(String ownerName, int gameId) {
        return games.containsKey(ownerName);
    }

    public void stop(CYGame game) {
        String key = "";

        for(Player player : game.getPlayers())
        {
            eliminate(player, game);
        }

        regenMap(game);

        game.setup();

        for(Map.Entry<String, List<CYGame>> game1 : games.entrySet())
        {
            for(CYGame c : game1.getValue())
            {
                if(c.equals(game))
                {
                    key = game1.getKey();
                }
            }
        }

        games.remove(key);

    }
    public List<CYGame> isPlayerInGame(Player player) {

        List<CYGame> gs = new ArrayList<>();

        for(Map.Entry<String, List<CYGame>> d : games.entrySet()) {
            for (CYGame game : d.getValue()) {
                if (game.getPlayers().contains(player)) {
                    gs.add(game);
                }
            }
        }

        return gs;
    }

    public CYGame getCurrentGame(Player player) {
        for(Map.Entry<String, List<CYGame>> d : games.entrySet())
        {
            for(CYGame g : d.getValue())
            {
                if(g.getPlayers().contains(player))
                {
                    return g;
                }
            }
        }

        return null;
    }

    public Map<Integer, Storage> getVerifyGames(){ return verifyGames; }

    public Map<String, List<CYGame>> getGames() { return games;}


    public void removeLoopStorage(Storage storage) {
        if (onLoopEnable.contains(storage)) {
            onLoopEnable.remove(storage);
        }
    }
}
