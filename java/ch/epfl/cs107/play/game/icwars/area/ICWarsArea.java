package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICWarsArea extends Area {

    private ICWarsBehavior behavior;
    private <Units>Units
    // raisonnement Urs : inititilalise realplayers :
// dans Area, players, registorUnit  dans une liste et appelle dans ICWarsPlayer (et du oup pour RealP etAIP en ,m te;.ps)
//     // iune liste des index     (int) et une liste des coords
    // use getUnit pour
    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    public abstract DiscreteCoordinates getEnemySpawnPosition();

    protected abstract void createArea();

    /**
     * appelle dans ICWarsPlayer
     */
    public void registorUnits(){


    }
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICWarsBehavior(window, getTitle());
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }

}
