package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.elements.Ground;
import sample.elements.Block;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bogusz on 10.03.18.
 */
public class GamePanel {
    public static final float HEIGHT = 640;
    public static final float WIDHT = 800;
    public final static float SCALE_TO_WORLD = 0.005f;
    public final static float SCALE_TO_JAVAFX = 20.0f;

    private Canvas canvas;
    private Group root;
    private GraphicsContext graphicsContext2D;

    //elements:
    private PhysicWorld physicWorld;
    private Ground ground;
    private Block blockTest;
    List<Block> blocks = new ArrayList<>();

    public GamePanel(){
        physicWorld = new PhysicWorld();
        ground = new Ground(0,600,
                WIDHT,40f,physicWorld.getWorld());

        blockTest =new Block(400,100,physicWorld.getWorld());

        canvas = new Canvas(WIDHT,HEIGHT);
        root = new Group();
        graphicsContext2D = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                float x = (float) event.getSceneX();
                float y = (float) event.getSceneY();
                blocks.add(new Block(x,y,physicWorld.getWorld()));
                System.out.println("Mouse click x= " + x + " y = " + y);
                blocks.get(blocks.size() - 1).writePostionConvertedFromWorld();
            }
        });
    }
    public void update(){
        physicWorld.worldUpdate();
    }
    public void draw(String fps){

        graphicsContext2D.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphicsContext2D.setFill(Color.YELLOW);
        graphicsContext2D.setFont(Font.font(28));
        graphicsContext2D.fillText(fps,WIDHT -200, 40);
        ground.updateGraphic(graphicsContext2D);
        blockTest.updateGraphic(graphicsContext2D);
        for(int i=0; i< blocks.size();i++)
        {
            blocks.get(i).updateGraphic(graphicsContext2D);
        }
        blockTest.updateGraphic(graphicsContext2D);

    }

    public Scene getScene()
    {
        return new Scene(root);
    }

}
