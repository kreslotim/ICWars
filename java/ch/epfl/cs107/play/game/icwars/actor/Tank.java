package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Tank extends Unit {

    private final static String[] factionImage = {"icwars/friendlyTank","icwars/enemyTank"};

    /**
     * Default Unit constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */


    public Tank (Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position,4, 7, 10, faction, "icwars/friendlyTank");
       // factionImage[0] = ...

    }


}