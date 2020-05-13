package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;

import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Logo;

public class MenuScreen  extends BaseScreen {

    private Texture bg;
    private Texture ssh;
    private Background background;

    private Logo logo;


    @Override
    public void show() {
        super.show();
        ssh = new Texture("spaceship.png");
        bg = new Texture("stargamefon.jpg");
        background = new Background(bg);
        logo = new Logo(ssh);
    }

    @Override
    public void resize(Rect worldBounds) {

        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();

    }
//    public void stopPoint(){
//        if (pos.dst(touch) > pos1.len()){
//            pos.add(pos1);
//        }
//        else pos.set(touch);
//    }

    @Override
    public void dispose() {
//        batch.dispose();
        ssh.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }

        //    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
//        pos1.set(touch.cpy().sub(pos).nor());
//        return super.touchDown(screenX, screenY, pointer, button);
//    }
        private void update ( float delta){
            logo.update(delta);
        }

        private void draw () {
            batch.begin();
            background.draw(batch);
            logo.draw(batch);
            batch.end();

        }


}
