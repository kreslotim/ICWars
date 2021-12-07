package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Tank extends Unit {

    private String spriteName;
    private final String[] factionImage = {"icwars/friendlyTank","icwars/enemyTank"};
    private Sprite sprite;

    /**
     * Default Unit constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */


    public Tank (Area area, DiscreteCoordinates position, Faction faction) {
        super(area, position,4, 7, 10, faction);
        spriteName = factionImage[faction.ordinal()];
        sprite = new Sprite(spriteName, 1.5f, 1.5f, this, null, new Vector(-0.25f,-0.25f));
        this.setSprite(sprite);
    }


    /**
     * GhostPlayer devra évidemment avoir une méthode de dessin spécifique, laquelle se contentera
     * de dessiner le Sprite associé.
     **/
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas); // l'affichage du sprite sur l'ecran
    }
}