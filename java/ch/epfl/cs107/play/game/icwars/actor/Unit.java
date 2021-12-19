package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public abstract class Unit extends ICWarsActor implements Interactor {

    private String unitName;
    private int hp;
    private int maxHp;
    private int damage;
    private int radius;
    //private Faction faction;
    private Sprite sprite;
    private ICWarsRange range = new ICWarsRange();
    private boolean usedUnit = false;
    private int defenseStar; // comming from where?

    protected final List<Action> actionsList = new ArrayList<>();





    /**
     * Default Unit constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    Unit(ICWarsArea area, DiscreteCoordinates position, int radius, int damage, int maxHp, Faction faction, String unitName) {
        super(area, position, faction);
        this.unitName = unitName;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.damage = damage;
        this.radius = radius;

        // Method allowing to draw the range, around a unit
        addEdge(getCurrentMainCellCoordinates());

        //Builds the image of each unit, on the grid
        Sprite sprite = new Sprite(unitName, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
        this.sprite = sprite;
        setOwnerArea(area);
    }

    /**
     * drawing method for each unit
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas); // Unit Draw
    }

    /******************************************************************************************************************
     *                                            DRAWING RANGE
     ******************************************************************************************************************/
    /**
     * Main method determining the edges of a unit's range, based on four booleans, defining if each side has an edge
     * @param from (Discrete Coordinates). Center of the range, positioned on the selected unit (origin)
     */
    private void addEdge(DiscreteCoordinates from) {

        boolean hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge;

        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {

                if (x + from.x <= getOwnerArea().getWidth() - 1 && x + from.x >= 0 &&
                        y + from.y <= getOwnerArea().getHeight() - 1 && y + from.y >= 0) {

                    hasLeftEdge = x > -radius && from.x + x >= 0;
                    hasRightEdge = x < radius && from.x + x >= 0;
                    hasUpEdge = y < radius && from.y + y >= 0;
                    hasDownEdge = y > -radius && from.y + y >= 0;

                    range.addNode(new DiscreteCoordinates(x + from.x, y + from.y),
                            hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
                }
            }
        }
    }

    /**
     * Draw the unit's range and a path from the unit position to
     * destination
     *
     * @param destination path destination
     * @param canvas      canvas
     */
    public void drawRangeAndPathTo(DiscreteCoordinates destination, Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path = range.shortestPath(getCurrentMainCellCoordinates(), destination);

        //Draw path only if it exists (destination inside the range)
        if (path != null) {
            new Path(getCurrentMainCellCoordinates().toVector(), path).draw(canvas);
        }
    }

    /**
     * @param newPosition new unit's position
     * @return (Boolean) true if new position is in bounds, and is different from it's initial position
     */
    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        if (!range.nodeExists(newPosition) || !super.changePosition(newPosition)) {
            return false;
        }

        // resetting the new range, after changing unit's position
        range = new ICWarsRange();
        addEdge(newPosition);
        return true;
    }


    /******************************************************************************************************************
                                             GETTERS   &   SETTERS
     *****************************************************************************************************************/
    /** GETTERS */

    public List<Action> getAction() {
        return actionsList;
    }

    public String getName() {
        return unitName;
    }

    public ICWarsRange getRange() {
        return range;
    }

    public boolean isUsed() {
        return usedUnit;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }


    /** SETTERS */

    public void setIsUsedUnit(boolean used) {
        sprite.setAlpha(used ? 0.5f : 1f);
        usedUnit = used;
    }

    public void makeDamage() {
        hp = hp - damage + defenseStar;
    }

    public void receiveDamage(int damage) {
        // check if hp < 0
        int newHp = this.hp - damage;
        this.hp = Math.max(newHp, 0);
    }

    public void setHeal(int heal) {
        // check if hp > maxHp
        int newHp = this.hp + heal;
        this.hp = Math.min(newHp, maxHp);
    }



    /******************************************************************************************************************
     ****************************************    INTERACTIONS    ******************************************************
     ******************************************************************************************************************/

    /**
     * Nested class, handling the Interactions
     */
    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {
        /**
         * InteractWith method redefined in the Interface ICWarsInteractionVisitor, setting the defense stars
         * @param cell
         */
        @Override
        public void interactWith(ICWarsBehavior.ICWarsCell cell) {
            defenseStar = cell.getDefenseStars();
        }
    }


    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor) v).interactWith(this);
    } // A unit must accept interactions requested from an Interactor (player), casted to an ICWarsInteractionVisitor

    @Override
    public boolean isCellInteractable() {
        return true;
    } // A unit must be Interactable by an Interactor (player), on a specific Cell

    @Override
    public boolean takeCellSpace() {
        return true;
    } // A unit occupies a specific cell, making it not traversable
}