import java.util.ArrayList;
import javafx.scene.image.Image;

public class King extends Piece {

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    @Override
    void pull(int startPos) {
        int dimension = super.getPlayer().getGame().getDimension();

        int x, y;
        for (int i = 0; i < 4; i++) {
            //Front left
            if (i == 0) { y = 1; x = -1; }
            //Front right
            else if (i == 1) { y = 1; x = 1; }
            //Rear left
            else if (i == 2) { y = -1; x = -1; }
            //Rear right
            else if (i == 3) { y = -1; x = 1; }

            /*
            1. Für jede Richtung bis stop
            2. Neue ArrayList<Integer> mit
                2.1 Erster Durchgang: startPos
                2.2 Sonst: getOptions.get(getOptions.size()-1)
            2. Feld frei -> false = stop
            3. Feld am Rand -> letzter Durchgang
            */
        }
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image checker) {
        //wip...
        return false;
    }

}
