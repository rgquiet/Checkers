import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Path {

    ArrayList<Integer> path = new ArrayList<>();
    King king;
    ArrayList<Pane> playground;

    public Path(ArrayList<Integer> steps, King king){
        path.addAll(steps);
        this.king = king;
        playground = king.getPlayer().getGame().getPlayground();
    }

    public Path(ArrayList<Integer> steps, int target, King king){
        path.addAll(steps);
        path.add(target);
        this.king = king;
        playground = king.getPlayer().getGame().getPlayground();
    }


    // Getter Methods

    public ArrayList<Integer> getPath() {
        return path;
    }
    public King getKing() {
        return king;
    }


    boolean jumpPossible(){

        int[] directions = {-9, 11, 9, -11};
        int start = path.get(path.size() - 1);
        boolean possible = false;
        for(int d : directions) {
            for (int i = 1; start + (i + 1) * d <= 99 && start + (i + 1) * d >= 0; i++) {

                //Überprüft ob die Richtung mit dem Startpunkt kompatibel ist
                if((start % 10 == 9 && (d == 11 || d == -9) || (start % 10 == 0 && (d == -11 || d == 9)))){
                    break;
                }

                if (!playground.get(start + i * d).getChildren().isEmpty() && //Ist ein potenzielles Ziel in der Reihe vorhanden
                        playground.get(start + (i + 1) * d).getChildren().isEmpty()) //Ist das Feld hinter dem Ziel frei?
                    {

                        Piece target = (Piece) playground.get(start + i * d).getChildren().get(0);
                        if (target.getPlayer() != king.getPlayer() &&
                            (start + i * d) % 10 != 0 && //Und Ziel nicht am linken Rand
                            (start + i * d) % 10 != 9 && //Und Ziel nicht am rechten Rand
                            getKilledCheckers().indexOf(start + i * d) == -1) //Ist das Ziel bereits übersprungen worden?
                        {

                            // Neuen Path erstellen
                            ArrayList<Integer> pos = getPossibleFields(start, d);
                            for (int p : pos) {
                                Path newPath = new Path(path, p, getKing());
                                king.addPath(newPath);
                            }
                            possible = true;
                        } else {
                            break;
                        }
                    }
            }
        }
        if(possible){
            king.removePath(this);
        }
        return possible;
    }

    //Returned einen List mit allen möglichen Zielfeldern

    private ArrayList<Integer> getPossibleFields(int start, int direction){
        ArrayList<Integer> possible = new ArrayList<>();
        int target = -1;

        // Sets target Checker

        for (int i = 1; start + i  * direction <= 99 && start + i * direction >= 0 ; i++) {
            if (!playground.get(start + i * direction).getChildren().isEmpty()) {
                target = start + i * direction;
                break;
            }
        }

        //Generates a list with every possible Field
        if(target != -1) {

            for (int i = 1; target + i  * direction <= 99 && target + i * direction >= 0; i++) {

                if(!playground.get(target + i * direction).getChildren().isEmpty()){
                    break;
                }

                if (playground.get(target + i * direction).getChildren().isEmpty() && getKilledCheckers().indexOf(start + i * direction) == -1) {
                    possible.add(target + i * direction);
                }

                if((target + i * direction) % 10 == 9 || (target + i * direction) % 10 == 0 ){
                    break;
                }
            }
        }
        return possible;
    }

    private ArrayList getKilledCheckers(){
        ArrayList<Integer> killed = new ArrayList<>();

        if(path.size() > 1) {
            for (int i = 0; i < path.size() - 1; i++) {
                int start = path.get(i);
                int target = path.get(i + 1);
                int direction = getKing().getPlayer().getGame().getDirection(start, target);

                //Searches for slain Checker and add it to list
                for (int j = 1; start + j * direction <= 99 && start + j * direction >= 0; j++) {
                    if (!playground.get(start + j * direction).getChildren().isEmpty()) {
                        killed.add(start + j * direction);
                        break;
                    }
                }
            }
        }
        return killed;
    }

}
