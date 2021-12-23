package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor implements Interactable, Interactor {

    private final List<Unit> memorisedUnits = new ArrayList<>();
    private List<Unit> ICUnitsList = new ArrayList<>();
    private PlayerStates playerState;


    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public ICWarsPlayer(ICWarsArea area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction);
        ICUnitsList = new ArrayList<>(List.of(units));
    }

    /**
     * update
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * Method adding the new actors (players and units) on the grid
     * @param area     (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    @Override
    public void enterArea(ICWarsArea area, DiscreteCoordinates position) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    /**
     * Method allowing to a player to start his round
     */
    public void startTurn() {
        setPlayerState(PlayerStates.NORMAL);
        this.centerCamera();
    }


    /**
     * Sets all units of a player available
     */
    public void UnitAvailability() {
        for (Unit u : ICUnitsList) {
            u.setIsUsedUnit(false);
        }
    }

    /**
     * Tests if a player is defeated, depending if it's list of usable units is empty or not
     *
     * @return defeated (Boolean)
     */
    public boolean isDefeated() {
        boolean defeated = false;
        if (ICUnitsList.isEmpty()) {
            defeated = true;
        }
        return defeated;
    }

    /**
     * gets the list of memorized (selected) units
     * @return memorizedUnits (List)
     */
    public List<Unit> getMemorisedUnits() {
        return memorisedUnits;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * Gets the list of units, of a specific player
     *
     * @return playerUnitsList (List)
     */
    public List<Unit> getICUnitsList() {
        return ICUnitsList;
    }

    /**
     * get the playerState of the player
     */
    public PlayerStates getPlayerState() {
        return playerState;
    }


    /**
     * sets the playerState of the player
     * @param playerState
     */
    public void setPlayerState(PlayerStates playerState) {
        this.playerState = playerState;
    }


    /**
     * Enumeration of all States of player, used in RealPlayer, and ICWars
     */
    public enum PlayerStates {IDLE, NORMAL, SELECT_CELL, MOVE_UNIT, ACTION_SELECTION, ACTION;}
}
