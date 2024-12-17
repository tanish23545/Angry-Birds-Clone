package io.github.angrybirds.objects;

import com.badlogic.gdx.physics.box2d.World;

public class GlassV extends Materials {
    private int points = 1000;
    public GlassV(World world, float x, float y) {
        super(world, 2,"GlassV.png");
        createBlock(x, y, 20, 200);
    }

    public int getPoints() {
        return points;
    }
}
