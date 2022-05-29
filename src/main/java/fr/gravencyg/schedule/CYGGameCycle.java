package fr.gravencyg.schedule;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.team.Team;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.game.CYGameState;
import fr.gravencyg.model.CYGameMode;
import fr.gravencyg.utils.CEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CYGGameCycle extends BukkitRunnable {

    private CYG main;

    public CYGGameCycle(CYG main){
        this.main = main;
    }

    @Override
    public void run() {

        for(Map.Entry<String, List<CYGame>> g : main.getGameManager().getGames().entrySet())
        {
            for(CYGame game : g.getValue()) {
                if (game.getAutoStartMin() == game.getPlayers().size() && game.isState(CYGameState.WAITING)) {
                    game.setState(CYGameState.STARTING);
                    main.getScoreboardManager().updateInGameState(game);
                    main.getGameManager().broadcast(game, "§6Démarrage du jeu dans:");
                }

                if (game.getPlayers().size() < game.getAutoStartMin() && game.isState(CYGameState.STARTING)) {
                    game.setState(CYGameState.WAITING);
                    main.getScoreboardManager().updateInGameState(game);
                    main.getGameManager().broadcast(game, "§cPas assez de joueur... Phase d'attente");
                }

                if (game.isState(CYGameState.STARTING)) {

                    if (game.getCountdown() == 0) {

                        // assign player to team
                        List<Team> teams = new ArrayList<>(game.getTeams().keySet());
                        if (teams.size() >= 2) {

                            int teamId = 0;
                            for (Player player : game.getPlayers()) {
                                Team selected = teams.get(teamId);
                                game.getTeams().get(selected).add(player);

                                // dispatch team
                                if (teamId == game.getTeams().size()) {
                                    teamId = 0;
                                } else {
                                    teamId++;
                                }

                                player.sendMessage("Vous faites partie de l'équipe " + selected.getColor() + selected.getName());
                            }

                        }

                        game.setState(CYGameState.PLAYING);
                        main.getScoreboardManager().updateInGameState(game);
                        main.getGameManager().runEvent(CEvent.START, game, game.getPlayers().get(0));
                        main.getGameManager().broadcast(game, "§cGO !");

                    } else {
                        main.getGameManager().broadcast(game, "§c" + game.getCountdown() + "§es");
                        game.decrementCountdown();
                    }

                }

                if (game.isState(CYGameState.PLAYING)) {

                    if (game.hasTeamSystem()) {
                        if ((game.getPlayers().size() == game.getTeams().get(game.getTeamPlayer(game.getPlayers().get(0))).size()) && game.getAutoStartMin() != 1) {
                            Team team = game.getTeamPlayer(game.getPlayers().get(0));
                            main.getGameManager().broadcast(game, "Victoire de l'equipe " + team.getColor() + team.getName());
                            main.getGameManager().runEvent(CEvent.FINISH, game, game.getPlayers().get(0));

                            for (Player winner : game.getPlayers()) {
                                if (game.getAutoStartMin() != 1) {
                                    winner.sendMessage("§r+ §e7§r Trophés de victoire !");
                                    main.getDataManager().addTrophy(winner, 7);
                                }
                                winner.teleport(main.spawnLocation);
                            }

                            game.setState(CYGameState.FINISH);
                            main.getScoreboardManager().updateInGameState(game);
                            game.setCountdown(5);
                        }
                    }
                }

                if (game.isState(CYGameState.PLAYING) && game.isMode(CYGameMode.DEATH_MATCH) && game.getPlayers().size() <= 1 && game.getAutoStartMin() != 1) {
                    game.setState(CYGameState.FINISH);
                    main.getScoreboardManager().updateInGameState(game);
                    game.setCountdown(5);

                    if (!game.hasTeamSystem()) {
                        Player winner = game.getPlayers().get(0);

                        if (game.getAutoStartMin() != 1) {
                            winner.sendMessage("§r+ §e7§r Trophées de victoire !");

                            main.getDataManager().addTrophy(winner, 7);

                        }
                        winner.teleport(main.spawnLocation);
                    }

                    if (game.getPlayers().size() == 1 && game.getAutoStartMin() != 1) {
                        main.getGameManager().runEvent(CEvent.FINISH, game, game.getPlayers().get(0));
                    }

                }

                if (!game.isState(CYGameState.WAITING) && !game.isState(CYGameState.STARTING) && game.getPlayers().size() == 0) {
                    main.getGameManager().stop(game);
                    main.getGameManager().runEvent(CEvent.FINISH, game, null);

                }

                if (game.isState(CYGameState.FINISH)) {
                    game.decrementCountdown();
                    if (game.getPlayers().size() != 0) {
                        main.getGameManager().quit(game.getPlayers().get(0));
                    }
                    main.getGameManager().stop(game);

                }
            }

        }

    }

}
