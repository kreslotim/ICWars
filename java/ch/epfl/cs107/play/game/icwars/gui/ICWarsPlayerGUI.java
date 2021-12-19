package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {
    public final static float FONT_SIZE = 20f;
    private Unit selectedUnit;
    private Unit infoPanelUnit;
    private ICWarsPlayer player;

    private ICWarsBehavior.ICWarsCellType myCell;

    private ICWarsActionsPanel panelOfActions;
    private ICWarsInfoPanel panelOfInfo;

    public ICWarsPlayerGUI(float cameraScaleFactor, ICWarsPlayer player) {
        this.panelOfInfo = new ICWarsInfoPanel(cameraScaleFactor);
        this.panelOfActions = new ICWarsActionsPanel(cameraScaleFactor);
        this.player = player;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void setCurrentCell(ICWarsBehavior.ICWarsCellType cell) { myCell = cell;}

    public void setPanelOfInfoForUnit(Unit infoPanelUnit) {
        this.infoPanelUnit = infoPanelUnit;
    }





    /**
     * invoking the method
     * drawRangeAndPathTo on the unit selected by the player whose display it is in charge
     * of, so that it draws the unit’s range of action and the path separating the unit from
     * the player
     **/
    @Override
    public void draw(Canvas canvas) {

        panelOfInfo.setCurrentCell(myCell);
        panelOfInfo.setUnit(infoPanelUnit);

        panelOfInfo.draw(canvas);



        if (player.getPlayerState().equals(ICWarsPlayer.PlayerStates.ACTION_SELECTION)) {
            panelOfActions.setActions(selectedUnit.getAction());
            panelOfActions.draw(canvas);
        }

        if (player.getPlayerState().equals(ICWarsPlayer.PlayerStates.MOVE_UNIT)) {
            selectedUnit.drawRangeAndPathTo(new DiscreteCoordinates((int) player.getPosition().x,
                                                                    (int) player.getPosition().y), canvas);
        }




    }
}
