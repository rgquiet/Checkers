import java.util.ArrayList;
import javafx.scene.image.Image;

public class King extends Piece {

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    @Override
    void pull() {
        //wip..
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image king) {
        //wip...
        return false;
    }

}
