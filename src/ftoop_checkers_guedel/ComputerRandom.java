package ftoop_checkers_guedel;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ComputerRandom implements Runnable{

    private List<Pane> playground = new ArrayList<>();
    private Game game;
    private ImageView checker;

    public ComputerRandom(Game game){
        this.game = game;
        playground = game.getPlayground();
    }

    @Override
    public void run() {

    }

    public void generateRandomMove(){

        Random randomPiece = new Random();

        int rp = randomPiece.nextInt(game.getH1().size());
        checker = (ImageView) playground.get((int)game.getH1().get(rp)).getChildren().get(0);

        game.moveAnimation(checker);
    }

}

