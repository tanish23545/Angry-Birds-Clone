package io.github.angrybirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.angrybirds.AngryBirds;
import io.github.angrybirds.birds.BlackBird;
import io.github.angrybirds.pigs.KingPig;
import io.github.angrybirds.pigs.MediumPig;
import io.github.angrybirds.pigs.Pig;
import io.github.angrybirds.Slingshot;
import io.github.angrybirds.birds.Bird;
import io.github.angrybirds.birds.BlueBird;
import io.github.angrybirds.birds.RedBird;
import io.github.angrybirds.objects.*;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.Timer;
import io.github.angrybirds.pigs.SmallPig;


import java.util.*;


public class Level3 implements Screen, InputProcessor {
    private static final float PPM = 100; // Pixels per meter for scaling
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;
    private Queue<Bird> birdsQueue;
    private Queue<Bird>launchedBird ;
    private ArrayList<Pig> pigs;
    public ArrayList<Body> toBeDestroyed = new ArrayList<>();
    private ArrayList<Materials> materials;
    public int levelNumber = 3;





    private boolean levelCompleted = false;



    private World world;                // The physics world
    private Box2DDebugRenderer debugRenderer; // Visualize physics objects
    private OrthographicCamera camera;  // Camera for rendering

    private SpriteBatch batch;
    private Texture background;
    private Texture birdTexture;
    private Texture pigTexture;
    private Viewport viewport;
    private AngryBirds game;
    private Stage stage;
    private ImageButton backButton;
    private Body groundBody;
    private Bird currentBird;
    private Texture trajectoryTexture;



    Slingshot slingshot;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera b2dCam;

    private Texture woodBlockYTexture;
    private Texture woodBlockHTexture;
    private WoodV woodV;
    private WoodH woodH;
    private int points;




    public Level3(AngryBirds game) {
        this.game = game;
    }

    @Override
    public void show() {
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, 1600 / PPM, 900 / PPM);
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(1600, 900);
        batch = new SpriteBatch();
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(this);

        // Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT);


        // Load textures
        background = new Texture("bg.jpg");
//        slingshot = new Texture("slingshot.png");
//        birdTexture = new Texture("Redbird.png");
        pigTexture = new Texture("pig.png");
//        woodBlockYTexture = new Texture("wood.png");
//        woodBlockHTexture = new Texture("WoodH.png");

        world = new World(new Vector2(0, -1f), true); // Gravity pointing down
        debugRenderer = new Box2DDebugRenderer();
//        redBird = new RedBird(world, 200, 200);
//        sampleBird = new BlueBird(world,120, 200);
        trajectoryTexture = new Texture("trajectory.png");

        birdsQueue = new LinkedList<>();
        launchedBird = new LinkedList<>();
        pigs = new ArrayList<>();
        materials = new ArrayList<>();
        birdsQueue.add(new BlackBird(world, 150,100));
        birdsQueue.add(new BlueBird(world, 100,100));
        birdsQueue.add(new RedBird(world, 50,100));
        slingshot = new Slingshot(200, 115);

        loadNextBird();
        pigs.add(new MediumPig(world, 1000, 370));
        pigs.add(new KingPig(world, 1000, 200));
        pigs.add(new MediumPig(world, 1370, 370));
        pigs.add(new KingPig(world, 1370, 200));
        materials.add(new WoodH(world, 975, 325));
        materials.add(new WoodV(world, 900, 200));
        materials.add(new WoodV(world, 1050, 200));
        materials.add(new WoodV(world,1300 , 200));
        materials.add(new WoodV(world, 1450, 200));
        materials.add(new WoodH(world, 1375, 325));
        createGround();
//        createBird();
        createBackButton();
        for (int i = 0; i < 60; i++) { // Simulate 1 second
            world.step(1 / 60f, 6, 2);
        }


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // Check for pig collision
                if (isPigCollision(fixtureA, fixtureB)) {
                    Pig pig = (Pig) (fixtureA.getUserData() instanceof Pig ? fixtureA.getUserData() : fixtureB.getUserData());
                    pig.takeDamage(); // Reduce pig's health
                }

                // Check for bird collision with objects
                if (isBirdCollision(fixtureA, fixtureB)) {
                    Bird bird = (Bird) (fixtureA.getUserData() instanceof Bird ? fixtureA.getUserData() : fixtureB.getUserData());
                    bird.getBody().setLinearVelocity(0, 0); // Stop bird movement after collision
                }

                if (isMaterialCollision(fixtureA, fixtureB)) {
                    Materials material = (Materials) (fixtureA.getUserData() instanceof Materials ? fixtureA.getUserData() : fixtureB.getUserData());
                    material.takeDamage(); // Stop bird movement after collision
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}

            private boolean isPigCollision(Fixture fixtureA, Fixture fixtureB) {
                return (fixtureA.getUserData() instanceof Pig || fixtureB.getUserData() instanceof Pig) &&
                        (fixtureA.getUserData() == "ground" || fixtureB.getUserData() == "ground");
            }

            private boolean isBirdCollision(Fixture fixtureA, Fixture fixtureB) {
                return (fixtureA.getUserData() instanceof Bird || fixtureB.getUserData() instanceof Bird);
            }

            private boolean isMaterialCollision(Fixture fixtureA, Fixture fixtureB) {
                return (fixtureA.getUserData() instanceof Materials || fixtureB.getUserData() instanceof Materials) &&
                        (fixtureA.getUserData() == "ground" || fixtureB.getUserData() == "ground");
            }

        });





        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);  // Stage for back button
        multiplexer.addProcessor(this);  // Game input
        Gdx.input.setInputProcessor(multiplexer);

//        world.setContactListener(new ContactListener() {
//            @Override
//            public void beginContact(Contact contact) {
//                Fixture fixtureA = contact.getFixtureA();
//                Fixture fixtureB = contact.getFixtureB();
//
//                // Check for pig collisions
//                if (fixtureA.getBody().getUserData() instanceof Pig || fixtureB.getBody().getUserData() instanceof Pig) {
//                    Pig pig = (Pig) (fixtureA.getBody().getUserData() instanceof Pig
//                            ? fixtureA.getBody().getUserData()
//                            : fixtureB.getBody().getUserData());
//                    pig.takeDamage(); // Reduce health
//                    if (pig.health <= 0) {
//                        // Add pig bodies to the destruction queue
//                        for (Body body : pig.pigBodies) {
//                            toBeDestroyed.add(body);
//                        }
//                    }
//                }
//            }
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA.getBody().getUserData() instanceof Materials || fixtureB.getBody().getUserData() instanceof Materials) {
                    Materials material = (Materials) (fixtureA.getBody().getUserData() instanceof Materials
                            ? fixtureA.getBody().getUserData()
                            : fixtureB.getBody().getUserData());

                    // Call takeDamage method to reduce health
                    material.takeDamage();

                    if (material.health <= 0) {
                        for (Body body : material.getBlockBodies()) {
                            toBeDestroyed.add(body);
                        }
                    }
                }
                // Check for pig collisions
                if (fixtureA.getBody().getUserData() instanceof Pig || fixtureB.getBody().getUserData() instanceof Pig) {
                    Pig pig = (Pig) (fixtureA.getBody().getUserData() instanceof Pig
                            ? fixtureA.getBody().getUserData()
                            : fixtureB.getBody().getUserData());
                    pig.takeDamage(); // Reduce health for the pig
                    if (pig.health <= 0) {
                        for (Body body : pig.pigBodies) {
                            toBeDestroyed.add(body);
                        }
                    }
                }

                // Check for material collisions

            }


            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });




    }

    private void checkLevelCompletion() {
//        if (pigs.isEmpty() && birdsQueue.isEmpty() && launchedBird.isEmpty()) {
//            System.out.println("Level Completed!");
//            // Transition to the next level or show completion screen
//            game.setScreen(new LevelCompleteScreen(game));
//        }
    }


    private void loadNextBird() {
        if (!birdsQueue.isEmpty()) {
            // Add a delay of 1 second before loading the next bird
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (!birdsQueue.isEmpty()) { // Ensure the queue is not empty
                        // Dequeue the next bird
                        currentBird = birdsQueue.poll();
                        launchedBird.add(currentBird);


                        if (currentBird != null) { // Ensure currentBird is valid
                            // Position the bird on the slingshot
                            currentBird.getBody().setTransform(
                                    slingshot.getAttachPoint().x / PPM,
                                    slingshot.getAttachPoint().y / PPM,
                                    0
                            );
                            slingshot.setBird(currentBird.getBody());

                            // Log for debugging
                            System.out.println("Loaded bird: " + currentBird.getClass().getSimpleName());
                        } else {
                            System.out.println("No bird to load!");
                        }
                    } else {
                        System.out.println("Bird queue is empty!");
                    }
                }
            }, 1); // Delay of 1 second
        } else {
            // No more birds left
            currentBird = null;
            System.out.println("No more birds in the queue.");
        }
    }




    private void createBackButton() {
        Texture backButtonTexture = new Texture("backButton.png");
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(backButtonTexture);

        backButton = new ImageButton(backButtonStyle);
        backButton.setSize(400, 50);
        backButton.setPosition(-50, 800);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    private Array<Vector2> calculateTrajectory(Vector2 startPosition, Vector2 velocity, int numPoints) {
        Array<Vector2> trajectoryPoints = new Array<>();
        float timeStep = 1 / 60f;
        float gravity = world.getGravity().y;

        for (int i = 0; i < numPoints; i++) {
            float t = i * timeStep;
            float x = startPosition.x + velocity.x * t;
            float y = startPosition.y + velocity.y * t + 0.5f * gravity * t * t;
            trajectoryPoints.add(new Vector2(x, y));
        }

        return trajectoryPoints;
    }

    private void renderTrajectory(SpriteBatch batch, Array<Vector2> trajectoryPoints) {
        for (Vector2 point : trajectoryPoints) {
            batch.draw(trajectoryTexture, point.x * PPM, point.y * PPM, 5, 5); // Draw small circles for trajectory points
        }
    }


    private void createGround() {
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(WINDOW_WIDTH / 2f / PPM , 10 / PPM); // Center of screen
        groundBody = world.createBody(groundDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(WINDOW_WIDTH / 2f / PPM, 100 / PPM); // Half-width and height
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 1f; // High friction
        groundBody.createFixture(groundFixtureDef); // Static body with density 0
        groundShape.dispose();
    }

    //    @Override
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0.4f, 0.6f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Step the physics world
        world.step(1 / 60f, 6, 2);

        // Destroy bodies marked for removal
        if (!toBeDestroyed.isEmpty()) {
            Iterator<Body> iterator = toBeDestroyed.iterator();
            while (iterator.hasNext()) {
                Body body = iterator.next();
                if (!world.isLocked()) {
                    world.destroyBody(body);
                    iterator.remove(); // Remove from the list after destruction
                }
            }
        }

        // Remove pigs whose bodies are destroyed
        pigs.removeIf(pig -> pig.pigBodies.isEmpty());
        materials.removeIf(pig -> pig.getBlockBodies().isEmpty());
        // Check for level completion
        if (pigs.isEmpty() && !levelCompleted) {
            // Add points based on birds in the queue
            for (Bird bird : birdsQueue) {
                points += 10000;
            }

            // Add points if there's a current bird
            if (currentBird != null) {
                points += 10000;
            }

            // Set the flag to indicate the level is complete
            levelCompleted = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("All pigs destroyed! Level Complete! " + points);
                    game.setScreen(new LevelComplete(game, points)); // Transition to LevelCompleteScreen
                }
            }, 3);
            // Schedule a task to switch to the LevelComplete screen after 5 seconds
        }

        if (!pigs.isEmpty() && birdsQueue.isEmpty() && currentBird == null && !levelCompleted) {
            levelCompleted = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("Level Failed! No more birds and pigs are left.");
                    game.setScreen(new LevelFailed(game, levelNumber));
                }
            }, 5); // Delay of 15 seconds
        }


        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Render the game world
        batch.begin();
        batch.draw(background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        for (Bird bird : birdsQueue) {
            bird.render(batch);
        }

        for (Bird bird : launchedBird) {
            bird.render(batch);
        }

        Iterator<Pig> iterator = pigs.iterator();
        while (iterator.hasNext()) {
            Pig pig = iterator.next();
            if (pig.health <= 0) {
                points += pig.getPoints();
                iterator.remove(); // Safely remove the pig
                continue;
            }
            pig.render(batch); // Render only existing pigs
        }

        slingshot.render(batch, shapeRenderer);

        if (currentBird != null && !slingshot.isLaunched()) {
            Vector2 startPosition = new Vector2(slingshot.getAttachPoint().x / PPM, slingshot.getAttachPoint().y / PPM);
            Vector2 velocity = slingshot.getLaunchVelocity();
            Array<Vector2> trajectoryPoints = calculateTrajectory(startPosition, velocity, 100);
            renderTrajectory(batch, trajectoryPoints);
        }

//        woodV.render(batch);
//        woodH.render(batch);
        Iterator<Materials> iterator2 = materials.iterator();
        while (iterator2.hasNext()) {
            Materials material = iterator2.next();
            if (material.health <= 0) {
                points += material.getPoints();
                iterator2.remove(); // Safely remove the pig
                continue;
            }
            material.render(batch); // Render only existing pigs
        }

        batch.end();
        debugRenderer.render(world, camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        b2dCam.update();
        debugRenderer.render(world, b2dCam.combined);
    }





    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        slingshot.touchDown(screenX, Gdx.graphics.getHeight() - screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        slingshot.touchDragged(screenX, Gdx.graphics.getHeight() - screenY);
        return true;
    }


    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        slingshot.touchUp();

        // If the current bird is launched, load the next one
        if (currentBird != null && slingshot.isLaunched()) {
            loadNextBird();
        }
        return true;
    }


    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        slingshot.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
    }


}