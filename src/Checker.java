import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Checker extends Piece {

    public Checker(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    @Override
    void pull(int startPos) {
        int dimension = super.getPlayer().getGame().getDimension();

        //Not on the edge
        if (startPos % dimension != 0) {
            //Pull left
            int left = (startPos-1) + dimension * super.getDirection();
            setPull(startPos, left);
        }
        if ((startPos+1) % dimension != 0) {
            //Pull right
            int right = (startPos+1) + dimension * super.getDirection();
            setPull(startPos, right);
        }
    }

    public void setPull(int startPos, int x) {
        ArrayList<Pane> playground = super.getPlayer().getGame().getPlayground();
        if (playground.get(x).getChildren().isEmpty()) {
            ArrayList<Integer> pos = new ArrayList<>();
            pos.add(startPos);
            pos.add(x);
            super.getOptions().add(pos);
        }
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image king) {
        int dimension = super.getPlayer().getGame().getDimension();
        ArrayList<Pane> playground = super.getPlayer().getGame().getPlayground();

        //Stop if checker lands in the last row (convert to King)
        int startPos = start.get(start.size()-1);
        if (otherside(startPos, dimension)) { return false; }

        //For each diagonal
        boolean oneMore = false;
        int x, y;
        //wip: Refactoring (no absolute value -> 1, 8)
        for (int i = 0; i < 4; i++) {
            //Front left
            if (i == 0 && startPos / dimension < 8 && startPos % dimension > 1) { y = 1; x = -1; }
            //Front right
            else if (i == 1 && startPos / dimension < 8 && startPos % dimension < 8) { y = 1; x = 1; }
            //Rear left
            else if (i == 2 && startPos / dimension > 1 && startPos % dimension > 1) { y = -1; x = -1; }
            //Rear right
            else if (i == 3 && startPos / dimension > 1 && startPos % dimension < 8) { y = -1; x = 1; }
            else { continue; }

            int jumpPos = startPos + y * dimension + x;
            //Field not free
            if (!playground.get(jumpPos).getChildren().isEmpty()) {
                Image img = ((ImageView)playground.get(jumpPos).getChildren().get(0)).getImage();
                //Enemy
                if (!(img == this.getImage() || img == king)) {
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
                            oneMore = true;
                            ArrayList<Integer> pos = new ArrayList<>(start);
                            pos.add(newPos);
                            super.getOptions().add(pos);
                        }
                    }
                }
            }
        }

        if (oneMore) {
            //Keep only the biggest ArrayList
            int biggest = 0;
            for (int j = 0; j < super.getOptions().size(); j++) {
                if (biggest < super.getOptions().get(j).size()) { biggest = super.getOptions().get(j).size(); }
            }
            for (Iterator<ArrayList> it = super.getOptions().iterator(); it.hasNext();) {
                if (it.next().size() < biggest) { it.remove(); }
            }
        }

        return oneMore;
    }

    public boolean otherside(int pos, int dimension) {
        if (super.getDirection() == -1) {
            if (pos >= 0 && pos <= dimension + super.getDirection()) { return true; }
        } else if (super.getDirection() == 1) {
            if (pos >= dimension*dimension - dimension && pos <= dimension*dimension - super.getDirection()) { return true; }
        }
        return false;
    }

}
