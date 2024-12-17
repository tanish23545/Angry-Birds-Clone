package io.github.angrybirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.angrybirds.AngryBirds;

public class LevelFailed implements Screen {
    private final AngryBirds game;
    private final Texture levelFailedTexture;
    private final SpriteBatch batch;
    private ImageButton backButton;
    private ImageButton restartButton;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private int level;

    public LevelFailed(AngryBirds game, int level) {
        this.game = game;
        this.levelFailedTexture = new Texture("LevelFailed.png");
        this.batch = new SpriteBatch();
        this.level = level;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);  // Set the camera to fit your window size
        viewport = new FitViewport(1600,900);

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

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
                if (level == 1){
                    game.setScreen(new Level1(game));

                } else if (level == 2) {
                    game.setScreen(new Level2(game));

                } else if (level == 3) {
                    game.setScreen(new Level3(game));

                }
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(levelFailedTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        levelFailedTexture.dispose();
        batch.dispose();
    }
}
