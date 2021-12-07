package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class Soldier extends Unit {

    private String spriteName;
    private final String[] factionImage = {"icwars/friendlySoldier","icwars/enemySoldier"};
    private ICWarsRange range;


    /**
     * Default Unit constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */


    public Soldier (Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position,4, 7, 10, faction);
        spriteName = factionImage[faction.ordinal()];
        Sprite sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(3,5));
        this.setSprite(sprite);
    }

}
