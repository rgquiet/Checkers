import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Path {

    ArrayList<Integer> path = new ArrayList<>();
    King king;
    ArrayList<Pane> playground = king.getPlayer().getGame().getPlayground();

    public Path(ArrayList<Integer> steps, King king){
        path.addAll(steps);
        this.king = king;
    }

    public Path(ArrayList<Integer> steps, int target, King king){
        path.addAll(steps);
        path.add(target);
        this.king = king;
    }


    // Getter Methods

    public ArrayList<Integer> getPath() {
        return path;
    }
    public King getKing() {
        return king;
    }


    ArrayList getKilledCheckers(){
        ArrayList<Integer> killed = new ArrayList<>();

        for(int i = 0; i < path.size() - 1; i++){
            int start = path.get(i);
            int target = path.get(i + 1);
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

    boolean movePossible(){

        int directions[] = {-9, 11, 9, -11};
        int start = path.get(path.size() - 1);

        for(int d : directions) {
            for (int i = 1; i < 10; i++) {
                if (!playground.get(start + i * d).getChildren().isEmpty() && //Ist ein potenzielles Ziel in der Reihe vorhanden
                        playground.get(start + (i + 1) * 11).getChildren().isEmpty() && //Ist das Feld hinter dem Ziel frei?
                        getKilledCheckers().indexOf(start + i * d) == -1) { //Ist das Ziel bereits übersprungen worden?

                    Piece target = (Piece) playground.get(start + i * d).getChildren();
                    if (target.getPlayer() != king.getPlayer()) {
                        // Neuen Path erstellen
                        ArrayList<Integer> pos = getPossibleFields(start, d);
                        for (int p : pos) {
                            Path newPath = new Path(path, p, getKing());
                            king.addPath(newPath);
                        }
                        return true;
                    } else {
                        break;
                    }
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

}
