package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;

public class ButtonExitGameOver extends ScaledButton {

    public ButtonExitGameOver (TextureAtlas atlas){
        super(atlas.findRegion("Exit"));

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.13f);
        setBottom(worldBounds.getBottom() + 0.2f);
    }
    @Override
    public void action() {
        Gdx.app.exit();
    }
}
