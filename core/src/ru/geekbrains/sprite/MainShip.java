package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MainShip  extends Sprite {

    private static final float size = 0.15f;
    private static final float margin = 0.05f;
    private static final int INVALID_POINTER = -1;

    private final Vector2 v0;
    private Vector2 v;

    private int leftPointer;
    private  int rightPointer;

    private boolean pressedLeft;
    private boolean pressedRight;

    private Rect worldBounds;

    public MainShip (TextureAtlas atlas){
        super(atlas.findRegion("main_ship"),1 , 2, 2);
        v0 = new Vector2(0.5f,0);
        v = new Vector2();
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
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
        pos.mulAdd(v, delta);
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
        this.worldBounds = worldBounds;
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
