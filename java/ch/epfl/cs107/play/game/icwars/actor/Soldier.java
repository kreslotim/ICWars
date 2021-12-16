package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Soldier extends Unit {

    private static final String[] factionImage = {"icwars/friendlySoldier", "icwars/enemySoldier"};

    /**
     * Default Unit constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */


    public Soldier(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, 2, 7, 10, faction, factionImage[faction.ordinal()]);
    }


}
