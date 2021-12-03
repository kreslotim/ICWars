package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;

public class ICWarsArea extends Area {

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }
}
