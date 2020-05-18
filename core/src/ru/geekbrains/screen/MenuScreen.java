package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;

import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;


public class MenuScreen  extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;


    @Override
    public void show() {
        super.show();
        bg = new Texture("stargamefon.jpg");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/MenuAtlas.pack"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
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
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer,button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer,button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update (float delta){

        }

        private void draw () {
            batch.begin();
            background.draw(batch);
            buttonExit.draw(batch);
            buttonPlay.draw(batch);
            batch.end();

    }

}
