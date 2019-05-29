import java.util.ArrayList;
import javafx.scene.image.Image;

public class King extends Piece {

    public King(Image img, Player player) {
        super(img, player);
    }

    @Override
    void pull(int startPos) {
        int dimension = getPlayer().getGame().getDimension();

        //Down left, Down right, Up left, Up right
        int[] diagonal = {dimension-1, dimension+1, -dimension-1, -dimension+1};
        for (int d: diagonal) {
            int jumpPos = startPos;

            boolean more;
            if (d < 0) { more = !up(jumpPos); }
            else { more = !down(jumpPos); }

            while (more) {
                jumpPos += d;
                if (getPlayer().getGame().getPlayground().get(jumpPos).getChildren().isEmpty()) {
                    more = !up(jumpPos) && !down(jumpPos) && !left(jumpPos) && !right(jumpPos);
                    ArrayList<Integer> pos = new ArrayList<>();
                    pos.add(startPos);
                    pos.add(jumpPos);
                    getOptions().add(pos);
                } else {
                    more = false;
                }
            }
        }
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image checker) {
        //wip...
        return false;
    }

}
