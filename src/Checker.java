import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Checker extends ImageView {

    private final int direction, dimension;
    private ArrayList<ArrayList> options;
    private Game game;

    public Checker(Image img, int direction, int dimension, Game game) {
        super(img);
        this.direction = direction;
        this.dimension = dimension;
        this.game = game;
        options = new ArrayList<>();
    }

    //Getter-Methods
    public int getDirection() { return direction; }
    public int getDimension() { return dimension; }
    public ArrayList<ArrayList> getOptions () { return options; }

    //Setter-Methods
    public void setOnMouseClick() {
        setOnMouseClicked((MouseEvent e) -> {
            game.setSelected(this);
            game.clearStyleH2();
            options.forEach(n -> game.setStyleH2((int)n.get(n.size()-1)));
        });
    }

    public void removeOnMouseClick() {
        setOnMouseClicked(null);
    }

    //wip...
    public void pull() {

    }

    public boolean jump(ArrayList<Integer> start, Image king, Game game) {
        //Stop if checker lands in the last row (convert to King)
        int startPos = start.get(start.size()-1);
        if (direction == -1) {
            if (startPos >= 0 && startPos <= dimension + direction) { return false; }
        } else if (direction == 1) {
            if (startPos >= dimension*dimension - dimension && startPos <= dimension*dimension - direction) { return false; }
        }

        //For each diagonal
        boolean oneMore = false;
        int x, y;
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
            if (!game.getPlayground().get(jumpPos).getChildren().isEmpty()) {
                Image img = ((ImageView)game.getPlayground().get(jumpPos).getChildren().get(0)).getImage();
                //Enemy
                if (!(img == this.getImage() || img == king)) {
                    int newPos = jumpPos + y * dimension + x;
                    //Field free (double jump not allowed)
                    if (game.getPlayground().get(newPos).getChildren().isEmpty()) {
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
                            options.add(pos);
                        }
                    }
                }
            }
        }

        if (oneMore) {
            //Keep only the biggest ArrayList
            int biggest = 0;
            for (int j = 0; j < options.size(); j++) {
                if (biggest < options.get(j).size()) { biggest = options.get(j).size(); }
            }
            for (Iterator<ArrayList> it = options.iterator(); it.hasNext();) {
                if (it.next().size() < biggest) { it.remove(); }
            }
        }

        return oneMore;
    }

}
