import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Checker extends ImageView {

    private int pullLeft, pullRight;

    public Checker(Image img) {
        super(img);
        pullLeft = -1;
        pullRight = -1;

        //wip...
        setOnMouseClicked((MouseEvent e) -> {
            System.out.println("left: " + pullLeft + " & right: " + pullRight);
        });
    }

    //Setter-Methods
    public void setPullLeft(int left) { pullLeft = left; }
    public void setPullRight(int right) { pullRight = right; }

}
