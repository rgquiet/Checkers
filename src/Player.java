import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;

public class Player {

    private Game game;
    private ArrayList<Checker> pieces;
    private final Image kingImg;

    public Player(Image img, Image king, Game game, int direction, int start) {
        kingImg = king;
        this.game = game;

        //Create 20 pieces for current Player at the right start position
        pieces = new ArrayList<>();
        for (int i = 20; start < start+i; start += 2) {
            //New Row
            if (pieces.size() % 5 == 0) {
                //Even
                if (pieces.size()/5 % 2 == 0) { start += 1; }
                //Odd
                else { start -= 1; }
            }
            Checker checker = new Checker(img, direction, game.getDimension());
            checker.setFitHeight(game.getSizeWindow()/game.getDimension()-2);
            checker.setFitWidth(game.getSizeWindow()/game.getDimension()-2);

            game.getPlayground().get(start).getChildren().add(checker);
            pieces.add(checker);
            i--;
        }
    }

    //Getter-Methods
    public ArrayList<Checker> getPieces() { return pieces; }

    //Setter-Methods
    public void setKing(int i) {
        King king = new King(kingImg, pieces.get(i).getDirection(), pieces.get(i).getDimension());
        king.setFitHeight(game.getSizeWindow()/game.getDimension()-2);
        king.setFitWidth(game.getSizeWindow()/game.getDimension()-2);

        int j = game.getPlayground().indexOf(pieces.get(i).getParent());
        game.getPlayground().get(j).getChildren().remove(pieces.get(i));
        game.getPlayground().get(j).getChildren().add(king);
        pieces.set(i, king);
    }

    public void checkOptions() {
        pieces.forEach(n -> {
            ArrayList<Integer> start = new ArrayList<>();
            start.add(game.getPlayground().indexOf(n.getParent()));
            if (n.jump(start, kingImg, game)) {
                boolean repeat = true;
                while (repeat) {
                    repeat = false;
                    for (Iterator<ArrayList> it = n.getOptions().iterator(); it.hasNext();) {
                        if (n.jump(it.next(), kingImg, game)) { repeat = true; }
                    }
                }
            }
        });
    }

}
