package io.github.angrybirds.pigs;

import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig {
    private static final int points = 3000;

    public SmallPig(World world,float x, float y) {
        super(world, 1, "Spig.png");
        super.createPig(x, y, 50,50); // Set size for SmallPig

    }

    public int getPoints() {
        return points;
    }
}