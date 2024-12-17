package io.github.angrybirds.objects;

import com.badlogic.gdx.physics.box2d.World;

public class StoneH extends Materials {
    private int points = 1000;
    public StoneH(World world, float x, float y) {
        super(world,4, "StoneH.png");
        createBlock(x, y, 175, 20);

    }

    public int getPoints() {
        return points;
    }
}
