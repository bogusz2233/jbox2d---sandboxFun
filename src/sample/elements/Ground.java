package sample.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import sample.GamePanel;
import sample.Main;

import static sample.GamePanel.*;


/**
 * Created by bogusz on 10.03.18.
 */
public class Ground {
    //Dimisions
    private float xPosition;
    private float yPosition;
    private float width;
    private float height;

    //Physics Parameters
    private World world;
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape shape;

    //Graphics parameters
    Color color = Color.GREEN;

    public Ground(float xPosition, float yPosition, float width, float height, World world) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.world = world;

        physicCreate();
    }


    public void updateGraphic(GraphicsContext graphicsContext){
        xPosition = body.getPosition().x * GamePanel.SCALE_TO_JAVAFX - width/2f;
        yPosition = body.getPosition().y *GamePanel.SCALE_TO_JAVAFX -height/2f;
        graphicsContext.setFill(color);
        graphicsContext.fillRect(xPosition, yPosition, width,height);
    }

    private void physicCreate(){
        bodyDef = new BodyDef();
        bodyDef.position.set((xPosition + width/2)/ GamePanel.SCALE_TO_JAVAFX ,
                             (yPosition + height/2)/ GamePanel.SCALE_TO_JAVAFX);

        body = world.createBody(bodyDef);

        shape = new PolygonShape();
        shape.setAsBox(width/2 /GamePanel.SCALE_TO_JAVAFX ,height/2 /GamePanel.SCALE_TO_JAVAFX );

        body.createFixture(shape,0);
    }
}
