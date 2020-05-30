package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledButton;

import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class NewGame extends ScaledButton {

    private GameScreen gameScreen;

    private static final float ANIMATE_INTERVAL = 1f;
    private  float animateTimer;
    private boolean scaleUp = true;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("newgame"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if(animateTimer >= ANIMATE_INTERVAL){
            animateTimer = 0f;
            scaleUp =!scaleUp;
        }
        if(scaleUp){
            setScale(getScale() + 0.002f);
        }else {
            setScale(getScale() - 0.002f);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.13f);
        setTop(0.001f);

    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}

