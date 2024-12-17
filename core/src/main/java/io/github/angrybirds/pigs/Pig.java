
package io.github.angrybirds.pigs;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public abstract class Pig {
    private static final float PPM = 100; // Pixels per meter for scaling
    protected World world;
    public Array<Body> pigBodies = new Array<>();
    protected Array<Sprite> pigSprites = new Array<>();
    public int health;
    private Texture pigTexture;
    protected int points;
    protected Body pigBody;

    public Pig(World world, int health, String texturePath) {
        this.world = world;
        this.health = health;
        this.pigTexture = new Texture(texturePath);
    }

    public int getPoints() {
        return points;
    }

    public void createPig(float x, float y, float size, float spriteSize) {
        // Create physics body
        BodyDef pigDef = new BodyDef();
        pigDef.type = BodyDef.BodyType.DynamicBody;
        pigDef.position.set(x / PPM, y / PPM);
        pigBody = world.createBody(pigDef);

        CircleShape pigShape = new CircleShape();
        pigShape.setRadius(size / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = pigShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.8f;

        pigBody.createFixture(fixtureDef);
        pigShape.dispose();

        pigBody.setUserData(this);


        // Create corresponding sprite
        Sprite pigSprite = new Sprite(pigTexture);
        pigSprite.setSize(spriteSize, spriteSize);
        pigSprite.setOrigin(pigSprite.getWidth() / 2, pigSprite.getHeight() / 2);

        pigBodies.add(pigBody);
        pigSprites.add(pigSprite);
    }

    public void render(Batch batch) {
        // If there are no bodies, skip rendering
        if (pigBodies.isEmpty()) {
            return;
        }

        for (int i = 0; i < pigBodies.size; i++) {
            Body body = pigBodies.get(i);
            Sprite sprite = pigSprites.get(i);

            // Update sprite position and rotation
            Vector2 position = body.getPosition();
            sprite.setPosition(
                    position.x * PPM - sprite.getWidth() / 2,
                    position.y * PPM - sprite.getHeight() / 2
            );
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));

            sprite.draw(batch);
        }
    }



    public void takeDamage() {
        System.out.println("Pig Took Damage");
        health--;
        System.out.println("Health after damage: " + health);
        if (health <= 0) {
            System.out.println("Health is zero or less. Removing pig...");
//            removePig();
        }
    }

    public void applyDamage(float damage) {
        health -= damage;
        System.out.println("Pig Took Damage: "+ damage);
        if (health <= 0) {
//            removePig();
        }
    }

    public Body getBody() {
        return pigBody;
    }


    public float calculateDamage() {
        // Get the speed of the bird (magnitude of velocity)
        float speed = getBody().getLinearVelocity().len();

        // Damage could be proportional to speed, for example:
        float damage = speed * 10;  // Adjust the multiplier for appropriate damage scaling

        return damage;
    }

    private void removePig() {
        System.out.println("Pig destroyed!");
        for (Body body : pigBodies) {
            if (world.isLocked()) {
                body.setUserData("destroyed"); // Mark for removal later
            } else {
                world.destroyBody(body); // Remove the physics body immediately
            }
        }

        // Clear the lists after the bodies are destroyed
        pigSprites.clear(); // Remove sprites too
        pigBodies.clear();
    }






    public void dispose() {
        pigTexture.dispose();
        for (Sprite sprite : pigSprites) {
            sprite.getTexture().dispose();
        }
    }
}
