package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor implements Interactable {

    protected List<Unit> unitsList;
    protected List<Area> areasList = new ArrayList<>();
    private PlayerStatesEnum state;

    /**
     * Default ICWarsActor constructor
     *
     * @param area     (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public ICWarsPlayer(Area area, DiscreteCoordinates position, Faction faction, Unit... units) {
        super(area, position, faction);
        for (Unit unit : units) {
            area.registerActor(unit);
            unitsList = new ArrayList<>(List.of(units));
        }
        state = PlayerStatesEnum.IDLE; // any player at its creation is in the state IDLE ;

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void update(float deltaTime) {
        if (unitsList != null) {
            for (Unit unit : unitsList) {
                if (unit.getCurrentHp() <= 0) {
                    getOwnerArea().unregisterActor(unit);
                    areasList.remove(unit);
                }
            }

        }
        super.update(deltaTime);
    }

    /**
     * taking care of the necessary actions to be undertaken when the player begins his turn
     * For the moment, this method will allow the player to go to the state NORMAL where he becomes active and receptive to controls and will focus the camera on him
     * All of its units must become available again.
     * (the first player immediately takes the hand and begins his
     * turn)
     */
    public void startTurn() {


    }

    /**
     * get the state of the player
     * @return
     */
    public PlayerStatesEnum getState() {
        return state;
    }

    /**
     * set the state of the player
     * @param state
     */
    public void setState(PlayerStatesEnum state) {
        this.state = state;
    }

    /**
     * IDLE do nothing (it is no longer his turn);
     * • NORMAL if the key Enter is pressed go to state SELECT_CELL (which indicates that the
     * player will have an interaction with a unit, we will discuss this later), otherwise if the
     * key Tab is pressed, return to state IDLE (it is now an opponent’s turn to play);
     * • SELECT_CELL means that a selected unit has been memorized and is followed by the
     * state MOVE_UNIT ;
     * • MOVE_UNIT if the key Enter is pressed move the memorized unit to the current
     * location (see instructions below); mark it as used and go to NORMAL state;
     * • for any other value of the report, do nothing for the moment.
     **/
    public enum PlayerStatesEnum {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION;
    }


}
