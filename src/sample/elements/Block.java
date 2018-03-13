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
public class Block extends ElementBase {

    //Dimisions:
    private static float width = 40f;
    private static float height = 40f;

    //Graphics parameters
    private static Color color = Color.RED;


    public Block(float xPosition, float yPosition,
                 float width, float height, World world) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height =height;
        this.world = world;
        physicCreate();
    }

    public Block (float xPosition, float yPosition,World world){
        this.xPosition = xPosition - width/2;
        this.yPosition = yPosition - height/2;
        this.world = world;
        physicCreate();
    }

    @Override
    protected  void physicCreate(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set((xPosition + width/2) / GamePanel.SCALE_TO_JAVAFX,
                            (yPosition + height/2) / GamePanel.SCALE_TO_JAVAFX);

        body = world.createBody(bodyDef);

        shape = new PolygonShape();

        Vec2 B1 = new Vec2( ((float)-width / 2)/ GamePanel.SCALE_TO_JAVAFX,
                -height/2/ GamePanel.SCALE_TO_JAVAFX);
        Vec2 B2 = new Vec2((width/2)/ GamePanel.SCALE_TO_JAVAFX,
                -height/2/ GamePanel.SCALE_TO_JAVAFX);
        Vec2 B3 = new Vec2((width/2)/ GamePanel.SCALE_TO_JAVAFX,
                height/2/ GamePanel.SCALE_TO_JAVAFX);
        Vec2 B4 = new Vec2((-width/2)/ GamePanel.SCALE_TO_JAVAFX,
                (height/2)/ GamePanel.SCALE_TO_JAVAFX);

        Vec2[] vec2 ={B1,B2,B3,B4};

        shape.set(vec2,4);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);
    }

    @Override
    public void updateGraphic(GraphicsContext graphicsContext){
        xPos = body.getPosition().x;
        yPos = body.getPosition().y;
        polygonX =new double[]{(shape.getVertex(0).x)
        ,( shape.getVertex(1).x)
        ,( shape.getVertex(2).x)
        ,( shape.getVertex(3).x)};

        polygonY = new double[]{(shape.getVertex(0).y)
                ,(shape.getVertex(1).y)
                ,(shape.getVertex(2).y)
                ,(shape.getVertex(3).y)};

        rotate();
        graphicsContext.setFill(color);
        graphicsContext.fillPolygon(polygonX,polygonY,4);

    }

    public void writePostionConvertedFromWorld(){
        double x = body.getPosition().x * GamePanel.SCALE_TO_JAVAFX;
        double y = body.getPosition().y * GamePanel.SCALE_TO_JAVAFX;
        System.out.println("Pozycja w świeci x= " + y + " y= " + y );
    }


    public static void drawSampleElement(GraphicsContext context,double xCenter,double yCenter){
        context.setFill(color);
        context.fillRect(xCenter-(width/2),yCenter-(height/2), width, height);
    }
}
