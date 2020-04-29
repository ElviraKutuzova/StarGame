package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen  extends BaseScreen {

    private Texture img;
    private Texture img2;
    private Vector2 pos;
    private Vector2 pos1;
    private Vector2 pos2;
    private Vector2 v;
    private float a;
    private Vector2 touch;

    @Override
    public void show() {
        super.show();
        img2 = new Texture("spaceship.png");
        img = new Texture("stargamefon.jpg");
        pos = new Vector2();
        pos1 = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, 0, 0);

        stopPoint();
        batch.draw(img2, pos.x, pos.y);
        batch.end();
    }
    public void stopPoint(){
        if (pos.dst(touch) > pos1.len()){
            pos.add(pos1);
        }
        else pos.set(touch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img2.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        pos1.set(touch.cpy().sub(pos).nor());
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
