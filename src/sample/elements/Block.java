package sample.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sample.GamePanel;
import sample.Main;

import java.awt.*;

import static sample.GamePanel.*;

/**
 * Created by bogusz on 10.03.18.
 */
public class Block {

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
    private FixtureDef fixtureDef;

    //Graphics parameters
    Color color = Color.RED;

    public Block(float xPosition, float yPosition,
                 float width, float height, World world) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height =height;
        this.world = world;

        physicCreate();

    }

    private void physicCreate(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set((xPosition + width/2) * GamePanel.SCALE_TO_WORLD,
                            (yPosition + height/2) * GamePanel.SCALE_TO_WORLD);

        body = world.createBody(bodyDef);

        shape = new PolygonShape();

        Vec2 B1 = new Vec2(xPosition * GamePanel.SCALE_TO_WORLD,
                yPosition* GamePanel.SCALE_TO_WORLD);
        Vec2 B2 = new Vec2((xPosition +width)* GamePanel.SCALE_TO_WORLD,
                yPosition* GamePanel.SCALE_TO_WORLD);
        Vec2 B3 = new Vec2((xPosition +width)* GamePanel.SCALE_TO_WORLD,
                (yPosition + height)* GamePanel.SCALE_TO_WORLD);
        Vec2 B4 = new Vec2((xPosition)* GamePanel.SCALE_TO_WORLD,
                (yPosition + height)* GamePanel.SCALE_TO_WORLD);

        Vec2[] vec2 ={B1,B2,B3,B4};

        shape.set(vec2,4);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef);

    }

    public void updateGraphic(GraphicsContext graphicsContext){
        double xPos = body.getPosition().x;
        double yPos = body.getPosition().y;
        double[] polygonX ={(xPos +shape.getVertex(0).x)/GamePanel.SCALE_TO_WORLD
        ,(xPos + shape.getVertex(1).x)/GamePanel.SCALE_TO_WORLD
        ,(xPos +shape.getVertex(2).x)/GamePanel.SCALE_TO_WORLD
        ,(xPos +shape.getVertex(3).x)/GamePanel.SCALE_TO_WORLD};

        double[] polygonY ={(yPos + shape.getVertex(0).y)/GamePanel.SCALE_TO_WORLD
                ,(yPos +shape.getVertex(1).y)/GamePanel.SCALE_TO_WORLD
                ,(yPos +shape.getVertex(2).y)/GamePanel.SCALE_TO_WORLD
                ,(yPos +shape.getVertex(3).y)/GamePanel.SCALE_TO_WORLD};

        graphicsContext.setFill(color);
        graphicsContext.fillPolygon(polygonX,polygonY,4);

    }

    public void writePosition(){
        System.out.println("Rotate = " + body.getAngle());
    }
}
