package io.github.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BlueBird extends Bird {
    private static final float PPM = 100; // Pixels per meter for scaling

    private final Texture blueBirdTexture;
    private final Sprite blueBirdSprite;

    public BlueBird(World world, float x, float y) {
        super(world, x, y); // Call the abstract class constructor
        getBody().setUserData(this); // Set the current bird instance as UserData



        // Load texture for the red bird
        blueBirdTexture = new Texture("Bluebird.png");
        blueBirdSprite = new Sprite(blueBirdTexture);
        blueBirdSprite.setSize(35, 35); // Set sprite size
        blueBirdSprite.setOrigin(blueBirdSprite.getWidth() / 2, blueBirdSprite.getHeight() / 2);

        Body body = getBody();

        // Remove existing fixtures
        for (Fixture fixture : body.getFixtureList()) {
            body.destroyFixture(fixture);
        }

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(15f/PPM); // Set smaller radius in meters

        // Update the fixture with the smaller shape
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        body.createFixture(fixtureDef);
        circleShape.dispose(); // Dispose the shape after usage

        // Recalculate mass after modifying the shape
        body.resetMassData();
    }

    @Override
    public void render(SpriteBatch batch) {
        // Update sprite position and rotation based on physics body
        Vector2 position = getBody().getPosition();
        blueBirdSprite.setPosition(
                position.x * PPM - blueBirdSprite.getWidth() / 2,
                position.y * PPM - blueBirdSprite.getHeight() / 2
        );
        // Set rotation based on body's angle
        blueBirdSprite.setRotation((float) Math.toDegrees(getBody().getAngle()));

        // Draw the sprite
        blueBirdSprite.draw(batch);
    }


    @Override
    public void dispose() {
        blueBirdTexture.dispose();
    }
}
