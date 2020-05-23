package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private  TextureAtlas atlas;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Music music;
    private EnemyEmitter enemyEmitter;
    private ExplosionPool explosionPool;

    @Override
    public void show() {
        super.show();
        bg = new Texture("stargamefon.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[136];
        for (int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool,worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicFon.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();

    }

    @Override
    public void resize(Rect worldBounds) {

        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta){
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        mainShip.update(delta);
        enemyEmitter.generate(delta);

    }

    private void free(){
      bulletPool.freeAllDestroyed();
      enemyPool.freeAllDestroyed();
      explosionPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }
}
