package io.github.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    private static final float PPM = 100; // Pixels per meter for scaling

    private Body body;

    public Bird(World world, float x, float y) {
        // Create the physics body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(30f/PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.6f; // Friction to interact with ground

        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    // Abstract method for rendering
    public abstract void render(SpriteBatch batch);

    // Getter for the physics body
    public Body getBody() {
        return body;
    }

    public float calculateDamage() {
        // Get the speed of the bird (magnitude of velocity)
        float speed = getBody().getLinearVelocity().len();

        // Damage could be proportional to speed, for example:
        float damage = speed * 10;  // Adjust the multiplier for appropriate damage scaling

        return damage;
    }

    // Dispose method for subclasses to override if needed
    public void dispose() {
        // Subclasses can override if they need to dispose textures
    }
}
