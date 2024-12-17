package io.github.angrybirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.angrybirds.AngryBirds;

public class StartScreen implements Screen {

    AngryBirds game;
    Viewport viewport;
    Texture background;
    ImageButton playButton;
    ImageButton exitButton;
    Stage stage;


    public StartScreen(AngryBirds game) {
        this.game = game;
        background = new Texture("startingBackground.png");
        viewport = new FitViewport(1600, 900);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        // play button
        Texture playButtonTexture = new Texture("playButtonUnclicked.png");
        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.imageUp = new TextureRegionDrawable(playButtonTexture);

        playButton = new ImageButton(playButtonStyle);
        playButton.setSize(400, 50);
        playButton.setPosition(400, 50);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Play button clicked! Switching to Levels Screen.");
                game.setScreen(new LevelScreen(game));
//                game.setScreen(new LevelComplete(game, 10000));
            }
        });
        stage.addActor(playButton);

        // exit Button
        Texture exitButtonTexture = new Texture("exitButton.png");
        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = new TextureRegionDrawable(exitButtonTexture);

        exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(400, 50);
        exitButton.setPosition(800, 50);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked! Terminating Game");
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    }
}
