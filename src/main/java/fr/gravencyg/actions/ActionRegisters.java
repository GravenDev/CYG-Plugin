package fr.gravencyg.actions;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.basic.*;
import fr.gravencyg.actions.block.RegenMapAction;
import fr.gravencyg.actions.block.RemoveBlockAction;
import fr.gravencyg.actions.block.SetBlockAction;
import fr.gravencyg.actions.game.EliminateAction;
import fr.gravencyg.actions.game.StopGameAction;
import fr.gravencyg.actions.game.WinnerAction;
import fr.gravencyg.actions.inventory.ChestAction;
import fr.gravencyg.actions.inventory.ClearAction;
import fr.gravencyg.actions.message.BossBarAction;
import fr.gravencyg.actions.message.MessageAction;
import fr.gravencyg.actions.message.ResetBossBarAction;
import fr.gravencyg.actions.message.TitleAction;
import fr.gravencyg.actions.potion.ClearPotionEffectAction;
import fr.gravencyg.actions.potion.PotionEffectAction;
import fr.gravencyg.actions.team.TeamAction;
import fr.gravencyg.actions.toggle.*;
import fr.gravencyg.actions.waitable.WaitAction;
import fr.gravencyg.actions.waitable.WaitAction2;
import fr.gravencyg.actions.waitable.WaitAction3;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CLevel;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ActionRegisters {

    private CYG main;

    private Map<Material, Action> actions = new HashMap<>();

    public ActionRegisters(CYG main) {
        this.main = main;

        // basic
        registerAction(1, Material.CHEST, new ChestAction(main));
        registerAction(2, Material.NETHER_QUARTZ_ORE, new ClearAction(main));
        registerAction(3, Material.NOTE_BLOCK, new MusicAction(main));
        registerAction(4, Material.MYCELIUM, new RestorePlayer(main));
        registerAction(5, Material.SPONGE, new RegenMapAction(main));
        registerAction(6, Material.OBSERVER, new ChangeGameModeAction(main));
        registerAction(7, Material.BREWING_STAND, new PotionEffectAction(main));
        registerAction(8, Material.NETHERRACK, new ClearPotionEffectAction(main));

        registerAction(9, Material.BEACON, new TeleportAction(main));
        registerAction(10, Material.SPAWNER, new MonsterSpawnAction(main));
        registerAction(11, Material.JACK_O_LANTERN, new KillMonsterAction(main));
        registerAction(12, Material.DROPPER, new DropItemAction(main));
        registerAction(13, Material.BLACK_STAINED_GLASS, new SetBlockAction(main));
        registerAction(14, Material.GLASS, new RemoveBlockAction(main));

        // message
        registerAction(18, Material.BOOKSHELF, new MessageAction(main));
        registerAction(19, Material.SEA_LANTERN, new TitleAction(main));
        registerAction(20, Material.SOUL_SAND, new BossBarAction(main));
        registerAction(21, Material.END_STONE_BRICKS, new ResetBossBarAction(main));
        registerAction(15, Material.GLOWSTONE, new ToggleGlowAction(main));

        // game
        // registerAction(36, Material.GOLD_BLOCK, new TeamAction(main));
        registerAction(36, Material.REDSTONE_BLOCK, new EliminateAction(main));
        registerAction(37, Material.DIAMOND_BLOCK, new WinnerAction(main));
        registerAction(38, Material.NETHER_BRICKS, new StopGameAction(main));
        registerAction(39, Material.WHITE_WOOL, new TeamAction(main));

        // toggle
        registerAction(45, Material.SLIME_BLOCK, new ToggleFallDamage(main));
        registerAction(46, Material.IRON_BLOCK, new TogglePvPAction(main));
        registerAction(47, Material.EMERALD_BLOCK, new ToggleBuildAction(main));
        registerAction(48, Material.QUARTZ_BLOCK, new ToggleFlyAction(main));
        registerAction(49, Material.PACKED_ICE, new ToggleFreezeAction(main));

        // others
        registerAction(43, Material.STONE, new PassAction(main));
        registerAction(35, Material.COBWEB, new RandomAction(main));
        registerAction(44, Material.HOPPER, new ConditionAction(main));

        registerAction(51, Material.LEGACY_WOOD_STEP, new WaitAction(main));
        registerAction(52, Material.LEGACY_STEP, new WaitAction2(main));
        registerAction(53, Material.LEGACY_STONE_SLAB2, new WaitAction3(main));


    }

    public void registerAction(int slot, Material material, Action action){
        action.setSlot(slot);
        this.actions.put(material, action);
    }

    public void execute(ActionProcess actionProcess, Block block){
        if (!actions.containsKey(block.getType()))
        {
            return;
        }

        Storage storage = main.getDataManager().getStorageByUUID(block.getWorld().getName());
        int storageOwnerLevel = storage.getLevel();

        if(main.getActionBlocks().getActions().containsKey(block.getType()))
        {
            Action ac = main.getActionBlocks().getActions().get(block.getType());
            int requireLevel = ac.getRequireLevel().getRealLevel();
            if(requireLevel > storageOwnerLevel)
            {
                actionProcess.getPlayers().get(0).sendMessage("§cLe créateur du jeu doit obtenir le §nNiveau "+requireLevel+"§r§c pour utiliser l'instruction §n" + ac.title().replace("§9","§c§n"));
                return;
            }
        }

        if(block.getType() == Material.IRON_BLOCK || block.getType() == Material.EMERALD_BLOCK )
        {
            actions.get(block.getType()).onExecute(actionProcess.getPlayers().get(0), block, actionProcess);
        }
        else
        {
            for(Player player : actionProcess.getPlayers())
            {
                if(player.getWorld().getName().equalsIgnoreCase(block.getWorld().getName())) {
                    actions.get(block.getType()).onExecute(player, block, actionProcess);
                }
            }
        }


    }

    public Map<Material, Action> getActions() {
        return actions;
    }
}
