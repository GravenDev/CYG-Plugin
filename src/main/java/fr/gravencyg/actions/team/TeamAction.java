package fr.gravencyg.actions.team;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeamAction extends Action {

    public TeamAction(CYG main) {
        super(main, CLevel.LEVEL8);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "Team()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Créer un système d'equipe sur votre jeu";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        Location topBlock = block.getLocation().clone().add(0, 1, 0);

       /* if (topBlock.getBlock().getType() == Material.WOOL) {

            for (int i = 0; i < 25; i++) {

                Block b = block.getLocation().clone().add(0, i, 0).getBlock();

                if (b.getType() != Material.WOOL) break;

                byte data = b.getData();
                Team team = Team.getByWoolData(data);

                player.sendMessage("Création de l'equipe " + team.getColor() + team.getName() + " !");

                if (main.getGameManager().isPlayerInGame(player).size() != 0) {
                    CYGame game = main.getGameManager().getCurrentGame(player);
                    main.getGameManager().createTeam(game, team);
                }

            }
        }*/

        if (topBlock.getBlock().getType() == Material.BEACON) {
            byte data = block.getData();
            Team team = Team.getByWoolData(data);

            if (main.getGameManager().isPlayerInGame(player).size() != 0) {
                CYGame game = main.getGameManager().getCurrentGame(player);


                if(game.getTeamPlayer(player) == team) {

                    Plot plot = main.getDataManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());
                    String locPosition = LocationUtils.fromLocToString(topBlock);
                    String locName = plot.getLocationsSet().get(locPosition);

                    if(locName != null && plot.getLocations().containsKey(locName)) {
                        Location location = LocationUtils.fromStringToLocComplete(player.getWorld().getName(), plot.getLocations().get(locName));
                        player.teleport(location);
                    }

                    player.sendMessage("§rTéléportation vers " + team.getColor() + locName);

                }
            }

        }
    }
}
