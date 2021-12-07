package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Queue;


public class Unit extends ICWarsActor {

    private int currentHp;
    private int maxHp;
    private int damage;
    private int radius;
    private Faction faction;
    private Sprite sprite;
    private ICWarsRange range;
    private int fromX;
    private int fromY;

//    private void edge(){
//        for
//            for
//    }



    /**
     * Default Unit constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */

    Unit(Area area, DiscreteCoordinates position, int radius, int damage, int maxHp, Faction faction) {
        super(area, position, faction);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.damage = damage;
        this.radius = radius;
        this.faction = faction;
    }

    int getDamage() {
        return damage;
    }

    void receiveDamage(int damage) {
        // check if hp < 0
        int newHp = this.currentHp - damage;
        this.currentHp = newHp < 0 ? 0 : newHp;
    }

    void repairDamage(int heal) {
        // check if hp > maxHp
        int newHp = this.currentHp + heal;
        this.currentHp = newHp > maxHp ? maxHp : newHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }


    /**
     * GhostPlayer devra évidemment avoir une méthode de dessin spécifique, laquelle se contentera
     * de dessiner le Sprite associé.
     **/
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas); // l'affichage du sprite sur l'ecran
    }


    /**
     * Draw the unit's range and a path from the unit position to
     * destination
     *
     * @param destination path destination
     * @param canvas      canvas
     */
    public void drawRangeAndPathTo(DiscreteCoordinates destination,
                                   Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path =
                range.shortestPath(getCurrentMainCellCoordinates(),
                        destination);
//Draw path only if it exists (destination inside the range)
        if (path != null) {
            new Path(getCurrentMainCellCoordinates().toVector(),
                    path).draw(canvas);
        }
    }

}
