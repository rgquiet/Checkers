import java.util.ArrayList;
import javafx.scene.image.Image;

public class Player {

    private ArrayList<Checker> pieces;
    private Game game;
    private final int direction;

    public Player(Game game, Image img, int start, int direction) {
        this.game = game;
        this.direction = direction;

        //Create 20 pieces for current Player at the right start position
        pieces = new ArrayList<>();
        for (int i = 20; start < start+i; start += 2) {
            //New Row
            if (pieces.size() % 5 == 0) {
                //even
                if (pieces.size()/5 % 2 == 0) { start += 1; }
                //odd
                else { start -= 1; }
            }
            Checker checker = new Checker(img);
            checker.setFitHeight(game.getSizeWindow()/game.getDimension()-2);
            checker.setFitWidth(game.getSizeWindow()/game.getDimension()-2);

            game.getPlayground().get(start).getChildren().add(checker);
            pieces.add(checker);
            i--;
        }
    }

    //Getter-Methods
    public ArrayList<Checker> getPieces() { return pieces; }

    public void pull() {
        int dimension = game.getDimension();
        pieces.forEach(n -> {
            int i = game.getPlayground().indexOf(n.getParent()) + dimension * direction;

            //Save diagonal left field
            int left = i - 1;
            //Save diagonal right field
            int right = i + 1;

            //left edge or not free
            if (i % dimension == 0) { left = -1; }
            else if (!game.getPlayground().get(left).getChildren().isEmpty()) { left = -1; }
            //right edge or not free
            if ((i+1) % dimension == 0) { right = -1; }
            else if (!game.getPlayground().get(right).getChildren().isEmpty()) { right = -1; }

            n.setPullLeft(left);
            n.setPullRight(right);
        });
    }

    //wip...
    public void jump() {

    }

}
