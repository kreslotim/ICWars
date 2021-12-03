package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;

public class ICWars extends AreaGame {

    //class equivalente à Tuto2 (copy paste?)

    public final static float CAMERA_SCALE_FACTOR = 10.f;

    private final String[] areas = {"icwars/Level0" , "icwars/Level1"};

    @Override
    public String getTitle() {
        return "ICWars ;-)";
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
