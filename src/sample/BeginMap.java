package sample;

import javafx.scene.canvas.GraphicsContext;
import org.jbox2d.dynamics.World;
import sample.elementsWithTexture.Ground;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bogusz on 16.03.18.
 */
public class BeginMap {
    //dimision:
    private int widthBlock =20;
    private int heigthBlock = 5;
    //world:
    private World world;


    // Składowe:
    List<Ground> grounds = new ArrayList<>();

    public BeginMap(int xBegin, int yBegin, World world) {

        this.world = world;
        sampleTerrain1(xBegin,yBegin);

    }

    private void sampleTerrain1(int  xPos, int  yPos){

        int x = xPos;
        int y = yPos;
        grounds.add(new Ground(x,y,world,0));
        x += Ground.getWidth();
        for(int i=0;i<widthBlock -2;i++){
            grounds.add(new Ground(x,y,world,1));
            x += Ground.getWidth();
        }
        grounds.add(new Ground(x,y,world,2));

        for(int i=0; i<heigthBlock-1;i++){
            y += Ground.getHeight();
            x =xPos;
            for (int j=0;j<widthBlock;j++){
                grounds.add(new Ground(x,y,world,3));
                x += Ground.getWidth();
            }
        }
    }

    public void DrawAll(GraphicsContext context){
        for(int i=0;i<grounds.size();i++){
            grounds.get(i).updateGraphic(context);
        }
    }
}
