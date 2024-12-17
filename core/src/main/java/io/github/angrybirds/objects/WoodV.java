package io.github.angrybirds.objects;
import com.badlogic.gdx.physics.box2d.World;

public class WoodV extends Materials {
    private int points = 1000;
    public WoodV(World world, float x, float y) {
        super(world, 3,"wood.png");
        createBlock(x, y, 20, 200);
    }

    public int getPoints() {
        return points;
    }
}
