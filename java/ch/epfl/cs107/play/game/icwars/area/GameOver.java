package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class GameOver extends ICWarsArea {


    protected void createArea() {
        // Base
        registerActor(new Background(this));
        //registerActor(player);  //enregistrement deux fois
        //unregisterActor(player);
    }

    @Override
    public String getTitle() {
        return "icwars/GAME OVER";
    }

    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }

    public DiscreteCoordinates getEnemySpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }
}
