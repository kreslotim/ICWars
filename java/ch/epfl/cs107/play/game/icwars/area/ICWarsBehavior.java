package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class ICWarsBehavior extends AreaBehavior {
    /**
     * Default AreaBehavior Constructor
     *
     * @param window (Window): graphic context, not null
     * @param name   (String): name of the behavior image, not null
     */
    public ICWarsBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICWarsCellType color = ICWarsCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICWarsCell(x, y, color));
            }
        }
    }

    /**
     * commentaire
     **/
    public enum ICWarsCellType {
        NONE(0, 0),

        ROAD(16777216, 0),

        PLAIN(-14112955, 1),
        WOOD(-65536, 3),
        RIVER(-16776961, 0),
        MOUNTAIN(-256, 4),
        CITY(-1, 2);

        final int type;
        final int DefenceStars;

        ICWarsCellType(int type, int DefenceStars) {
            this.type = type;
            this.DefenceStars = DefenceStars;
        }

        public static ICWarsCellType toType(int type) {
            for (ICWarsCellType ict : ICWarsCellType.values()) {
                if (ict.type == type)
                    return ict;
            }
            // System.out.println(type);
            return NONE;
        }
    }

    /**
     * Cell adapted to the ICWars game
     */
    public class ICWarsCell extends AreaBehavior.Cell {
        /// Type of the cell following the enum
        private final ICWarsCellType type;

        /**
         * Default Cell constructor
         *
         * @param x    (int): x-coordinate of this cell
         * @param y    (int): y-coordinate of this cell
         * @param type (EnigmeCellType), not null
         */
        public ICWarsCell(int x, int y, ICWarsCellType type) {
            super(x, y);
            this.type = type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }


        @Override
        protected boolean canEnter(Interactable entity) {


            boolean containsNonTraversable = false;
            for (Interactable val : entities) {
                if (val.takeCellSpace()) {
                    containsNonTraversable = true;
                }

            }
            return !(containsNonTraversable && entity.takeCellSpace());
        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {

        }
    }
}
