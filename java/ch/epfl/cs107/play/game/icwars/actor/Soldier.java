package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Unit {

    private static final String[] factionImage = { "Good Guy", "Bad Boy" };

    /**
     * Default Unit constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */

    public Soldier(ICWarsArea area, DiscreteCoordinates position, Faction faction) {
        super(area, position, 2, 2, 5, faction, factionImage[faction.ordinal()]);

        Action attack = new Attack(this, (ICWarsArea) getOwnerArea());
        Action wait = new Wait(this, (ICWarsArea) getOwnerArea());
        actionsList.add(attack);
        actionsList.add(wait);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {

    }
}
