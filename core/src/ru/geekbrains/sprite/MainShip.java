package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip  extends Ship {

    private static final float size = 0.15f;
    private static final float margin = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final int HP = 100;

    private int leftPointer;
    private  int rightPointer;

    private boolean pressedLeft;
    private boolean pressedRight;

    public MainShip(TextureAtlas atlasM, BulletPool bulletPool, ExplosionPool explosionPool){
        super(atlasM.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletRegion = atlasM.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.7f);
        bulletHeight = 0.02f;
        damage = 1;
        v0.set(0.5f,0);
        reloadInterval = 0.2f;
        reloadTimer = reloadInterval;
        startNewGame();
    }

    public void startNewGame(){
        hp = HP;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        pressedLeft = false;
        pressedRight = false;
        stop();
        this.pos.x = 0;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));
        flushDestroy();
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight) {
                    moveRight();
                } else{
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft){
                    moveLeft();
                }else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        autoShoot(delta);
        if (getLeft() < worldBounds.getLeft()){
            stop();
            setLeft(worldBounds.getLeft());
        }
        if (getRight() > worldBounds.getRight()){
            stop();
            setRight(worldBounds.getRight());
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(size);
        setBottom(worldBounds.getBottom() + margin);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x){
            if(leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        }else{
            if( rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER){
                moveRight();
            }else {
                stop();
            }
        }else if (pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER){
                moveLeft();
            }else{
                stop();
            }
        }
        return false;
    }

    public void dispose(){
        sound.dispose();

    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }



    private void moveRight(){
        v.set(v0);
    }

    private  void moveLeft(){
        v.set(v0).rotate(180);
    }

    private void stop(){
        v.setZero();
    }

}
