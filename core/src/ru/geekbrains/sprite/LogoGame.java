package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class LogoGame extends Sprite {

    private static final float MARGIN = 0.35f;
    private static final float marginHeight = 0.5f;

    public LogoGame(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.4f);
        this.pos.set(worldBounds.pos);
//        setBottom(worldBounds.getBottom() + marginHeight);
//        setLeft(worldBounds.getLeft() + MARGIN);
    }
}
