package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.Tank;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;


public class ICWars extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 10.f;
    private final String[] areas = {"icwars/Level0" , "icwars/Level1"};
    private RealPlayer player1;
    private RealPlayer player2;
    private int areaIndex;
    private GameStates gameState;
    private List<ICWarsPlayer> currentRound = new ArrayList<>();
    private List<ICWarsPlayer> nextRound = new ArrayList<>();
    private ICWarsPlayer currentlyActivePlayer;

    private List<ICWarsPlayer> icWarsPlayers = new ArrayList(){{ add(player1);
                                                                 add(player2); }};

    @Override
    public String getTitle() {
        return "ICWars";
    }

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
     * Add all the areas
     */
    private void createAreas(){

        addArea(new Level0());
        addArea(new Level1());

    }

    private void initArea(String areaKey) {

        ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coordsAlly = area.getPlayerSpawnPosition();
        DiscreteCoordinates coordsEnemy = area.getEnemySpawnPosition();

        player1 = new RealPlayer(area, coordsAlly, ICWarsActor.Faction.ALLY,
                 new Tank(area, new DiscreteCoordinates(2,5), ICWarsActor.Faction.ALLY),
                 new Soldier(area,  new DiscreteCoordinates(3, 5), ICWarsActor.Faction.ALLY));

        player2 = new RealPlayer(area, coordsEnemy, ICWarsActor.Faction.ENEMY,
                new Tank(area, new DiscreteCoordinates(8,5), ICWarsActor.Faction.ENEMY),
                new Soldier(area,  new DiscreteCoordinates(9, 5), ICWarsActor.Faction.ENEMY));

        player1.enterArea(area, coordsAlly);
        player2.enterArea(area,coordsEnemy);
        player1.centerCamera();
        player1.startTurn();
    }

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

        //nextRound.remove(unit); // accès au Units?

    }


    public boolean reset() {
        // reinitialiser les joueurs
        // les mettre au centre
        //player.centerCamera();
        createAreas();
        initArea(getCurrentArea().getTitle());
        return true;
    }

    @Override
    public void end() {
        System.out.println("Game Over");

    }

    protected void switchArea() {

        player1.leaveArea();

        areaIndex = (areaIndex==0) ? 1 : 0;


        ICWarsArea currentArea = (ICWarsArea)setCurrentArea(areas[areaIndex], false);
        player1.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
        player1.centerCamera();

    }


    /**
     * get the gameState of the player
     */
    public GameStates getGameState() {
        return gameState;
    }

    /**
     * set the gameState of the player
     * @param gameState
     */
    public void setGameState(GameStates gameState) {
        this.gameState = gameState;
    }


    public enum GameStates {
        INIT,
        CHOOSE_PLAYER,
        START_PLAYER_TURN,
        PLAYER_TURN,
        END_PLAYER_TURN,
        END_TURN,
        END;

    }

    public void switchGameStates(GameStates gameState) {
        Keyboard keyboard = getWindow().getKeyboard();
        switch (gameState) {
            case INIT:
                nextRound.add(player1);
                nextRound.add(player2);
                setGameState(GameStates.CHOOSE_PLAYER);
                break;
            case CHOOSE_PLAYER:
                if (currentRound.isEmpty()) {
                    setGameState(GameStates.END_TURN);
                }
                else {
                    currentlyActivePlayer = currentRound.get(0); //
                    currentRound.remove(currentlyActivePlayer);
                    setGameState(GameStates.START_PLAYER_TURN);
                }

                break;
            case START_PLAYER_TURN:
                break;
            case PLAYER_TURN:
                break;
            case END_PLAYER_TURN:
                break;
            case END_TURN:
                break;
            case END:
                break;
        }
    }


}
