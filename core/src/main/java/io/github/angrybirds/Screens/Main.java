package io.github.angrybirds.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture backgroudTexture;
    Texture Level1;
    Texture Level2;
    Texture Level3;
    SpriteBatch spriteBatch;
    FitViewport viewport;


    @Override
    public void create() {
        backgroudTexture = new Texture("GoldenIslandArtwork.png");
        Level1 = new Texture("Level1.png");
        Level2 = new Texture("Level2.png");
        Level3 = new Texture("Level3.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(16, 9);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {

        input();
        logic();
        draw();
    }

    private void input() {

    }

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroudTexture, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(Level1, 2, 2, 1, 1);
        spriteBatch.draw(Level2, 4, 2, 1, 1);
        spriteBatch.draw(Level3, 6, 2, 1, 1);


        spriteBatch.end();

    }


}
