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

public class LevelScreen implements Screen {
    AngryBirds game;
    Texture background;
    Viewport viewport;
    ImageButton one;
    ImageButton two;
    ImageButton three;
    ImageButton backButton;
    Stage stage;

    public LevelScreen(AngryBirds game){
        this.game = game;
        background = new Texture("LevelBackground-final.png");
        viewport = new FitViewport(1600,900);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        // Level 1
        Texture oneTexture = new Texture("level-1.png");
        ImageButton.ImageButtonStyle oneStyle = new ImageButton.ImageButtonStyle();
        oneStyle.imageUp = new TextureRegionDrawable(oneTexture);

        one = new ImageButton(oneStyle);
        one.setSize(200, 200);
        one.setPosition(500 - one.getWidth()/2, 450 - one.getHeight()/2);

        one.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked! Terminating Game");
                game.setScreen(new Level1(game));
            }
        });
        stage.addActor(one);


        // Level 2
        Texture twoTexture = new Texture("level-2.png");
        ImageButton.ImageButtonStyle twoStyle = new ImageButton.ImageButtonStyle();
        twoStyle.imageUp = new TextureRegionDrawable(twoTexture);

        two = new ImageButton(twoStyle);
        two.setSize(200, 200);
        two.setPosition(1100 - two.getWidth()/2, 450 - two.getHeight()/2);

        two.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked! Terminating Game");
                game.setScreen(new Level2(game));
            }
        });
        stage.addActor(two);

        // Level 3

        Texture threeTexture = new Texture("level-3.png");
        ImageButton.ImageButtonStyle threeStyle = new ImageButton.ImageButtonStyle();
        threeStyle.imageUp = new TextureRegionDrawable(threeTexture);

        three = new ImageButton(threeStyle);
        three.setSize(200, 200);
        three.setPosition(800 - three.getWidth()/2, 450 - three.getHeight()/2);

        three.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked! Terminating Game");
                game.setScreen(new Level3(game));
            }
        });
        stage.addActor(three);



        Texture backButtonTexture = new Texture("backButton.png");
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.imageUp = new TextureRegionDrawable(backButtonTexture);

        backButton = new ImageButton(backButtonStyle);
        backButton.setSize(400, 50);
        backButton.setPosition(-50, 800);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked! Terminating Game");
                game.setScreen(new StartScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background,0,0,viewport.getWorldWidth(), viewport.getScreenHeight());
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
