package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;

import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.sprite.LogoGame;
import ru.geekbrains.sprite.Star;


public class MenuScreen  extends BaseScreen {

    private final Game game;
    private Texture bg;
    private Background background;
    private LogoGame logoGame;
    private TextureAtlas atlas;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private Texture name;
    private Music music;
    private Star[] stars;

    public MenuScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        super.show();
        bg = new Texture("stargamefon.png");
        name = new Texture("name.png");
        background = new Background(bg);
        logoGame = new LogoGame(name);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.pack"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
        music.setLooping(true);
        music.play();
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logoGame.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        name.dispose();
        atlas.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        logoGame.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

}
