import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Checker extends ImageView {

    private final int direction, dimension;
    private ArrayList<ArrayList> options;

    public Checker(Image img, int direction, int dimension) {
        super(img);
        this.direction = direction;
        this.dimension = dimension;
        options = new ArrayList<>();

        //wip...
        setOnMouseClicked((MouseEvent e) -> {
            System.out.println(this);
        });
    }

    //Getter-Methods
    public int getDirection() { return direction; }
    public int getDimension() { return dimension; }
    public ArrayList<ArrayList> getOptions () { return options; }

    //wip: change to pull
    public void diagonalForward(int pos, Image king, Game game) {
        int left = pos + direction * dimension + direction;
        //Left edge
        if (pos % dimension == 0) { left = -1; }
        //Field not free
        else if (!game.getPlayground().get(left).getChildren().isEmpty()) {
            Image img = ((ImageView)game.getPlayground().get(left).getChildren().get(0)).getImage();
            //Own
            if (img == this.getImage() || img == king) { left = -1; }
            //Enemy
            else {
                left = left + direction * dimension + direction;
                //Field not free (double jump not allowed)
                if (!game.getPlayground().get(left).getChildren().isEmpty()) { left = -1; }
            }
        }

        //wip: same like left (- instead of +)
        int right = pos + direction * dimension - direction;
    }

    public boolean jump(ArrayList<Integer> start, Image king, Game game) {
        boolean oneMore = false;
        int startPos = start.get(start.size()-1);
        int i = 0;
        int j = 4;
        int x, y;

        //Left edge
        if (startPos % dimension == 0) { i = 2; }
        //Right edge
        else if ((startPos+1) % dimension == 0) { j = 2; }

        //For each diagonal
        for (; i < j; i++) {
            //Front left
            if (i == 0) { x = -1; y = 1; }
            //Rear left
            else if (i == 1) { x = -1; y = -1; }
            //Front right
            else if (i == 2) { x = 1; y = 1; }
            //Rear right
            else { x = 1; y = -1; }

            int jumpPos = startPos + (direction*y) * dimension + (direction*x);
            //Field not free
            if (!game.getPlayground().get(jumpPos).getChildren().isEmpty()) {
                Image img = ((ImageView)game.getPlayground().get(jumpPos).getChildren().get(0)).getImage();
                //Enemy
                if (!(img == this.getImage() || img == king)) {
                    int newPos = jumpPos + (direction*y) * dimension + (direction*x);
                    //Field free (double jump not allowed)
                    if (game.getPlayground().get(newPos).getChildren().isEmpty()) {
                        //Inside the playground
                        if (newPos < 100 && newPos >= 0) {
                            //Already jumped over current Enemy
                            boolean already = false;
                            if (start.size() > 1) {
                                for (int a = 1; a < start.size(); a++) {
                                    if (2 * (jumpPos - start.get(a-1)) == start.get(a) - start.get(a-1)) {
                                        already = true;
                                        break;
                                    }
                                }
                            }
                            //Save newPos in a new ArrayList
                            if (already == false) {
                                oneMore = true;
                                ArrayList<Integer> pos = new ArrayList<>();
                                pos = start;
                                pos.add(newPos);
                                options.add(pos);
                            }
                        }
                    }
                }
            }
        }

        //Keep only the biggest ArrayList
        int biggest = 0;
        for (int a = 0; a < options.size(); a++) {
            if (biggest < options.get(a).size()) { biggest = options.get(a).size(); }
        }
        for (Iterator<ArrayList> it = options.iterator(); it.hasNext();) {
            if (it.next().size() < biggest) { it.remove(); }
        }

        return oneMore;
    }

}
