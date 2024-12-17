package io.github.angrybirds.objects;

import com.badlogic.gdx.physics.box2d.World;

public class StoneV extends Materials {
    private int points = 1000;
    public StoneV(World world, float x, float y) {
        super(world, 4,"StoneV.png");
        createBlock(x, y, 20, 200);
    }

    public int getPoints() {
        return points;
    }
}