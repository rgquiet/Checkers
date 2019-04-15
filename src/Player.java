import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

    private ArrayList<ImageView> pieces;

    public Player(ArrayList<Pane> playground, Image checker, int dimension, int sizeWindow, int start) {
        pieces = new ArrayList<>();
        //Create 20 pieces for current Player at the right start position
        for (int i = 20; start < start+i; start += 2) {
            //New Row
            if (pieces.size() % 5 == 0) {
                //even
                if (pieces.size()/5 % 2 == 0) { start += 1; }
                //odd
                else { start -= 1; }
            }
            ImageView img = new ImageView(checker);
            img.setFitHeight(sizeWindow/dimension-2);
            img.setFitWidth(sizeWindow/dimension-2);

            playground.get(start).getChildren().add(img);
            pieces.add(img);
            i--;
        }
    }

    //Getter-Methods
    public ArrayList<ImageView> getPieces() { return pieces; }

}
