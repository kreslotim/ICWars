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

    /**
     * (pdf p.12) a unit positioned in the range (fromX, fromY ), its attribute Range must
     * be initialized when creating the unit so as to contain all the nodes that are placed in the
     * range (x + fromX, y + fromY ), where x and y are between −radius and radius and where
     * radius is the unit’s radius of action. Adding a node will be done using the AddNode from
     * ICWarsRange. Attention, only the nodes which correspond to valid positions in the grid
     * must be added. The four booleans required by the AddNode method must be true if the
     * node corresponding to the coordinate (x + fromX, y + fromY ) has a neighbor on its left in
     * the graph ICWarsRange (resp. above, right and below). For the left neighbor for example
     * this will be the case if x is strictly greater than −radius and x + fromX is greater than
     * strictly zero
     **/
    private void addEdge() {
        DiscreteCoordinates from = getCurrentMainCellCoordinates();
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                DiscreteCoordinates thisCell = new DiscreteCoordinates(from.x + x, from.y + y);
                if (getOwnerArea().getWidth() < 0 || getOwnerArea().getHeight() < 0 ||
                        getOwnerArea().getWidth() > thisCell.x || getOwnerArea().getHeight() > thisCell.y) {
                    // row < 0 || col < 0 || row >= image.length || col >= image[row].length
                    range.addNode(thisCell, ((x > -radius) && (x + from.x > 0)), ((x > radius) &&
                            (x + from.x < 0)), ((y > -radius) && (y + from.x > 0)), ((y > radius) && (y + from.y < 0)));

                }

            }
        }
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
