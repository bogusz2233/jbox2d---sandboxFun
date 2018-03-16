package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.UserInterface.InterfaceMainContainer;
import sample.elements.ElementBase;
import sample.elements.Block;
import sample.elements.StaticBarier;
import sample.elementsWithTexture.Chest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bogusz on 10.03.18.
 */
public class WorldCreator {
    private static boolean resetWorldFlag = false;

    public static final float HEIGHT = 700;
    public static final float WIDHT = 1024;
    public final static float SCALE_TO_JAVAFX = 20.0f;


    // contener
    private Group root;
    //place to rendering
    private Canvas canvas;
    private GraphicsContext graphicsContext2D;
    private static int xCameraPostion=0;
    private static int yCameraPostion=0;

    //GUI
    private InterfaceMainContainer userInter;

    //elements:
    private PhysicWorld physicWorld;
    List<ElementBase> elementsOnBoard = new ArrayList<>();

    // Mouse position:
    private int xMousePos;
    private int yMousePos;

    private int xMouseStartMove;
    private int yMouseStartMove;

    private AnimationTimer swipeCameraAnimation;

    //world size:
    private int xBlock = 1500;
    private int yBlock = 200;
    private Image backGround;

    private BeginMap beginMap;

    public WorldCreator(){
        physicWorld = new PhysicWorld();

        canvas = new Canvas(WIDHT,HEIGHT);
        root = new Group();
        graphicsContext2D = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        userInter = new InterfaceMainContainer();
        root.getChildren().add(userInter.getContainer());
        // platform init:
        beginMap = new BeginMap(-200,600,physicWorld.getWorld());

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY) {

                    int x = (int) event.getSceneX();
                    int y = (int) event.getSceneY();

                    x -= (x%10)+ xCameraPostion;
                    y -= (y%10) + yCameraPostion;

                    switch (InterfaceMainContainer.getSelectedElemnt()) {
                        case BLOCK:
                            elementsOnBoard.add(new Block(x, y, physicWorld.getWorld()));
                            break;
                        case GROUND:
                            elementsOnBoard.add(new StaticBarier(x, y, physicWorld.getWorld()));
                            break;
                        case CHEST:
                            elementsOnBoard.add(new Chest(x,y,physicWorld.getWorld()));
                            break;
                        case BLOCK_GROUND_1:
                            elementsOnBoard.add(new sample.elementsWithTexture.Ground(x,y,physicWorld.getWorld(),0));
                            break;
                        case BLOCK_GROUND_2:
                            elementsOnBoard.add(new sample.elementsWithTexture.Ground(x,y,physicWorld.getWorld(),1));
                            break;
                        case BLOCK_GROUND_3:
                            elementsOnBoard.add(new sample.elementsWithTexture.Ground(x,y,physicWorld.getWorld(),2));
                            break;
                        case BLOCK_GROUND_4:
                            elementsOnBoard.add(new sample.elementsWithTexture.Ground(x,y,physicWorld.getWorld(),3));
                            break;
                   }
                }
                else if(event.getButton() == MouseButton.SECONDARY){
                }
            }
        });

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) event.getSceneX();
                int y = (int) event.getSceneY();

                x -= (x%10);
                y -= (y%10);

                xMousePos = x;
                yMousePos = y;
            }
        });



        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.MIDDLE) {
                    xMouseStartMove = (int)event.getX();
                    yMouseStartMove = (int)event.getY();
                }
            }
        });
        canvas.addEventFilter(MouseDragEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.MIDDLE) {
                    xCameraPostion -= xMouseStartMove - event.getX();
                    yCameraPostion -= yMouseStartMove - event.getY();

                    xCameraPostion -=(xCameraPostion%10);
                    yCameraPostion -=(yCameraPostion%10);

                    xMouseStartMove =(int)event.getX();
                    yMouseStartMove =(int)event.getY();



                    //zablokowanie przewijania:
                    if(xCameraPostion > xBlock) xCameraPostion =xBlock;
                    else if(xCameraPostion < - xBlock) xCameraPostion = -xBlock;

                    if(yCameraPostion >  yBlock) yCameraPostion = yBlock;
                    else if(yCameraPostion <  -yBlock) yCameraPostion = -yBlock;
                }
            }
        });

        backGround = new Image("img/bg_jungle.png", WIDHT +xBlock,HEIGHT + yBlock,false,false);
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
    public void draw(String fps ,String memoryUsage){

        graphicsContext2D.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphicsContext2D.drawImage(backGround,+xCameraPostion-xBlock,+yCameraPostion-yBlock,WIDHT+2*xBlock,HEIGHT+2*yBlock);
        beginMap.DrawAll(graphicsContext2D);
        for(int i=0; i< elementsOnBoard.size();i++)
        {
            elementsOnBoard.get(i).updateGraphic(graphicsContext2D);
        }
        userInter.drawSampleonScreen(graphicsContext2D,xMousePos,yMousePos);
        graphicsContext2D.setFill(Color.DARKRED);
        graphicsContext2D.setFont(Font.font(20));
        graphicsContext2D.fillText(fps,WIDHT -350, 140);
        graphicsContext2D.fillText("ELEMENTS: " +elementsOnBoard.size(),WIDHT -350, 162);
        graphicsContext2D.fillText(memoryUsage,WIDHT -350, 184);

    }

    public static void resetWorld() {
        WorldCreator.resetWorldFlag = true;
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
