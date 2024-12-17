package io.github.angrybirds.pigs;

import com.badlogic.gdx.physics.box2d.World;

public class MediumPig extends Pig {
    private static final int points = 5000;
    public MediumPig(World world,float x, float y) {
        super(world, 3, "Mpig.png");
        super.createPig(x, y, 50, 60); // Set physics size to 30 and sprite size to 70
    }

    public int getPoints() {
        return points;
    }
}
