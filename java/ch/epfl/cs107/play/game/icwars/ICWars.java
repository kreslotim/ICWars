package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Soldier;
import ch.epfl.cs107.play.game.icwars.actor.Tank;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.game.tutosSolution.actor.GhostPlayer;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Ferme;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public class ICWars extends AreaGame {

    //class equivalente à Tuto2 (copy paste?)

    public final static float CAMERA_SCALE_FACTOR = 10.f;
    private final String[] areas = {"icwars/Level0" , "icwars/Level1"};
    private RealPlayer player;
    private int areaIndex;

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
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new RealPlayer(area, coords, ICWarsActor.Faction.ALLY,
                 new Tank(area, new DiscreteCoordinates(2,5), ICWarsActor.Faction.ALLY),
                 new Soldier(area, new DiscreteCoordinates(3,5), ICWarsActor.Faction.ALLY));
        player.enterArea(area, coords);
        player.centerCamera();
    }

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getWindow().getKeyboard();
        if (keyboard.get(Keyboard.N).isReleased()) {
            switchArea();
        }

        super.update(deltaTime);

        if (keyboard.get(Keyboard.R).isReleased()) {
            reset();
        }

    }

    public boolean reset() {
        // reinitialiser les joueurs
        player.getPosition();
        // les mettre au centre
        player.centerCamera();
        createAreas();
        initArea(getCurrentArea().getTitle());
        return true;
    }

    @Override
    public void end() {
        System.out.println("Game Over");
    }

    protected void switchArea() {

        player.leaveArea();

        areaIndex = (areaIndex==0) ? 1 : 0;

        ICWarsArea currentArea = (ICWarsArea)setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
        player.centerCamera();


    }


}
