package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level1 extends ICWarsArea {

    protected void createArea() {
        registerActor(new Background(this));
    }

    @Override
    public String getTitle() {
        return "icwars/Level1";
    }

    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(2, 5);
    }

    public DiscreteCoordinates getEnemySpawnPosition() {
        return new DiscreteCoordinates(17, 5);
    }

}
