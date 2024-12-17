package io.github.angrybirds.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public abstract class Materials {
    protected static final float PPM = 100; // Pixels per meter
    protected World world;
    protected Array<Body> blockBodies = new Array<>();
    protected Array<Sprite> blockSprites = new Array<>();
    protected Texture materialTexture;
    protected int points;
    public int health;
    protected Body blockBody;



    public Materials(World world, int health, String texturePath) {
        this.world = world;
        this.health = health;
        materialTexture = new Texture(texturePath);
    }

    public int getPoints() {
        return points;
    }

    protected void createBlock(float x, float y, float width, float height) {
        // Create physics body
        BodyDef blockDef = new BodyDef();
        blockDef.type = BodyDef.BodyType.DynamicBody;
        blockDef.position.set(x / PPM, y / PPM);
        blockBody = world.createBody(blockDef);

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.9f; // Set friction

        blockBody.createFixture(fixtureDef);
        blockShape.dispose();
        blockBody.setUserData(this);

        // Create corresponding sprite
        Sprite blockSprite = new Sprite(materialTexture);
        blockSprite.setSize(width, height);
        blockSprite.setOrigin(blockSprite.getWidth() / 2, blockSprite.getHeight() / 2);

        blockBodies.add(blockBody);
        blockSprites.add(blockSprite);
    }

    public Array<Body> getBlockBodies() {
        return blockBodies;
    }

    public Body getBody() {
        return blockBody;
    }

    public void render(Batch batch) {
        if (blockBodies.isEmpty()) {
            return;
        }
        for (int i = 0; i < blockBodies.size; i++) {
            Body body = blockBodies.get(i);
            Sprite sprite = blockSprites.get(i);

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

    public void applyDamage(float damage) {
        health -= damage;
        System.out.println("Material took Damage: "+ damage);
        if (health <= 0) {
//            removePig();
        }
    }

    public void takeDamage() {
        System.out.println("Material Took Damage");
        health--;
        System.out.println("Health after damage: " + health);
        if (health <= 0) {
            System.out.println("Health is zero or less. Removing material...");
//            removePig();
        }
    }

    private void removeMaterial() {
        System.out.println("Material destroyed!");
        for (Body body : blockBodies) {
            if (world.isLocked()) {
                body.setUserData("destroyed"); // Mark for removal later
            } else {
                world.destroyBody(body); // Remove the physics body immediately
            }
        }

        // Clear the lists after the bodies are destroyed
        blockSprites.clear(); // Remove sprites too
        blockBodies.clear();
    }

    public void dispose() {
        materialTexture.dispose();
        for (Sprite sprite : blockSprites) {
            // Additional sprite disposal if needed
        }
    }
}