package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICWarsArea extends Area {

    private ICWarsBehavior behavior;
    private List<Unit> unitsList = new ArrayList<>();



    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }


    public void registerUnit(Unit u) { unitsList.add(u); }

    /**
     * get the index of every units
     *
     * @param faction
     * @return
     */
    public List<Integer> getIndex(ICWarsActor.Faction faction) {
        List<Integer> indexList = new ArrayList<>(); // maybe at the top
        for (Unit u : unitsList) {

            if (!u.getFaction().equals(faction) && u.getRange().nodeExists(new DiscreteCoordinates((int) u.getPosition().x,
                                                                                                   (int) u.getPosition().y))) {

                System.out.println(u.getPosition());
                indexList.add(unitsList.indexOf(u));
            }
        }

        return indexList;
    }


    /**
     *  && (unitsList.get(i).getRange().nodeExists(new DiscreteCoordinates((int) unitsList.get(i).getPosition().x,
     *                                                                                        (int) unitsList.get(i).getPosition().y )))); {
     * @param indexOfAttack
     */

    public void centerCameraOnUnit(int indexOfAttack) {
        unitsList.get(indexOfAttack).centerCamera();
    }

    public void doDamage(int indexOfAttack) {
        unitsList.get(indexOfAttack).makeDamage();
    }


    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    public abstract DiscreteCoordinates getEnemySpawnPosition();

    protected abstract void createArea();

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
