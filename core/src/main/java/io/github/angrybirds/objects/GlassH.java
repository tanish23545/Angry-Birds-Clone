package io.github.angrybirds.objects;

import com.badlogic.gdx.physics.box2d.World;

public class GlassH extends Materials {
    private int points = 1000;
    public GlassH(World world, float x, float y) {
        super(world,2, "GlassH.png");
        createBlock(x, y, 175, 20);

    }

    public int getPoints() {
        return points;
    }
}
