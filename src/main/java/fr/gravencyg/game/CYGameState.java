package fr.gravencyg.game;

public enum CYGameState {

    WAITING("En attente"),
    STARTING("Démarrage"),
    PLAYING("En Partie"),
    FINISH("Fin");

    private String name;

    CYGameState(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
