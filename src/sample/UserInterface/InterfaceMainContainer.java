package sample.UserInterface;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.WorldCreator;
import sample.elementsWithTexture.Ground;
import sample.elements.Block;
import sample.elements.StaticBarier;
import sample.elementsWithTexture.Chest;


/**
 * Created by bogusz on 12.03.18.
 */
public class InterfaceMainContainer {
    //GUI:
    private HBox container;

    //elemts choice field
    private VBox elementsVbox;
    private Slider elementsSelectorSlide;
    private Canvas elemtsCanvans;
    private GraphicsContext elementDrawContex;

    // element reset Button
    private Button resetButton;

    public enum DrawingElements{
        BLOCK,
        GROUND,
        CHEST,
        BLOCK_GROUND_1,
        BLOCK_GROUND_2,
        BLOCK_GROUND_3,
        BLOCK_GROUND_4,
    }
    private static DrawingElements selectedElemnt;      // visible to all
    public InterfaceMainContainer(){
        initContainer();
        //init selector:
        elementsFieldInit();
        resetButtonInit();
    }

    private void initContainer(){
        container = new HBox();
        container.setPrefWidth(WorldCreator.WIDHT);
        container.setPrefHeight(100);
        container.setStyle("-fx-background-color: #c1ffc3;");
        container.setAlignment(Pos.CENTER_LEFT);
    }

    private void elementsFieldInit(){
        //Select element:
        selectedElemnt = DrawingElements.BLOCK;
        elementsVbox = new VBox();
        elementsVbox.setAlignment(Pos.CENTER);
        elementsVbox.setPadding(new Insets(5));
        //slider :
        elementsSelectorSlide = new Slider();
        elementsSelectorSlide.setMax(DrawingElements.values().length);
        elementsSelectorSlide.setMin(1);
        elementsSelectorSlide.setValueChanging(true);
        elementsSelectorSlide.setMajorTickUnit(1);
        elementsSelectorSlide.setMinorTickCount(0);
        elementsSelectorSlide.setSnapToTicks(true);

        // canvans:
        elemtsCanvans = new Canvas();
        elemtsCanvans.setWidth(90);
        elemtsCanvans.setHeight(90);
        elementDrawContex = elemtsCanvans.getGraphicsContext2D();

        elementDrawContex.fillRect(0,0,elemtsCanvans.getWidth(),elemtsCanvans.getHeight());
        drawSampleElements();
        elementsVbox.getChildren().add(elemtsCanvans);
        elementsVbox.getChildren().add(elementsSelectorSlide);

        container.getChildren().add(elementsVbox);

        elementsSelectorSlide.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeElement();
            }
        });
        elementsSelectorSlide.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeElement();
            }
        });
    }

    private void resetButtonInit(){
        resetButton = new Button();
        resetButton.setText("Reset map");
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                WorldCreator.resetWorld();
            }
        });
        container.getChildren().add(resetButton);
    }

    private void drawSampleElements(){
        elementDrawContex.clearRect(0,0,elemtsCanvans.getWidth(),elemtsCanvans.getHeight());
        switch (selectedElemnt){
            case BLOCK:
                Block.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2);
                break;
            case GROUND:
                StaticBarier.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2);
                break;
            case CHEST:
                Chest.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2);
                break;
            case BLOCK_GROUND_1:
                Ground.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2,1);
                break;
            case BLOCK_GROUND_2:
                Ground.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2,2);
                break;
            case BLOCK_GROUND_3:
                Ground.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2,3);
                break;
            case BLOCK_GROUND_4:
                Ground.drawSampleElement(elementDrawContex,elemtsCanvans.getWidth()/2,elemtsCanvans.getHeight()/2,4);
                break;
            default:
                elementDrawContex.setFill(Color.BLACK);
                elementDrawContex.fillRect(elemtsCanvans.getWidth()/2 -10,elemtsCanvans.getHeight()/2 -10,20,20 );
                break;
        }
    }


    private void changeElement(){
        int elementNum = (int)elementsSelectorSlide.getValue();
        switch (elementNum){
            case 1:
                selectedElemnt = DrawingElements.BLOCK;
                break;
            case 2:
                selectedElemnt = DrawingElements.GROUND;
                break;
            case 3:
                selectedElemnt = DrawingElements.CHEST;
                break;
            case 4:
                selectedElemnt = DrawingElements.BLOCK_GROUND_1;
                break;
            case 5:
                selectedElemnt = DrawingElements.BLOCK_GROUND_2;
                break;
            case 6:
                selectedElemnt = DrawingElements.BLOCK_GROUND_3;
                break;
            case 7:
                selectedElemnt = DrawingElements.BLOCK_GROUND_4;
                break;
        }
        drawSampleElements();
    }

    public HBox getContainer() {
        return container;
    }

    public void drawSampleonScreen(GraphicsContext context, double x, double y){
        switch (selectedElemnt){
            case BLOCK:
                Block.drawSampleElement(context,x,y);
                break;
            case GROUND:
                StaticBarier.drawSampleElement(context,x,y);
                break;
            case CHEST:
                Chest.drawSampleElement(context,x,y);
                break;
            case BLOCK_GROUND_1:
                Ground.drawSampleElement(context,x,y,1);
                break;
            case BLOCK_GROUND_2:
                Ground.drawSampleElement(context,x,y,2);
                break;
            case BLOCK_GROUND_3:
                Ground.drawSampleElement(context,x,y,3);
                break;
            case BLOCK_GROUND_4:
                Ground.drawSampleElement(context,x,y,4);
                break;
            default:
                break;
        }
    }

    public static DrawingElements getSelectedElemnt() {
        return selectedElemnt;
    }
}
