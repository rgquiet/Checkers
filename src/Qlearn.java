import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class Qlearn implements Runnable{

    ArrayList<Pane> playground = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> q = new HashMap<>();
    Game game;

    public Qlearn(Game game){
        this.game = game;
        playground = game.getPlayground();
    }

    @Override
    public void run() {

        generateState();

    }

    public String generateState(){
        String state = "";

        for(Pane p : playground){
            if(p.getChildren().isEmpty()){
                state = state + "x_";
            }
            else if(!p.getChildren().isEmpty()){
                Piece target = (Piece) p.getChildren().get(0);
                if(target.getPlayer() == game.getBlackPlayer()){
                    state = state + "b_";
                }
                else {
                    state = state + "w_";
                }
            }
        }
        System.out.println(state);
        return state;
    }
}
