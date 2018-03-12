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

    // math parameters
    double xPos;
    double yPos;
    double[] polygonY,polygonX;

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
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.world = world;

        width = 40f;
        height =40f;
        physicCreate();
    }

    private void physicCreate(){
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
        body.createFixture(fixtureDef);
    }

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

        rotate(0);
        rotate(1);
        rotate(2);
        rotate(3);
        graphicsContext.setFill(color);
        graphicsContext.fillPolygon(polygonX,polygonY,4);

    }

    private void rotate(int i){
        double dx = polygonX[i];
        double dy = polygonY[i];
        double xPrim;
        double yPrim;
        double radian = body.getAngle();

        xPrim = dx * Math.cos(radian) - dy * Math.sin(radian);
        yPrim = dx * Math.sin(radian) + dy * Math.cos(radian);

        polygonX[i] = (xPrim +xPos) * GamePanel.SCALE_TO_JAVAFX;
        polygonY[i] = (yPrim +yPos) * GamePanel.SCALE_TO_JAVAFX;
    }

    public void writePosition(){
        System.out.println("Rotate = " + body.getAngle());
    }
    public void writePostionConvertedFromWorld(){
        double x = body.getPosition().x / GamePanel.SCALE_TO_WORLD;
        double y = body.getPosition().y / GamePanel.SCALE_TO_WORLD;
        System.out.println("Pozycja w świeci x= " + y + " y= " + y );
    }
}
