package fr.gravencyg.scoreboards;

import fr.gravencyg.CYG;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.utils.CRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private CYG main;
    private Map<Player, ScoreboardSign> scoreboards = new HashMap<>();
    private Map<Player, ScoreboardSign> scoreboardsGame = new HashMap<>();
    private Map<CRank, Team> teamMap = new HashMap<>();

    public ScoreboardManager(CYG main) {
        this.main = main;

        for(CRank rank: CRank.values())
        {
            teamMap.put(rank, Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam("0000"+rank.getName()));
        }
    }


    public void load(Player player) {
        ScoreboardSign board = new ScoreboardSign(player, "§eGravenMC v§c" + main.getDescription().getVersion());
        board.create();

        board.setLine(0, "§e");
        board.setLine(1, "En ligne§r: §71");
        board.setLine(2, "§5");
        board.setLine(3, "§fGrade§f:§r ?");
        board.setLine(4, "§fNiveau§f: §e?§7/§e30");
        board.setLine(5, "§fTrophés§f: §e?");
        board.setLine(6, "§d");
        board.setLine(7, "§eplay.gravenmc.fr");

        scoreboards.put(player, board);

        updateTotalPlayers();
        updatePlayerRank(player);
        updatePlayerLevel(player);
        updatePlayerTrophy(player);

    }


    public void loadGame(Player player, CYGame game) {

        unload(player);

        ScoreboardSign board = new ScoreboardSign(player, "§e" + game.getName());
        board.create();

        board.setLine(0, "§e");
        board.setLine(1, "§rJoueurs§r: §7" + "?§r/§7?");
        board.setLine(2, "§fStatut§f:§r En attente");
        board.setLine(3, "§d");
        board.setLine(4, "§eplay.gravenmc.fr");

        scoreboardsGame.put(player, board);
    }

    public void updateTotalPlayers() {
        for(Map.Entry<Player, ScoreboardSign> board : scoreboards.entrySet()) {
            board.getValue().setLine(1, "En ligne§r: §7" + Bukkit.getOnlinePlayers().size());
        }
    }

    public void updateInGameTotalPlayers(CYGame game) {
        for(Player player : game.getPlayers()) {
            ScoreboardSign board = scoreboardsGame.get(player);
            board.removeLine(1);
            board.setLine(1, "§rJoueurs§r: §7" + game.getPlayers().size() + "§r/§7" + game.getMaxPlayers());
        }
    }

    public void updateInGameState(CYGame game) {
       for(Player player : game.getPlayers()) {
            ScoreboardSign board = scoreboardsGame.get(player);
            board.removeLine(2);
            board.setLine(2, "§fStatut§f:§r " + game.getState().getName());
        }
    }

    public void updatePlayerRank(Player player) {
        if(scoreboards.containsKey(player)){
            ScoreboardSign board = scoreboards.get(player);
            CRank rank = main.getDataManager().getRank(player);
            board.setLine(3, "§fGrade§f:§r " + rank.getColor() +  rank.getName());
        }
    }

    public void updatePlayerLevel(Player player) {
        if(scoreboards.containsKey(player)){
            ScoreboardSign board = scoreboards.get(player);
            board.setLine(4, "§fNiveau§f: §e"+ main.getDataManager().getLevel(player.getName())+"/§e30");
        }
    }

    public void updatePlayerTrophy(Player player) {
        if(scoreboards.containsKey(player)){
            ScoreboardSign board = scoreboards.get(player);
            board.setLine(5, "§fTrophés§f: §e" +main.getDataManager().getTrophys(player.getName())+"");
        }
    }

    public void unload(Player player) {
        if(scoreboards.containsKey(player)) {
            ScoreboardSign board = scoreboards.get(player);
            board.destroy();

            teamMap.get(main.getDataManager().getRank(player)).removeEntry(player.getName());

            scoreboards.remove(player);

            for(Map.Entry<Player, ScoreboardSign> b : scoreboards.entrySet()) {
               b.getValue().setLine(1, "Joueurs§r: §7" + (Bukkit.getOnlinePlayers().size()-1));
            }

        }

        if(scoreboardsGame.containsKey(player)) {
            ScoreboardSign board = scoreboardsGame.get(player);
            board.destroy();

            scoreboardsGame.remove(player);

        }
    }

}
