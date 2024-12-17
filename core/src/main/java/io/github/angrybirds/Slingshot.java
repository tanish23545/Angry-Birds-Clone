//package io.github.angrybirds;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import io.github.angrybirds.birds.*;
//
//public class Slingshot {
//    private static final float ppm = 100; // Pixels per meter for scaling
//
//    private Texture baseTexture;   // Texture for the slingshot base
//    private Vector2 basePosition; // Position of the slingshot base (in pixels)
//
//    private float maxStretchDistance; // Maximum distance the bird can stretch (in meters)
//    private boolean isDragging;       // Whether the slingshot is being dragged
//    private Vector2 rubberBandStart;  // Rubber band attachment point on the slingshot
//    private Body birdBody;
//    private boolean isLaunched = false;
//
//    public Slingshot(float x, float y) {
//        this.baseTexture = new Texture("slingshot.png");
//        this.basePosition = new Vector2(x, y);
//        this.maxStretchDistance = 1f;
//        this.isDragging = false;
//        this.rubberBandStart = new Vector2(x + 100, y + 150); // Attach rubber band near the top
//    }
//
//    public void setBird(Body birdBody) {
//        this.birdBody = birdBody;
//        this.isLaunched = false; // Reset the launched state for the new bird
//    }
//
//
//    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
//        // Draw the slingshot base
//        batch.draw(baseTexture, basePosition.x, basePosition.y, 170, 200);
//        // Draw the rubber band (only if dragging)
//        if (isDragging && birdBody != null) {
//            float birdX = birdBody.getPosition().x * ppm;
//            float birdY = birdBody.getPosition().y * ppm;
//
////            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
////            shapeRenderer.line(rubberBandStart.x, rubberBandStart.y, birdX, birdY); // Top band
////            shapeRenderer.line(rubberBandStart.x, rubberBandStart.y - 50, birdX, birdY); // Bottom band
////            shapeRenderer.end();
//        }
//    }
//
//    public boolean touchDown(float screenX, float screenY) {
//        // Ensure there's a bird and it hasn't been launched yet
//        if (birdBody == null || isLaunched) return false;
//
//        float birdX = birdBody.getPosition().x * ppm;
//        float birdY = birdBody.getPosition().y * ppm;
//
//        // Check if the touch is near the bird
//        if (Math.hypot(screenX - birdX, screenY - birdY) < 50) {
//            isDragging = true;
//        }
//        return isDragging;
//    }
//
//
//    public void touchDragged(float screenX, float screenY) {
//        if (isDragging && birdBody != null) {
//            // Convert screen coordinates to Box2D world coordinates
//            float targetX = screenX / ppm;
//            float targetY = screenY / ppm;
//
//            // Calculate drag distance from the slingshot base
//            Vector2 slingshotPos = new Vector2(rubberBandStart.x / ppm, rubberBandStart.y / ppm);
//            Vector2 dragVector = new Vector2(targetX, targetY).sub(slingshotPos);
//
//            // Clamp drag distance to max stretch
//            if (dragVector.len() > maxStretchDistance) {
//                dragVector.setLength(maxStretchDistance);
//            }
//
//            // Smoothly update bird's position
//            birdBody.setTransform(slingshotPos.add(dragVector), 0);
//            birdBody.setLinearVelocity(0, 0); // Stop residual velocity
//        }
//    }
//
//
//    public void lockBirdToSlingshot() {
//        if (!isDragging && !isLaunched && birdBody != null) {
//            // Lock bird to the slingshot's rubber band start
//            birdBody.setTransform(rubberBandStart.x / ppm, rubberBandStart.y / ppm, 0);
//            birdBody.setLinearVelocity(0, 0); // Stop all motion
//            birdBody.setAngularVelocity(0);  // Stop any rotation
//            birdBody.setAwake(false);        // Freeze physics for the bird
//        }
//    }
//
//
//
////    public void attachBird(Body birdBody) {
////        if (birdBody != null) {
////            // Convert rubberBandStart (pixels) to Box2D world coordinates (meters)
////            float startX = rubberBandStart.x / ppm;
////            float startY = rubberBandStart.y / ppm;
////
////            // Set bird's position to align with slingshot base
////            birdBody.setTransform(startX, startY, 0);
////        }
////    }
//
//    public Vector2 getAttachPoint() {
//        return new Vector2(rubberBandStart.x, rubberBandStart.y);
//    }
//
//    public void touchUp() {
//        if (isDragging && birdBody != null) {
//            isDragging = false; // Stop dragging
//            isLaunched = true;  // Mark the bird as launched
//
//            // Calculate release vector
//            Vector2 slingshotPos = new Vector2(rubberBandStart.x / ppm, rubberBandStart.y / ppm);
//            Vector2 releaseVector = slingshotPos.sub(birdBody.getPosition());
//
//            // Adjust speed for specific birds
////            if (birdBody.getUserData() instanceof BlueBird) {
////                releaseVector.scl(0.1f); // Scale down speed for BlueBird
////            } else if (birdBody.getUserData() instanceof RedBird) {
////                releaseVector.scl(4.0f); // Default scaling for RedBird
////            }
//            // Calculate release vector
////            Vector2 slingshotPos = new Vector2(rubberBandStart.x / ppm, rubberBandStart.y / ppm);
////            Vector2 releaseVector = slingshotPos.sub(birdBody.getPosition());
//
//            // Apply impulse if release vector is significant
//            if (releaseVector.len() > 0.1f) {
//                birdBody.setAwake(true); // Wake the body for physics simulation
//
//                // Adjust speed for specific birds
//                if (birdBody.getUserData() instanceof BlueBird) {
//                    releaseVector.scl(0.3f); // Scale down speed for BlueBird
//                } else if (birdBody.getUserData() instanceof RedBird) {
//                    releaseVector.scl(1.0f); // Full speed for RedBird
//                }
//
//                birdBody.applyLinearImpulse(releaseVector, birdBody.getWorldCenter(), true);
//            } else {
//                lockBirdToSlingshot(); // Snap back if not pulled far enough
//            }
//        }
//    }
//
//
//    public void dispose() {
//        baseTexture.dispose();
//    }
//
//    public boolean isLaunched() {
//        return isLaunched;
//    }
//}


package io.github.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.angrybirds.birds.*;

public class Slingshot {
    private static final float ppm = 100; // Pixels per meter for scaling

    private Texture baseTexture;   // Texture for the slingshot base
    private Vector2 basePosition; // Position of the slingshot base (in pixels)

    private float maxStretchDistance; // Maximum distance the bird can stretch (in meters)
    private boolean isDragging;       // Whether the slingshot is being dragged
    private Vector2 rubberBandStart;  // Rubber band attachment point on the slingshot
    private Body birdBody;
    private boolean isLaunched = false;

    public Slingshot(float x, float y) {
        this.baseTexture = new Texture("slingshot.png");
        this.basePosition = new Vector2(x, y);
        this.maxStretchDistance = 1f;
        this.isDragging = false;
        this.rubberBandStart = new Vector2(x + 100, y + 150); // Attach rubber band near the top
    }

    public void setBird(Body birdBody) {
        this.birdBody = birdBody;
        this.isLaunched = false; // Reset the launched state for the new bird
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        // Draw the slingshot base
        batch.draw(baseTexture, basePosition.x, basePosition.y, 170, 200);
        // Draw the rubber band (only if dragging)
        if (isDragging && birdBody != null) {
            float birdX = birdBody.getPosition().x * ppm;
            float birdY = birdBody.getPosition().y * ppm;
        }
    }

    public boolean touchDown(float screenX, float screenY) {
        // Ensure there's a bird and it hasn't been launched yet
        if (birdBody == null || isLaunched) return false;

        float birdX = birdBody.getPosition().x * ppm;
        float birdY = birdBody.getPosition().y * ppm;

        // Check if the touch is near the bird
        if (Math.hypot(screenX - birdX, screenY - birdY) < 50) {
            isDragging = true;
        }
        return isDragging;
    }

    public void touchDragged(float screenX, float screenY) {
        if (isDragging && birdBody != null) {
            // Convert screen coordinates to Box2D world coordinates
            float targetX = screenX / ppm;
            float targetY = screenY / ppm;

            // Calculate drag distance from the slingshot base
            Vector2 slingshotPos = new Vector2(rubberBandStart.x / ppm, rubberBandStart.y / ppm);
            Vector2 dragVector = new Vector2(targetX, targetY).sub(slingshotPos);

            // Clamp drag distance to max stretch
            if (dragVector.len() > maxStretchDistance) {
                dragVector.setLength(maxStretchDistance);
            }

            // Smoothly update bird's position
            birdBody.setTransform(slingshotPos.add(dragVector), 0);
            birdBody.setLinearVelocity(0, 0); // Stop residual velocity
        }
    }

    public void lockBirdToSlingshot() {
        if (!isDragging && !isLaunched && birdBody != null) {
            // Lock bird to the slingshot's rubber band start
            birdBody.setTransform(rubberBandStart.x / ppm, rubberBandStart.y / ppm, 0);
            birdBody.setLinearVelocity(0, 0); // Stop all motion
            birdBody.setAngularVelocity(0);  // Stop any rotation
            birdBody.setAwake(false);        // Freeze physics for the bird
        }
    }

    public Vector2 getAttachPoint() {
        return new Vector2(rubberBandStart.x, rubberBandStart.y);
    }

    public void touchUp() {
        if (isDragging && birdBody != null) {
            isDragging = false; // Stop dragging
            isLaunched = true;  // Mark the bird as launched

            // Calculate release vector
            Vector2 slingshotPos = new Vector2(rubberBandStart.x / ppm, rubberBandStart.y / ppm);
            Vector2 releaseVector = slingshotPos.sub(birdBody.getPosition());

            // Apply impulse if release vector is significant
            if (releaseVector.len() > 0.1f) {
                birdBody.setAwake(true); // Wake the body for physics simulation

                // Adjust speed for specific birds
                if (birdBody.getUserData() instanceof BlueBird) {
                    releaseVector.scl(0.3f); // Scale down speed for BlueBird
                } else if (birdBody.getUserData() instanceof RedBird) {
                    releaseVector.scl(1.0f); // Full speed for RedBird
                }

                birdBody.applyLinearImpulse(releaseVector, birdBody.getWorldCenter(), true);
            } else {
                lockBirdToSlingshot(); // Snap back if not pulled far enough
            }
        }
    }

    public Vector2 getLaunchVelocity() {
        float launchSpeed = 3; // Adjust this value as needed
        Vector2 launchDirection = new Vector2(rubberBandStart).sub(birdBody.getPosition().scl(ppm)).nor();
        return launchDirection.scl(launchSpeed);
    }

    public void dispose() {
        baseTexture.dispose();
    }

    public boolean isLaunched() {
        return isLaunched;
    }
}