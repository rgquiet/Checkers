import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Checker extends Piece {

    private final int direction;

    public Checker(Image img, Image buddy, Player player, int direction) {
        super(img, buddy, player);
        this.direction = direction;
    }

    @Override
    void pull(int startPos) {
        //Not on the edge
        if (!left(startPos)) {
            //Pull left
            int left = (startPos-1) + getPlayer().getGame().getDimension() * direction;
            setPull(startPos, left);
        }
        if (!right(startPos)) {
            //Pull right
            int right = (startPos+1) + getPlayer().getGame().getDimension() * direction;
            setPull(startPos, right);
        }
    }

    private void setPull(int startPos, int x) {
        ArrayList<Pane> playground = getPlayer().getGame().getPlayground();
        if (playground.get(x).getChildren().isEmpty()) {
            ArrayList<Integer> pos = new ArrayList<>();
            pos.add(startPos);
            pos.add(x);
            getOptions().add(pos);
        }
    }

    @Override
    boolean jump(ArrayList<Integer> start) {
        //Stop if checker lands in the last row (convert to King)
        int startPos = start.get(start.size()-1);
        if (otherside(startPos)) { return false; }

        int x, y;
        boolean more = false;
        //For each diagonal
        for (int i = 0; i < 4; i++) {
            ArrayList<Pane> playground = getPlayer().getGame().getPlayground();
            int dimension = getPlayer().getGame().getDimension();

            //Down left
            if (i == 0 && !down(startPos+dimension) && !left(startPos-1) && !right(startPos-1)) { y = 1; x = -1; }
            //Down right
            else if (i == 1 && !down(startPos+dimension) && !left(startPos+1) && !right(startPos+1)) { y = 1; x = 1; }
            //Up left
            else if (i == 2 && !up(startPos-dimension) && !left(startPos-1) && !right(startPos-1)) { y = -1; x = -1; }
            //Up right
            else if (i == 3 && !up(startPos-dimension) && !left(startPos+1) && !right(startPos+1)) { y = -1; x = 1; }
            else { continue; }

            int jumpPos = startPos + y * dimension + x;
            //Field not free
            if (!playground.get(jumpPos).getChildren().isEmpty()) {
                //Enemy
                Image img = ((ImageView)playground.get(jumpPos).getChildren().get(0)).getImage();
                if (!(img == getImage() || img == getBuddy())) {
                    int newPos = jumpPos + y * dimension + x;
                    //Field free (double jump not allowed)
                    if (playground.get(newPos).getChildren().isEmpty()) {
                        //Already jumped over current Enemy
                        boolean already = false;
                        if (start.size() > 1) {
                            for (int j = 1; j < start.size(); j++) {
                                if (2 * (jumpPos - start.get(j-1)) == start.get(j) - start.get(j-1)) {
                                    already = true;
                                    break;
                                }
                            }
                        }
                        //Save newPos in a new ArrayList
                        if (!already) {
                            more = true;
                            ArrayList<Integer> pos = new ArrayList<>(start);
                            pos.add(newPos);
                            getOptions().add(pos);
                        }
                    }
                }
            }
        }

        if (more) { removeSmallerOptions(); }
        return more;
    }

    public boolean otherside(int pos) {
        if (direction == -1) {
            return up(pos);
        } else {
            return down(pos);
        }
    }

}
