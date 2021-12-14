package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class ICWarsActor extends MovableAreaEntity {

    private Faction faction;

    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */

    public ICWarsActor(Area area, DiscreteCoordinates position, Faction faction) {
        // by default every ICWarsActor will be orientated UP
        super(area, Orientation.UP, position);
        this.faction = faction;
    }

    public Faction getFaction() {
        return faction;
    }


    /**
     * Center the camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * @param area     (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }


    public enum Faction {

        ALLY(true),
        ENEMY(false);
        final boolean isFriendly;

        Faction(boolean isFriendly) {
            this.isFriendly = isFriendly;
        }


    }
}
