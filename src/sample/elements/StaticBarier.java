package sample.elements;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sample.GamePanel;

/**
 * Created by bogusz on 12.03.18.
 */
public class StaticBarier {

    //Dimisions
    private float xPosition;
    private float yPosition;
    private static float width = 200f;
    private static float height = 40f;

    //Physics Parameters
    private World world;
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape shape;
    private FixtureDef fixtureDef;

    //Graphics parameters
    private static Color color = Color.LAWNGREEN;

    // math parameters
    double xPos;
    double yPos;
    double[] polygonY,polygonX;

    public StaticBarier(float xPosition, float yPosition, World world) {
        this.xPosition = xPosition - width/2;
        this.yPosition = yPosition - height/2;
        this.world = world;

        physicCreate();
    }

    private void physicCreate(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
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
        fixtureDef.restitution = 0.1f;
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

    public void deletItself(){
        world.destroyBody(body);
    }
    public static void drawSampleElement(GraphicsContext context,double xCenter,double yCenter){
        context.setFill(color);
        context.fillRect(xCenter-(width/2),yCenter-(height/2), width, height);
    }

}
