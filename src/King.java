import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.omg.PortableInterceptor.INACTIVE;

public class King extends Piece {

    ArrayList<Pane> playground = getPlayer().getGame().getPlayground();
    ArrayList<Path> paths = new ArrayList<>();

    public King(Image img, int direction, Player player) {
        super(img, direction, player);
    }

    //Setter Methods

    public void addPath(Path path){
        paths.add(path);
    }
    public void removePath(Path path){
        paths.remove(path);
    }




    @Override
    void pull() {
        //wip..
    }

    @Override
    boolean jump(ArrayList<Integer> start, Image king) {

        Path path = new Path(start, this);
        boolean movesPossible = true;
        while(movesPossible){
            for(Path p : paths){
                movesPossible = p.movePossible();
            }
        }

        for(Path p : paths){
            getOptions().add(p.getPath());
        }

        return false;
    }


    // Hilfsmethoden, sind untereinander voneinander abhänging


    //überprüft ob das springen in eine Richtung möglich ist
/*
    boolean movePossible(int start, int direction){

        for(int i = 1; i < 10; i++){
            if(!playground.get(start + i * direction).getChildren().isEmpty() && playground.get(start + (i + 1) * 11).getChildren().isEmpty()){
                Piece target = (Piece)playground.get(start + i * direction).getChildren();
                if(target.getPlayer() != getPlayer()){
                    return true;
                }
                else{
                    break;
                }
            }
        }
        return false;
    }


    //Returned einen List mit allen möglichen Zielfeldern

    ArrayList getPossibleFields(int start, int direction){
        ArrayList<Integer> possible = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            int posTarget = playground.indexOf(playground.get(start + (i + 1) * direction));
            if(!playground.get(start + i * direction).getChildren().isEmpty() && posTarget > 0 && posTarget < 99){
                possible.add(playground.indexOf(playground.get(start + (i + 1) * direction)));
            }
        }
        return possible;
    }


    // Für getPossibleFields methode -- gibt ArrayList mit Arraylists zurück, die alle bisherigen Schritte mit einem jeweiligen möglichen Landungsfeld zurück

    ArrayList<ArrayList> generateLists(ArrayList<Integer> steps, ArrayList<Integer> possible){
        ArrayList<ArrayList> all = new ArrayList<>();
        for(int step : possible) {
            ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0; i < steps.size(); i++){
                list.add(steps.get(i));
            }
            list.add(step);
            all.add(list);
        }
        return all;
    }


    // Erstellt für jeden neuen möglichen Zug eine Arraylist mit bisherigen steps

    ArrayList<ArrayList> splitPath(ArrayList<Integer> steps, int possiblePaths){
        ArrayList<ArrayList> possible = new ArrayList<>();
        for(int i = 0; i < possiblePaths; i++){
            ArrayList<Integer> newSteps = new ArrayList<>();
            for(int step : steps){
                newSteps.add(step);
            }
            possible.add(newSteps);
        }
        return possible;
    }


    //Gibt die Werte aller Steine die in einem Zug übersprungen werden

    ArrayList getKilledCheckers(ArrayList<Integer> steps){
        ArrayList<Integer> killed = new ArrayList<>();
        for(int i = 0; i < steps.size() - 1; i++){
            int start = steps.get(i);
            int target = steps.get(i + 1);
            int direction;

            //Findet die Richtung des Sprungs
            if (start > target) {
                if(start % 11 == target % 11){
                    direction = 11;
                }
                else {
                    direction = 9;
                }
            }
            else {
                if(start % 11 == target % 11){
                    direction = -11;
                }
                else
                    direction = -9;
            }

                //Searches for slain Checker and add it to list
                for(int j = 0; j < 10; j++){
                    if(!playground.get(start + i * direction).getChildren().isEmpty()){
                        killed.add(start + i * direction);
                        break;
                    }
                }
            }
        return killed;
        }

*/

}
