package io.github.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import io.github.angrybirds.Screens.StartScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirds extends Game {
    public SpriteBatch batch;
    private Music backgroundMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new StartScreen(this));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Theme.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void render() {
        super.render();
    }
}