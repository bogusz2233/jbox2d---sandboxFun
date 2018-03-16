package sample.elements;

import javafx.scene.canvas.GraphicsContext;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import sample.WorldCreator;

/**
 * Created by bogusz on 12.03.18.
 */
public abstract class ElementBase {

    //Dimisions
    protected float xPosition;
    protected float yPosition;

    //Physics Parameters
    protected World world;
    protected BodyDef bodyDef;
    protected Body body;
    protected PolygonShape shape;
    protected FixtureDef fixtureDef;

    // math parameters
    protected double xPos;
    protected  double yPos;
    protected double[] polygonY,polygonX;

    protected abstract void physicCreate();
    public abstract void updateGraphic(GraphicsContext graphicsContext);

    public void deleteItself(){
        world.destroyBody(body);
    }

    public void updateElementsPosition(){

    }
    protected  void rotate(int i){
        double dx = polygonX[i];
        double dy = polygonY[i];
        double xPrim;
        double yPrim;
        double radian = body.getAngle();

        xPrim = dx * Math.cos(radian) - dy * Math.sin(radian);
        yPrim = dx * Math.sin(radian) + dy * Math.cos(radian);

        polygonX[i] = (xPrim +xPos) * WorldCreator.SCALE_TO_JAVAFX + WorldCreator.getxCameraPostion();
        polygonY[i] = (yPrim +yPos) * WorldCreator.SCALE_TO_JAVAFX + WorldCreator.getyCameraPostion();
    }

    protected void rotate() {
        for (int i = 0; i < polygonX.length; i++) {
            rotate(i);
        }
    }
}
