package sample.elements;

import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/**
 * Created by bogusz on 12.03.18.
 */
public class Ball {
    //dimension:
    private float xPos;
    private float yPos;
    private float radius;

    //Physics Parameters
    private World world;
    private BodyDef bodyDef;
    private Body body;
    private CircleShape circleShape;

    //Graphics parameters
    Color color = Color.GOLD;

    public Ball(float xPos, float yPos, World world) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.world = world;
        this.radius = 25f;
    }

    private void physicCreate(){
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        //TODO: position define

    }
}
