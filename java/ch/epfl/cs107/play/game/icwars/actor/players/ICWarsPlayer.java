package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.ICWars;
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

    protected List<Unit> unitsList;
    protected List<Area> areasList = new ArrayList<>();
    protected List<Unit> memorisedUnits = new ArrayList<>();
    private PlayerStates playerState;

    protected Unit selectedUnit; // à utiliser avec Interactable
    protected final ICWarsPlayerGUI gui = new ICWarsPlayerGUI(0, this); // @TODO


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

    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }


    public void startTurn() {
        setPlayerState(PlayerStates.NORMAL);
        // make all units of the player not Used

    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {

        if (getCurrentMainCellCoordinates().equals(coordinates.get(0)) && getPlayerState() == PlayerStates.SELECT_CELL) {
            setPlayerState(PlayerStates.NORMAL);
            System.out.println("onLeaving works!");
        }
        // Entreprendre les traitements nécessaires lorsque player quitte cellule
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction ( AreaInteractionVisitor v) {
        ((ICWarsInteractionVisitor)v). interactWith ( this );
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

    public void selectUnit(int unitIndex) {
        if (unitsList.size() > unitIndex) {
            selectedUnit = unitsList.get(unitIndex);
            gui.setSelectedUnit(unitsList.get(unitIndex));
            this.unitsList.get(unitIndex).isUsed();  // create a setter setUnitUsed
        }
    }

    public int getUnitIndex() {

        int index = 0;
        int myX = getCurrentMainCellCoordinates().x;
        int myY = getCurrentMainCellCoordinates().y;

        for (Unit unit : unitsList) {

            if ((int) unit.getPosition().x == myX && (int) unit.getPosition().y == myY) {
                return index;
            }
            ++index;
        }
        return 99999;
    }

    /**
     * get the playerState of the player
     */
    public PlayerStates getPlayerState() {
        return playerState;
    }

    /**
     * set the playerState of the player
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
        ACTION
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
                System.out.println("Normal");
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    setPlayerState(playerState.SELECT_CELL);
                    System.out.println("State: SELECT_CELL");

                }
                if (keyboard.get(Keyboard.TAB).isReleased()) {
                    setPlayerState(playerState.IDLE);
                }
                break;
            case SELECT_CELL:
                this.selectUnit(getUnitIndex());
                if (selectedUnit != null) {

                    interactWith(selectedUnit);
                    System.out.println("memorized!");
                    setPlayerState(playerState.MOVE_UNIT);
                    System.out.println("State: MOVE_UNIT");
                }

                else {
                    onLeaving((getLeftCells()));
                } // onLeaving() sur la position de l'unité...
                break;

            case MOVE_UNIT:
                if (keyboard.get(Keyboard.ENTER).isReleased()) {
                    // move the selectedUnit to currentSpace
                    // mark memorisedUnit as used

                    selectedUnit.changePosition(new DiscreteCoordinates(getCurrentMainCellCoordinates().x,getCurrentMainCellCoordinates().y));

                    //selectedUnit.isUsed() ? sprite.setAlpha(0.5f) : sprite.setAlpha(1.f)
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
}
