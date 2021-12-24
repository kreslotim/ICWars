package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICWarsArea extends Area {

    private ICWarsBehavior behavior;
    private List<Unit> unitsList = new ArrayList<>();


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


    /**
     * Gets the indexes of target units, that are in the range of the attacker
     * @param attacker (Unit)
     * @return List of indexes (Integer)
     */
    public List<Integer> getTargetIndex(Unit attacker) {
        ICWarsActor.Faction faction = attacker.getFaction();
        List<Integer> indexList = new ArrayList<>(); // maybe at the top
        for (Unit u : unitsList) {
            if (!u.getFaction().equals(faction) && attacker.getRange().nodeExists(new DiscreteCoordinates((int) u.getPosition().x,
                                                                                                          (int) u.getPosition().y))) {
                indexList.add(unitsList.indexOf(u));
            }
            /**
            if (u.getHp() == 0) {
                unitsList.remove(u);
            }
             */
        }
        return indexList;
    }

    /**
     * Gets the index of any unit
     * @param unit (Unit)
     * @return Index (Integer)
     */
    public int getUnitIndex(Unit unit) {
        return unitsList.indexOf(unit);
    }

    /**
     * Makes the link between target and attacker, inflicting the damage through Area
     * @param indexOfTarget (Integer)
     * @param indexOfAttacker (Integer)
     */
    public void makeDamageLink(int indexOfTarget, int indexOfAttacker) {
        int received_damage = unitsList.get(indexOfAttacker).getDamage();
        unitsList.get(indexOfTarget).doDamage(received_damage);
    }

    /**
     * Registers unit in the list of units
     * @param u (Unit)
     */
    public void registerUnit(Unit u) { unitsList.add(u); }

    /**
     * Unregisters unit from the list of units
     * @param u (Unit)
     */
    public void unregisterUnit (Unit u) { unitsList.remove(u); }

    /**
     * Centers the camera on target
     * @param indexOfAttack (Integer)
     */
    public void centerCameraOnUnit(int indexOfAttack) {

        unitsList.get(indexOfAttack%unitsList.size()).centerCamera();
    }

    public DiscreteCoordinates getClosetEnemy(DiscreteCoordinates position, ICWarsActor.Faction faction, int radius) {
        for (Unit u : unitsList) {
            DiscreteCoordinates.distanceBetween(position, u.getCurrentCells().get(0));
        }
        return position;
    }

    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    public abstract DiscreteCoordinates getEnemySpawnPosition();

    protected abstract void createArea();

    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }

}
