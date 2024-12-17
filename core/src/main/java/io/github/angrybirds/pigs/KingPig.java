package io.github.angrybirds.pigs;

import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig {
    private int points = 10000;
    public KingPig(World world,float x, float y) {
        super(world, 3, "Kpig.png");
        super.createPig(x, y, 70,75); // Set size for KingPig

    }

    public int getPoints() {
        return points;
    }
}
