package sample;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Created by bogusz on 10.03.18.
 */
public class PhysicWorld {

    private Vec2 gravity;
    private World world;
    public PhysicWorld() {
        gravity = new Vec2(1,40);
        world = new World(gravity, false);
    }

    public void worldUpdate(){
        world.step(1f/60f,6,3);
    }
    public World getWorld() {
        return world;
    }
}
