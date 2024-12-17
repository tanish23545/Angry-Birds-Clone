package io.github.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class RedBird extends Bird {
    private static final float PPM = 100; // Pixels per meter for scaling

    private Texture redBirdTexture;
    private Sprite redBirdSprite;

    public RedBird(World world, float x, float y) {

        super(world, x, y); // Call the abstract class constructor
        getBody().setUserData(this); // Set the current bird instance as UserData


        // Load texture for the red bird
        redBirdTexture = new Texture("Redbird.png");
        redBirdSprite = new Sprite(redBirdTexture);
        redBirdSprite.setSize(100, 55); // Set sprite size
        redBirdSprite.setOrigin(redBirdSprite.getWidth() / 2, redBirdSprite.getHeight() / 2);

        // Customize body properties for Red Bird (e.g., heavier density)
//        getBody().getFixtureList().first().setDensity(1); // Make Red Bird heavier
//        getBody().resetMassData(); // Recalculate mass

    }

    @Override
    public void render(SpriteBatch batch) {
        // Update sprite position and rotation based on physics body
        Vector2 position = getBody().getPosition();
        redBirdSprite.setPosition(
                position.x * PPM - redBirdSprite.getWidth() / 2,
                position.y * PPM - redBirdSprite.getHeight() / 2
        );
        // Set rotation based on body's angle
        redBirdSprite.setRotation((float) Math.toDegrees(getBody().getAngle()));

        // Draw the sprite
        redBirdSprite.draw(batch);
    }


    @Override
    public void dispose() {
        redBirdTexture.dispose();
    }
}