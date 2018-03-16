package sample.elementsWithTexture;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import sample.WorldCreator;
import sample.elements.ElementBase;

/**
 * Created by bogusz on 16.03.18.
 */
public class Ground extends ElementBase {


    private int numBlock;
    //Dimisions:
    protected static float width = 70f;
    protected static float height = 70f;

    private static Image[] textureImage =new Image[]{
            new Image("img/ground/1.png",width,height,true,false),
            new Image("img/ground/2.png",width,height,true,false),
            new Image("img/ground/3.png",width,height,true,false),
            new Image("img/ground/4.png",width,height,true,false)
    };

    public Ground(float xPosition, float yPosition, World world,int numBlock) {
        this.xPosition = xPosition - width/2;
        this.yPosition = yPosition - height/2;
        this.world = world;

        this.numBlock = numBlock;

        physicCreate();
    }

    @Override
    protected void physicCreate() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set((xPosition + width/2) / WorldCreator.SCALE_TO_JAVAFX,
                (yPosition + height/2) / WorldCreator.SCALE_TO_JAVAFX);

        body = world.createBody(bodyDef);

        shape = new PolygonShape();

        Vec2 B1 = new Vec2( ((float)-width / 2)/ WorldCreator.SCALE_TO_JAVAFX,
                -height/2/ WorldCreator.SCALE_TO_JAVAFX);
        Vec2 B2 = new Vec2((width/2)/ WorldCreator.SCALE_TO_JAVAFX,
                -height/2/ WorldCreator.SCALE_TO_JAVAFX);
        Vec2 B3 = new Vec2((width/2)/ WorldCreator.SCALE_TO_JAVAFX,
                height/2/ WorldCreator.SCALE_TO_JAVAFX);
        Vec2 B4 = new Vec2((-width/2)/ WorldCreator.SCALE_TO_JAVAFX,
                (height/2)/ WorldCreator.SCALE_TO_JAVAFX);

        Vec2[] vec2 ={B1,B2,B3,B4};

        shape.set(vec2,4);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.4f;
        body.createFixture(fixtureDef);
    }

    @Override
    public void updateGraphic(GraphicsContext graphicsContext){


        xPos = body.getPosition().x * WorldCreator.SCALE_TO_JAVAFX;
        yPos = body.getPosition().y * WorldCreator.SCALE_TO_JAVAFX;

        xPos += WorldCreator.getxCameraPostion();
        yPos += WorldCreator.getyCameraPostion();
        graphicsContext.save();
        graphicsContext.translate(xPos,yPos);

        graphicsContext.rotate( Math.toDegrees(body.getAngle()));
        graphicsContext.drawImage(textureImage[numBlock],
                -width/2,-height/2,width,height);
        graphicsContext.restore();
    }

    public static void drawSampleElement(GraphicsContext context,double xCenter,double yCenter,int numBlock){
        Image sampleImage = new Image("img/ground/"+numBlock+".png",width,height,true,false);
        context.drawImage(sampleImage, xCenter-(width/2),yCenter-(height/2));
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
