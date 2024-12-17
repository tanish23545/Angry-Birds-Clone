package io.github.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BlackBird extends Bird {
    private static final float PPM = 100; // Pixels per meter for scaling

    private Texture blackBirdTexture;
    private Sprite blackBirdSprite;

    public BlackBird(World world, float x, float y) {

        super(world, x, y); // Call the abstract class constructor
        getBody().setUserData(this); // Set the current bird instance as UserData


        // Load texture for the red bird
        blackBirdTexture = new Texture("BlackBird.png");
        blackBirdSprite = new Sprite(blackBirdTexture);
        blackBirdSprite.setSize(70, 70); // Set sprite size
        blackBirdSprite.setOrigin(blackBirdSprite.getWidth() / 2, blackBirdSprite.getHeight() / 2);

        // Customize body properties for Red Bird (e.g., heavier density)
//        getBody().getFixtureList().first().setDensity(1); // Make Red Bird heavier
//        getBody().resetMassData(); // Recalculate mass

    }

    @Override
    public void render(SpriteBatch batch) {
        // Update sprite position and rotation based on physics body
        Vector2 position = getBody().getPosition();
        blackBirdSprite.setPosition(
                position.x * PPM - blackBirdSprite.getWidth() / 2,
                position.y * PPM - blackBirdSprite.getHeight() / 2
        );
        // Set rotation based on body's angle
        blackBirdSprite.setRotation((float) Math.toDegrees(getBody().getAngle()));

        // Draw the sprite
        blackBirdSprite.draw(batch);
    }


    @Override
    public void dispose() {
        blackBirdTexture.dispose();
    }
}