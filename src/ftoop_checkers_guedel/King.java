package ftoop_checkers_guedel;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class King extends Piece {

    public King(Image img, Image buddy, Player player) {
        super(img, buddy, player);
    }

    @Override
    void pull(int startPos) {
        int dimension = getPlayer().getGame().getDimension();

        //Down left, Down right, Up left, Up right
        int[] diagonal = {dimension-1, dimension+1, -dimension-1, -dimension+1};
        for (int i = 0; i < diagonal.length; i++) {
            int jumpPos = startPos;

            boolean next = outside(i, jumpPos);
            while (next) {
                jumpPos += diagonal[i];
                if (getPlayer().getGame().getPlayground().get(jumpPos).getChildren().isEmpty()) {
                    next = !up(jumpPos) && !down(jumpPos) && !left(jumpPos) && !right(jumpPos);
                    ArrayList<Integer> pos = new ArrayList<>();
                    pos.add(startPos);
                    pos.add(jumpPos);
                    getOptions().add(pos);
                } else { next = false; }
            }
        }
    }

    @Override
    boolean jump(ArrayList<Integer> start) {
        int startPos = start.get(start.size()-1);
        int dimension = getPlayer().getGame().getDimension();

        boolean more = false;
        //Down left, Down right, Up left, Up right
        int[] diagonal = {dimension-1, dimension+1, -dimension-1, -dimension+1};
        for (int i = 0; i < diagonal.length; i++) {
            List<Pane> playground = getPlayer().getGame().getPlayground();
            int jumpPos = startPos;

            boolean kill = false;
            boolean next = outside(i, jumpPos);

            while (next) {
                jumpPos += diagonal[i];
                if (getPlayer().getGame().getPlayground().get(jumpPos).getChildren().isEmpty()) {
                    next = !up(jumpPos) && !down(jumpPos) && !left(jumpPos) && !right(jumpPos);
                    if (kill) {
                        more = true;
                        ArrayList<Integer> pos = new ArrayList<>(start);
                        pos.add(jumpPos);
                        getOptions().add(pos);
                    }
                } else {
                    //Enemy
                    Image img = ((ImageView) playground.get(jumpPos).getChildren().get(0)).getImage();
                    if (!kill && !(img == getImage() || img == getBuddy())) {
                        next = !up(jumpPos) && !down(jumpPos) && !left(jumpPos) && !right(jumpPos);
                        //Already jumped over current Enemy
                        boolean already = false;
                        if (start.size() > 1) {
                            for (int j = 1; j < start.size(); j++) {
                                int from = start.get(j-1);
                                int to = start.get(j);
                                int diag = getPlayer().getGame().setDiagonal(from, to);

                                while (from != to && diag != 0) {
                                    from += diag;
                                    if (jumpPos == from) {
                                        already = true;
                                        break;
                                    }
                                }
                            }
                        }
                        kill = !already;
                    } else { next = false; }
                }
            }
        }

        if (more) { removeSmallerOptions(); }
        return more;
    }

    public boolean outside(int i, int pos) {
        boolean out = false;

        if (i == 0) { out = !down(pos) && !left(pos); }
        else if (i == 1) { out = !down(pos) && !right(pos); }
        else if (i == 2) { out = !up(pos) && !left(pos); }
        else if (i == 3){ out = !up(pos) && !right(pos); }

        return out;
    }

}
