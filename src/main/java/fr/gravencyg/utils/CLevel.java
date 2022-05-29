package fr.gravencyg.utils;

import java.util.*;

public enum CLevel {

    LEVEL1(1, 0, PlotSize.NORMAL, Arrays.asList(
          "§e/play§f pour jouer à un jeu",
          "§e/plot§f pour obtenir une parcelle",
          "§e/locations§f pour gérer les zones du jeu",
          "§e/friend§f pour gerer vos co-constructeurs"
    ), Arrays.asList(
          "§6Bloc onEvent()§f pour gérer les évenements",
          "§6Bloc Random()§f pour ajouter du hasard",
          "§6Bloc TogglePvP()§f pour on/off le pvp",
          "§6Bloc Eliminate()§f pour eliminer un joueur",
          "§6Bloc Stuff()§f pour give des items",
          "§6Bloc Clear()§f pour enlever les items",
          "§6Bloc Teleport()§f pour tp un joueur"
    )),

    LEVEL2(2, 15, PlotSize.NORMAL, Arrays.asList(
          "§e/messages§f pour gérer les messages du jeu"
    ), Arrays.asList(
          "§6Bloc SendMessage()§f pour un message",
          "§6Bloc SendTitle()§f pour fait un /tellraw",
          "§6Bloc Wait()§f pour attendre 1 seconde"
    )),

    LEVEL3(3, 50, PlotSize.NORMAL, Arrays.asList(
          "§e/barriere§f pour obtenir bloc incassable",
          "§e/gamemode§f pour changer de mode"
    ), Arrays.asList(
          "§6Bloc ChangeGameMode()§f pour /gm un joueur",
          "§6Bloc ToggleFreeze()§f pour geler/degeler un joueur",
          "§6Bloc ToggleFallDamage()§f pour on/off la chute"
    )),

    LEVEL4(4, 75, PlotSize.NORMAL, Arrays.asList(), Arrays.asList(
          "§6Bloc Wait()§f pour attendre 10s",
          "§6Bloc ToggleBuild()§f pour on/off le build"
    )),

    LEVEL5(5, 100, PlotSize.MEDIUM, Collections.emptyList(), Arrays.asList(
          "§6Bloc RegenMap()§f pour regen la map",
          "§6Bloc StopGame()§f pour stop le jeu",
          "§6Bloc Win()§f pour forcer un gagnant"
    )),

    LEVEL6(6, 150, PlotSize.MEDIUM, Collections.singletonList(
         "§e/head§f pour obtenir une tête du joueur"
    ), Arrays.asList(
         "§6Bloc SetBlock()§f pour placer un bloc",
         "§6Bloc RemoveBlock()§f pour retirer un bloc"
    )),

    LEVEL7(7, 250, PlotSize.MEDIUM, Collections.emptyList(), Arrays.asList(
         "§6Bloc SendPotionEffect()§f pour un effet de potion",
         "§6Bloc ClearPotionEffects()§f pour retirer les effets"
    )),

    LEVEL8(8, 500, PlotSize.MEDIUM, Collections.emptyList(), Arrays.asList(
        "§f§lBloc Team()§f pour gérer les équipes",
        "§6Bloc DropItem()§f pour loop un item"
    )),

    LEVEL9(9, 750, PlotSize.MEDIUM, Collections.emptyList(), Arrays.asList(
         "§6Bloc SendBossBar()§f pour message en bar de boss",
         "§6Bloc ResetBossBar()§f pour supprimer les bars"
    )),

    LEVEL10(10, 1200, PlotSize.BIG, Arrays.asList(), Collections.emptyList()),

    LEVEL11(11, 1500, PlotSize.BIG, Collections.emptyList(), Arrays.asList(
         "§6Bloc SpawnMonster()§f pour spawn un monstre",
         "§6Bloc KillMonster()§f pour tuer tout les monstres"
    )),

    LEVEL12(12, 2000, PlotSize.BIG, Collections.emptyList(), Collections.singletonList(
         "§6Bloc Restore()§f pour se heal() et feed()"
    )),

    LEVEL13(13, 2500, PlotSize.BIG, Collections.emptyList(), Collections.singletonList(
         "§6Bloc ToggleFly()§f pour on/off le fly du joueur"
    )),

    LEVEL14(14, 3200, PlotSize.BIG, Collections.emptyList(), Collections.emptyList()),
    LEVEL15(15, 5000, PlotSize.LARGE, Arrays.asList(
            "§f§n/we§f pour l'accès au Mini Worldedit"
    ), Collections.emptyList()),

    LEVEL16(16, 7000, PlotSize.LARGE, Collections.emptyList(), Arrays.asList(
        "§6Bloc Glow()§f pour on/off l'effet glow"
    )),

    LEVEL17(17, 8000, PlotSize.LARGE, Arrays.asList(), Arrays.asList()),
    LEVEL18(18, 9000, PlotSize.LARGE, Arrays.asList(), Arrays.asList()),
    LEVEL19(19, 10000, PlotSize.LARGE, Arrays.asList(), Arrays.asList()),
    LEVEL20(20, 12000, PlotSize.MEGA, Arrays.asList(), Arrays.asList()),

    LEVEL21(21, 15000, PlotSize.MEGA, Arrays.asList(), Arrays.asList()),
    LEVEL22(22, 17000, PlotSize.MEGA, Arrays.asList(), Arrays.asList()),
    LEVEL23(23, 19000, PlotSize.MEGA, Arrays.asList(), Arrays.asList()),
    LEVEL24(24, 21000, PlotSize.MEGA, Arrays.asList(), Arrays.asList()),
    LEVEL25(25, 22000, PlotSize.EXTREME, Arrays.asList(), Arrays.asList()),
    LEVEL26(26, 25000, PlotSize.EXTREME, Arrays.asList(), Arrays.asList()),
    LEVEL27(27, 30000, PlotSize.EXTREME, Arrays.asList(), Arrays.asList()),
    LEVEL28(28, 32000, PlotSize.EXTREME, Arrays.asList(), Arrays.asList()),
    LEVEL29(29, 35000, PlotSize.EXTREME, Arrays.asList(), Arrays.asList()),
    LEVEL30(30, 50000, PlotSize.GOD, Arrays.asList(), Arrays.asList());

    static Map<Integer, CLevel> levels = new HashMap<>();

    static {
        int count = 0;
        for(CLevel level : CLevel.values())
        {
            levels.put(count, level);
            count++;
        }
    }

    private List<String> loreCommands;
    private List<String> loreActions;

    private int realLevel;
    private int trophyMin;
    private PlotSize plotSize;

    CLevel(int realLevel, int trophyMin, PlotSize plotSize, List<String> loreCommands, List<String> loreActions){
        this.realLevel = realLevel;
        this.trophyMin = trophyMin;
        this.loreCommands = loreCommands;
        this.loreActions = loreActions;
        this.plotSize = plotSize;
    }

    public static CLevel findLevelByTrophy(int trophy){
        for(Map.Entry<Integer, CLevel> lvl : levels.entrySet()){
            if(trophy > lvl.getValue().getTrophy()) continue;
            return lvl.getValue();
        }
        return CLevel.LEVEL1;
    }

    public int getRealLevel(){
        return realLevel;
    }

    public int getTrophy(){
        return trophyMin;
    }

    public List<String> getCommands(){
        return loreCommands;
    }

    public List<String> getLoreActions(){
        return loreActions;
    }

    public PlotSize getPlotSize() {
        return plotSize;
    }
}
