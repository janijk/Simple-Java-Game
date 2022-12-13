package main;

import main.enums.ID;
import main.enums.STATE;
import main.objects.Player;
import main.screens.End;
import main.screens.Menu;
import main.util.*;
import main.util.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1200, HEIGHT = WIDTH / 12 * 9;
    public static STATE state;
    private int spawnEnemyTimer;
    private boolean running = false;
    private Thread thread;
    private Handler handler;
    private StatusBar statusBar;
    private Menu menu;
    private End end;
    private KeyInput keys;

    public Game() {
        new Window(WIDTH, HEIGHT, "First Game", this);
        handler = new Handler();
        statusBar = new StatusBar();
        state = STATE.MENU;
        menu = new Menu(statusBar,handler);
        end = new End(statusBar,handler);
        keys = new KeyInput();
        spawnEnemyTimer = 100;
        start();
        initialize();
    }

    private void initialize(){
        handler.addGameObject(new Player(Game.WIDTH/2, Game.HEIGHT/2, ID.Player, handler, statusBar));
        addMouseListener(menu);
        addMouseListener(end);
        addKeyListener(keys);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Game loop
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
        double amountOfTicks = 60.0, ns = 1_000_000_000 / amountOfTicks, delta = 0;
        //int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if (running){
                render();
            }
            //frames++;
            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                /*System.out.println("FPS: " + frames);
                frames = 0;*/
            }
        }
        stop();
    }

    // Call update (tick) methods according to current game state
    private void tick(){
        if (state == STATE.GAME) {
            if (spawnEnemyTimer-- <= 0) {
                handler.addGameObject(EnemyRandomizer.createRandomEnemy(handler));
                spawnEnemyTimer = 150;
                statusBar.plusLevel();
            }
            handler.tick();
            statusBar.tick();
        }
    }

    // Call render methods according to current game state
    private void render(){
        BufferStrategy bufStrat = getBufferStrategy();
        if (bufStrat == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics graph = bufStrat.getDrawGraphics();
        graph.setColor(Color.black);
        graph.fillRect(0,0,WIDTH,HEIGHT);

        if (state == STATE.GAME) {
            handler.render(graph);
            statusBar.render(graph);
        } else if (state == STATE.MENU) {
            menu.render(graph);
        } else if (state == STATE.END) {
            handler.render(graph);
            statusBar.render(graph);
            end.render(graph);
        }

        graph.dispose();
        bufStrat.show();
    }

    // Method to prevent GameObjects from going beyond visible screen
    public static float screenLimit(float var, float min, float max, boolean xAxis){
        if (xAxis) {
            return var < min ? min : Math.min(var, max - 40);
        }
        return var < min ? min : Math.min(var, max - 64);
    }

    // Method to limit maximum speed of GameObjects
    public static float limit(float val, float min, float max){
        if (val > max) {
            val = max;
        }else if (val < min) {
            val = min;
        }
        return val;
    }

    public static void main(String[] args) {
        new Game();
    }
}
