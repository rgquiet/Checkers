import java.util.ArrayList;
import javafx.scene.image.Image;

public class King extends Piece {

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    @Override
    void pull(int startPos) {
        //wip...
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image checker) {
        //wip...
        return false;
    }

}
