package sample.elementsWithTexture;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
/**
 * Created by bogusz on 13.03.18.
 */
public class TextureReader {

    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private double radius;

    private Image image;
    private PixelReader reader;

    public TextureReader(double xPos, double yPos, double width, double height, Image image) {

        reader = image.getPixelReader();
    }

}
