package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor implements Interactable, Interactor {

    private final ICWarsPlayerGUI gui = new ICWarsPlayerGUI(0, this); // @TODO
    private Unit selectedUnit; // à utiliser avec Interactable pour interagir avec Handler
    private List<Unit> unitsList;
    private List<Area> areasList = new ArrayList<>();
    private List<Unit> memorisedUnits = new ArrayList<>();
    private PlayerStates playerState;
    private boolean defeated = false;

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
        //player = new ICWarsPlayer(area, position, ICWarsActor.Faction.values());
        playerState = PlayerStates.IDLE;
    }

    public ICWarsPlayerGUI getgetGui() {
        return gui;
    }

    public List<Unit> getMemorisedUnits() {
        return memorisedUnits;
    }

    public List<Unit> getUnitsList() {
        return unitsList;
    }


    /**
     * @param area     (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }


    public void startTurn() {
        setPlayerState(PlayerStates.NORMAL);
        this.centerCamera();

        for (Unit u : unitsList) {
            u.setIsUsedUnit(false);
        }
        // make all units of the player not Used

    }

    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {

        if (getCurrentMainCellCoordinates().equals(coordinates.get(0)) && getPlayerState().equals(PlayerStates.SELECT_CELL)) {
            setPlayerState(PlayerStates.NORMAL);
            //System.out.println("onLeaving works!");
        }
        // Entreprendre les traitements nécessaires lorsque player quitte cellule
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
        if (unitsList != null) {
            for (Unit unit : unitsList) {
                if (unit.getCurrentHp() <= 0) {
                    getOwnerArea().unregisterActor(unit);
                    areasList.remove(unit);

                    if (unitsList.isEmpty()) {
                        defeated = true;
                    }
                }
            }

        }
        super.update(deltaTime);
    }


    /**
     * trouver les cibles de l'attaque
     *
     * @return
     */


    public boolean isDefeated() {
        return defeated;
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

    /**
     * Method that switches the states of the player
     *
     * @param playerState
     */
    public void switchPlayerStates(PlayerStates playerState) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        switch (playerState) {
            case IDLE:
                // do nothing
                break;
            case NORMAL:
                //System.out.println("Normal");
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    setPlayerState(playerState.SELECT_CELL);
                    System.out.println("State: SELECT_CELL");
                }


                break;
            case SELECT_CELL:

                if (selectedUnit != null) {

                    //interactWith(selectedUnit);
                    setPlayerState(playerState.MOVE_UNIT);
                    System.out.println("State: MOVE_UNIT");
                } else {
                    onLeaving((getLeftCells()));
                } // onLeaving() sur la position de l'unité...
                break;

            case MOVE_UNIT:
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    // move the selectedUnit to currentSpace
                    // mark memorisedUnit as used

                    selectedUnit.changePosition(new DiscreteCoordinates(getCurrentMainCellCoordinates().x, getCurrentMainCellCoordinates().y));

                    selectedUnit.setIsUsedUnit(true);
                    selectedUnit = null;
                    setPlayerState(playerState.NORMAL);
                    System.out.println("State: NORMAL");
                }
                break;
            case ACTION_SELECTION:
                break;
            case ACTION:
                break;


        }
    }

    public enum PlayerStates {
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION
    }
}
