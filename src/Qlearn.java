import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.HashMap;

public class Qlearn implements Runnable{

    ArrayList<Pane> playground = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> q = new HashMap<>();
    ArrayList<String> prevStates;
    Game game;

    public Qlearn(Game game){
        this.game = game;
        playground = game.getPlayground();
    }

    @Override
    public void run() {

        generateState();

    }

    public void setQ(){
        q.put(generateState(), getMoves());
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
        return state;
    }

    public ArrayList<Integer> getMoves(){
        ArrayList<Integer> possible = new ArrayList<>();
        ArrayList<Piece> pieces = game.getBlackPlayer().getPieces();
        int counter = 0;
        for(Piece piece : pieces){
            if(!piece.getOptions().isEmpty()){
                for (ArrayList<Integer> pos : piece.getOptions()){
                    counter++;
                }
            }
        }
        for(int i = 0; i <= counter; i++){
            possible.add(0);
        }
        return possible;

    }

    public HashMap<String, ArrayList<Integer>> getQ(){
        return q;
    }
}
