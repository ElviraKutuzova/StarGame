package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Enemy;

public class EnemyPool extends SpritesPool {

    private BulletPool bulletPool;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    private Sound sound;

    public EnemyPool(BulletPool bulletPool,ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/enemyShot.mp3"));
    }
    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, worldBounds,sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
