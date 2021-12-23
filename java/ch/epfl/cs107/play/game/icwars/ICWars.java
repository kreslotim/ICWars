package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.Tank;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;


public class ICWars extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 10.f;
    private final String[] areas = {"icwars/Level0", "icwars/Level1"};
    private final String[] areasResult = {"icwars/GAMEOVER", "icwars/VICTORY"};
    private boolean won = true;

    private List<RealPlayer> icWarsPlayerList = new ArrayList<>();
    private List<RealPlayer> currentRound = new ArrayList<>();
    private List<RealPlayer> nextRound = new ArrayList<>();
    private RealPlayer currentlyActivePlayer;

    private RealPlayer player1;
    private RealPlayer player2;

    private int playerIndex = 0;
    private int areaIndex;
    private GameStates gameState;


    /******************************************************************************************************************
                                            |-- WELCOME TO ICWARS --|
     ******************************************************************************************************************/
    /**
     * Title of the window
     *
     * @return title (String)
     */
    @Override
    public String getTitle() {
        return "ICWars";
    }

    /**
     * @param window
     * @param fileSystem
     * @return true if the game must begin
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {

        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            return true;
        }
        return false;
    }

    /**
     * Add all the areas to the game
     */
    private void createAreas() {

        addArea(new Level0());
        addArea(new Level1());

        addArea(new Victory());
        addArea(new GameOver());

    }

    /**
     * Initializing the grid, and players
     *
     * @param areaKey
     */
    private void initArea(String areaKey) {

        ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coordsAlly = area.getPlayerSpawnPosition();
        DiscreteCoordinates coordsEnemy = area.getEnemySpawnPosition();

        player1 = new RealPlayer(area, coordsAlly, ICWarsActor.Faction.ALLY,
                new Tank(area, new DiscreteCoordinates(2, 5), ICWarsActor.Faction.ALLY),
                new Soldier(area, new DiscreteCoordinates(3, 5), ICWarsActor.Faction.ALLY));

        player2 = new RealPlayer(area, coordsEnemy, ICWarsActor.Faction.ENEMY,
                new Tank(area, new DiscreteCoordinates(8, 5), ICWarsActor.Faction.ENEMY),
                new Soldier(area, new DiscreteCoordinates(9, 5), ICWarsActor.Faction.ENEMY));

        player1.enterArea(area, coordsAlly);
        player2.enterArea(area, coordsEnemy);

        //clearing and adding all players to a new level
        icWarsPlayerList.clear();
        icWarsPlayerList.add(player1);
        icWarsPlayerList.add(player2);

        // default state of the game
        setGameState(GameStates.INIT);

        player1.centerCamera(); // redundant, but it's faster...
        currentRound.addAll(icWarsPlayerList);
    }

    /**
     * Main update method, for a player,
     * allowing to manipulate the game through the keyboard
     *
     * @param deltaTime (float) frequency of update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Keyboard keyboard = getWindow().getKeyboard();
        if (keyboard.get(Keyboard.N).isReleased()) {
            switchArea();
            if (getCurrentArea().getTitle().equals("icwars/Level0") && keyboard.get(Keyboard.N).isReleased()) {
                end();
            }
        }

        if (keyboard.get(Keyboard.R).isReleased()) {
            reset();
        }

        if (keyboard.get(Keyboard.TAB).isReleased()) {
            switchTurn();
        }

        /**
         if ( keyboard.get(Keyboard.U).isReleased ()) {
         System.out.println("test");
         currentlyActivePlayer.selectUnit(1) ; // 0, 1 ...
         } // draws the range, of a selected unit, with some index (int).
         // works only if a player is in "SELECT_CELL" state (after pushing "Enter")
         */

        updateGameStates();
    }


    /******************************************************************************************************************
     ***********************************     FINITE STATE AUTOMATON     ***********************************************
     ******************************************************************************************************************/

    /**
     * Automaton that changes the behaviour of the Game
     */
    public void updateGameStates() {

        switch (gameState) {
            case INIT:
                System.out.println("LET THE GAME BEGIN");

                nextRound.addAll(icWarsPlayerList);
                setGameState(GameStates.CHOOSE_PLAYER);
                break;
            case CHOOSE_PLAYER:
                if (currentRound.isEmpty()) {
                    setGameState(GameStates.END_TURN);
                } else {
                    currentlyActivePlayer = currentRound.get(0);

                    currentRound.remove(currentlyActivePlayer);

                    setGameState(GameStates.START_PLAYER_TURN);
                }

                break;
            case START_PLAYER_TURN:
                currentlyActivePlayer.startTurn();

                setGameState(GameStates.PLAYER_TURN);

                break;
            case PLAYER_TURN:
                if (currentlyActivePlayer.getPlayerState().equals(RealPlayer.PlayerStates.IDLE)) {

                    setGameState(GameStates.END_PLAYER_TURN);
                }

                break;
            case END_PLAYER_TURN:
                if (currentlyActivePlayer.isDefeated()) {

                    nextRound.remove(currentlyActivePlayer);
                    currentlyActivePlayer.leaveArea();
                    setGameState(GameStates.CHOOSE_PLAYER);
                } else {

                    nextRound.add(currentlyActivePlayer);
                    nextRound.remove(0);

                    for (Unit u : currentlyActivePlayer.getPlayerUnitsList()) {
                        u.setIsUsedUnit(false);
                    } // set all units as usable again

                    setGameState(GameStates.CHOOSE_PLAYER);

                }

                break;
            case END_TURN:

                if (nextRound.size() < 2) {
                    setGameState(GameStates.END);
                    System.out.println("END");
                } else {
                    currentRound.addAll(nextRound);
                    setGameState(GameStates.CHOOSE_PLAYER);
                }
                break;
            case END:
                if ((getCurrentArea().getTitle().equals("icwars/Level0"))) switchArea();
                else {
                    if (nextRound.get(0).getFaction().equals(ICWarsActor.Faction.ENEMY)) won = false;
                    else if (nextRound.get(0).getFaction().equals(ICWarsActor.Faction.ALLY)) won = true;
                    end();
                }
                break;
        }
    }

    /******************************************************************************************************************
     *                                    All methods used in the Automaton
     ******************************************************************************************************************/
    /**
     * sets the gameState of the player
     *
     * @param gameState
     */
    public void setGameState(GameStates gameState) {
        this.gameState = gameState;
    }

    /**
     * Switches rounds between players, whenever user hits "TAB"
     */
    public void switchTurn() {
        currentlyActivePlayer.setPlayerState(RealPlayer.PlayerStates.IDLE);

        for (Unit u : currentlyActivePlayer.getPlayerUnitsList()) {
            u.setIsUsedUnit(false);
        }

        playerIndex++;
        currentlyActivePlayer = icWarsPlayerList.get(playerIndex % icWarsPlayerList.size());

        currentlyActivePlayer.startTurn();
    }

    /**
     * Jumps to next level
     */
    protected void switchArea() {

        player1.leaveArea();
        player2.leaveArea();

        areaIndex++;
        areaIndex = areaIndex % areas.length;

        ICWarsArea currentArea = (ICWarsArea) setCurrentArea(areas[areaIndex], false);
        initArea(currentArea.getTitle());

        reset();
    }

    /**
     * Resets the game, on current level (must be executed when changing level)
     *
     * @return true if executed
     */
    public boolean reset() {
        createAreas();

        nextRound.clear();
        currentRound.clear();
        initArea(getCurrentArea().getTitle());

        return true;
    }

    /**
     * Ends the game
     */
    @Override
    public void end() {
        System.out.println("Game Over");

        if (won == true) {
            setCurrentArea(areasResult[1], false);
            icWarsPlayerList.clear();


        } else {
            setCurrentArea(areasResult[0], false);
            icWarsPlayerList.clear();

        }
        //System.exit(0);
        //switchArea(); // If need to restart game after final level
    }


    /**
     * Enumeration of all States of the Game
     */
    public enum GameStates {INIT, CHOOSE_PLAYER, START_PLAYER_TURN, PLAYER_TURN, END_PLAYER_TURN, END_TURN, END;}
}
