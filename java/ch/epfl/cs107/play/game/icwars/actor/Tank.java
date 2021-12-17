package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Tank extends Unit {

    private final static String[] factionImage = {"icwars/friendlyTank", "icwars/enemyTank"};

    /**
     * Default Unit constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */


    public Tank(Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position, 4, 7, 10, faction, factionImage[faction.ordinal()]);
        // factionImage[0] = ...

    }


}