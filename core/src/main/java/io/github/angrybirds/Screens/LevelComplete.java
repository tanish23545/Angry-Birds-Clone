package io.github.angrybirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.angrybirds.AngryBirds;

public class LevelComplete implements Screen {

    private AngryBirds game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;  // Add a texture for the background
    private OrthographicCamera camera;
    private String levelText;
    private ImageButton backButton;
    private ImageButton restartButton;
    private Stage stage;
    private Viewport viewport;



    private int points;

    private GlyphLayout layout;

    public LevelComplete(AngryBirds game, int points) {
        this.game = game;
        this.points = points;
        this.levelText = "Level Complete!\nPoints: " + points;
    }

    @Override
    public void show() {
        // Initialize camera and sprite batch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);  // Set the camera to fit your window size
        batch = new SpriteBatch();
        viewport = new FitViewport(1600,900);


        // Load the background image
        background = new Texture("LevelComplete.jpg");  // Make sure this file is in your assets folder
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        // Load the system font dynamically using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Bold.ttf"));  // Use a system font file (e.g., Arial.ttf)
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;  // Set the font size
        font = generator.generateFont(parameter);  // Create the font

        // Set text color
        font.setColor(Color.WHITE);

        // Create GlyphLayout for text measurement
        layout = new GlyphLayout();
        layout.setText(font, levelText);  // Set the layout for the text

        generator.dispose();  // Dispose of the generator to free up resources


        Texture oneTexture = new Texture("RestartButton.png");
        ImageButton.ImageButtonStyle oneStyle = new ImageButton.ImageButtonStyle();
        oneStyle.imageUp = new TextureRegionDrawable(oneTexture);

        restartButton = new ImageButton(oneStyle);
        restartButton.setSize(250, 250);
        restartButton.setPosition(685,88);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Restarting Level");
                game.setScreen(new Level1(game));
            }
        });
        stage.addActor(restartButton);

        Texture backButtonTexture = new Texture("MenuButton.png");
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(backButtonTexture);

        backButton = new ImageButton(backButtonStyle);
        backButton.setSize(250, 250);
        backButton.setPosition(400, 88);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked! Terminating Game");
                game.setScreen(new LevelScreen(game));
            }
        });
        stage.addActor(backButton);
    }


    @Override
    public void render(float delta) {
        // Clear the screen with a background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Start drawing
        batch.begin();

        // Draw the background image
        batch.draw(background, 0, 0, 1600, 900);  // Adjust the size to fill the screen

        // Draw the level complete text at a larger size
        float x = (1600 - layout.width) / 2;  // Center the text horizontally
        float y = 450;  // Adjust y to position text where you want it
        font.draw(batch, levelText, x, y);  // Draw the text using the layout

        // End drawing
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        background.dispose();  // Don't forget to dispose of the background texture
    }
}
