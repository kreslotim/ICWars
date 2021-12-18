package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor implements Interactable, Interactor {


    private PlayerStates playerState;

    private List<Unit> memorisedUnits = new ArrayList<>();



    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public ICWarsPlayer(ICWarsArea area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction);

        //player = new ICWarsPlayer(area, position, ICWarsActor.Faction.values());

    }



    @Override
    public void enterArea(ICWarsArea area, DiscreteCoordinates position) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor) v).interactWith(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }


    /**
     * get the playerState of the player
     */
    public PlayerStates getPlayerState() {
        return playerState;
    }


    /**
     * set the playerState of the player
     *
     * @param playerState
     */
    public void setPlayerState(PlayerStates playerState) {
        this.playerState = playerState;
    }


    public enum PlayerStates {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION;
    }


    public List<Unit> getMemorisedUnits() {
        return memorisedUnits;
    }
}
