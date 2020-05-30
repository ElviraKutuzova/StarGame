package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import java.util.logging.Level;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonExitGameOver;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.NewGame;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final float FONT_SIZE = 0.02f;
    private static final String FRAGS = "Frags: ";
    private static final float TEXT_MARGIN = 0.01f;
    private static final String HP= "Hp: ";
    private static final String LEVEL = "Level: ";

    public static GameScreen.State State;

    private enum State {PLAYING, GAME_OVER }

    private final Game game;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Music music;
    private EnemyEmitter enemyEmitter;
    private ExplosionPool explosionPool;
    private State state;
    private GameOver gameOver;
    private NewGame newGame;
    private Font font;
    private ButtonExitGameOver buttonExitGameOver;
    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;


    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("stargamefon.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.pack"));
        stars = new Star[136];
        for (int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool,worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);
        gameOver = new GameOver(atlas);
        buttonExitGameOver = new ButtonExitGameOver(atlas);
        newGame = new NewGame(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicFon.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
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
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
        buttonExitGameOver.resize(worldBounds);
        font.setSize(FONT_SIZE);
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
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }else if(state == State.GAME_OVER){
            newGame.touchDown(touch,pointer,button);
            buttonExitGameOver.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        }else if(state == State.GAME_OVER){
            newGame.touchUp(touch,pointer,button);
            buttonExitGameOver.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void startNewGame(){
        frags = 0;
        mainShip.startNewGame();
        enemyPool.startNewGame();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        state = State.PLAYING;
    }

    private void update(float delta){
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if(state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            mainShip.update(delta);
            enemyEmitter.generate(delta, frags);
        } else if (state == State.GAME_OVER){
            newGame.update(delta);
            buttonExitGameOver.update(delta);
        }

    }

    private void checkCollision(){
        if(state != State.PLAYING){
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for(Enemy enemy : enemyList){
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if(mainShip.pos.dst(enemy.pos) < minDist){
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                continue;
            }
            for(Bullet bullet : bulletList){
                if(bullet.getOwner() != mainShip || bullet.isDestroyed()){
                    continue;
                }
                if(enemy.isBulletCollision(bullet)){
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()){
                        frags +=1;
                    }
                }
            }
        }
        for(Bullet bullet : bulletList){
            if(bullet.getOwner() == mainShip || bullet.isDestroyed()){
                continue;
            }
            if(mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.isDestroyed()){
            enemyPool.dispose();
            mainShip.dispose();
            state = State.GAME_OVER;
        }
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
        if(state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
        }else if(state == State.GAME_OVER){
            newGame.draw(batch);
            gameOver.draw(batch);
            buttonExitGameOver.draw(batch);
        }
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft()+TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - TEXT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN, Align.right);

    }


}
