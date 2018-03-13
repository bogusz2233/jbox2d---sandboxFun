package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.UserInterface.InterfaceMainContainer;
import sample.elements.ElementBase;
import sample.elements.Ground;
import sample.elements.Block;
import sample.elements.StaticBarier;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bogusz on 10.03.18.
 */
public class GamePanel {
    private static boolean resetWorldFlag = false;
    public static final float HEIGHT = 700;
    public static final float WIDHT = 1024;
    public final static float SCALE_TO_JAVAFX = 20.0f;


    // contener
    private Group root;
    //place to rendering
    private Canvas canvas;
    private GraphicsContext graphicsContext2D;
    private static double xCameraPostion=0;
    private static double yCameraPostion=0;

    //GUI
    private InterfaceMainContainer userInter;

    //elements:
    private PhysicWorld physicWorld;
    private Ground ground;
    private Block blockTest;
    List<ElementBase> elementsOnBoard = new ArrayList<>();

    // Mouse position:
    private double xMousePos;
    private double yMousePos;

    private double xMouseStartMove;
    private double yMouseStartMove;

    private AnimationTimer swipeCameraAnimation;

    public GamePanel(){
        physicWorld = new PhysicWorld();
        ground = new Ground(-100,HEIGHT -40,
                WIDHT + 200,40f,physicWorld.getWorld());

        blockTest =new Block(400,100,physicWorld.getWorld());

        canvas = new Canvas(WIDHT,HEIGHT);
        root = new Group();
        graphicsContext2D = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        userInter = new InterfaceMainContainer();
        root.getChildren().add(userInter.getContainer());

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY) {

                    float x = (float) (event.getSceneX() - xCameraPostion);
                    float y = (float) (event.getSceneY() - yCameraPostion);
                    switch (InterfaceMainContainer.getSelectedElemnt()) {
                        case BLOCK:
                            elementsOnBoard.add(new Block(x, y, physicWorld.getWorld()));
                            break;
                        case GROUND:
                            elementsOnBoard.add(new StaticBarier(x, y, physicWorld.getWorld()));

                    }
                }
            }
        });

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xMousePos = event.getX();
                yMousePos = event.getY();
            }
        });



        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.MIDDLE) {
                    xMouseStartMove = event.getX();
                    yMouseStartMove = event.getY();
                }
            }
        });
        canvas.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.MIDDLE) {
                    xCameraPostion -= xMouseStartMove - event.getX();
                    yCameraPostion -= yMouseStartMove - event.getY();

                    xMouseStartMove =event.getX();
                    yMouseStartMove =event.getY();


                    double xBlock = 1500;
                    double yBlock = 200;
                    //zablokowanie przewijania:
                    if(xCameraPostion > xBlock) xCameraPostion =xBlock;
                    else if(xCameraPostion < - xBlock) xCameraPostion = -xBlock;

                    if(yCameraPostion >  yBlock) yCameraPostion = yBlock;
                    else if(yCameraPostion <  -yBlock) yCameraPostion = -yBlock;
                }
            }
        });

    }
    public void update(){
        physicWorld.worldUpdate();
        if(resetWorldFlag){
            for(int i=0; i< elementsOnBoard.size();i++)
            {
                elementsOnBoard.get(i).deleteItself();
            }

            elementsOnBoard.clear();

            resetWorldFlag = false;
        }
    }
    public void draw(String fps){

        graphicsContext2D.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphicsContext2D.setFill(Color.YELLOW);
        graphicsContext2D.setFont(Font.font(28));
        graphicsContext2D.fillText(fps,WIDHT -200, 150);
        ground.updateGraphic(graphicsContext2D);
        blockTest.updateGraphic(graphicsContext2D);
        for(int i=0; i< elementsOnBoard.size();i++)
        {
            elementsOnBoard.get(i).updateGraphic(graphicsContext2D);
        }
        blockTest.updateGraphic(graphicsContext2D);
        userInter.drawSampleonScreen(graphicsContext2D,xMousePos,yMousePos);

    }

    public static void resetWorld() {
        GamePanel.resetWorldFlag = true;
    }

    public Scene getScene()
    {
        return new Scene(root);
    }

    public static double getxCameraPostion() {
        return xCameraPostion;
    }

    public static double getyCameraPostion() {
        return yCameraPostion;
    }

}
