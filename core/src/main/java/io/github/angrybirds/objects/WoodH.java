package io.github.angrybirds.objects;

import com.badlogic.gdx.physics.box2d.World;

public class WoodH extends Materials {
    private int points = 1000;
    public WoodH(World world, float x, float y) {
        super(world,3, "WoodH.png");
        createBlock(x, y, 175, 20);

    }

    public int getPoints() {
        return points;
    }
}