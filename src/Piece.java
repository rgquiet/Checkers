import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class Piece extends ImageView {

    private final int direction;
    private Player player;
    private ArrayList<ArrayList> options;

    public Piece(Image img, int direction, Player player) {
        super(img);
        this.direction = direction;
        this.player = player;
        options = new ArrayList<>();
    }

    //Abstract-Methods
    abstract void pull();
    abstract boolean jump(ArrayList<Integer> start);

    //Getter-Methods
    public int getDirection() { return direction; }
    public Player getPlayer() { return player; }
    public ArrayList<ArrayList> getOptions () { return options; }

    //Setter-Methods
    public void setOnMouseClick() {
        setOnMouseClicked((MouseEvent e) -> {
            player.getGame().setSelected(this);
            player.getGame().clearStyleH2();

            options.forEach(n -> player.getGame().setStyleH2((int)n.get(n.size()-1)));


        });
    }

    public void removeOnMouseClick() {
        setOnMouseClicked(null);
    }

}
