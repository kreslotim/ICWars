package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class Tank extends Unit {

    private String spriteName;
    private final String[] factionImage = {"icwars/friendlyTank","icwars/enemyTank"};


    /**
     * Default Unit constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */


    public Tank (Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position,4, 7, 10, faction);
        spriteName = factionImage[faction.ordinal()];
        Sprite sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(2,5));
        this.setSprite(sprite);
    }

}