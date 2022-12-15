package main;

import main.enums.State;
import main.objects.animations.Images;
import main.objects.enemies.BossEnemy;
import main.screens.End;
import main.screens.Info;
import main.screens.Menu;
import main.util.*;
import main.util.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1200, HEIGHT = WIDTH / 12 * 9;
    public static State state = State.MENU;
    private int spawnEnemyTimer;
    private ArrayList<String> bossLevels;
    private boolean running = false;
    private Thread thread;
    private Handler handler;
    private StatusBar statusBar;
    private Menu menu;
    private End end;
    private Info info;
    private KeyInput keys;

    public Game() {
        new Window(WIDTH, HEIGHT, "First Game", this);
        this.handler = new Handler();
        this.statusBar = new StatusBar();
        this.menu = new Menu(statusBar, handler);
        this.end = new End(statusBar,handler);
        this.info = new Info();
        this.keys = new KeyInput();
        spawnEnemyTimer = 100;
        bossLevels = new ArrayList<>(Arrays.asList("5", "40", "60", "80", "100"));
        start();
        setListeners();
        loadTextures();
    }

    private void setListeners(){
        addMouseListener(menu);
        addMouseListener(end);
        addKeyListener(keys);
        addKeyListener(info);
    }

    private void loadTextures(){
        /*BufferedImageLoader loader = new BufferedImageLoader();

        try {
            buffImg = loader.loadImage("Devil.png");
        }catch (IOException ioe){
            System.out.println("Exception ignored: " + ioe);
        }

        images = new Images(buffImg);

        boss = images.getImage(1,1,32,32 );
*/
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
            if (running) render();
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
        if (state == State.GAME || state == State.BOSSFIGHT) {

            // Activate boss fight at set levels
            if (state != State.BOSSFIGHT && bossLevels.contains(String.valueOf(statusBar.getLevel()))){
                handler.removeAllExceptPlayer();
                handler.addGameObject(new BossEnemy(handler, statusBar));
                state = State.BOSSFIGHT;

            }else if(state != State.BOSSFIGHT){

                // Spawn enemies at set intervals
                if (spawnEnemyTimer-- <= 0) {
                    handler.addGameObject(EnemyRandomizer.createRandomEnemy(handler));
                    spawnEnemyTimer = 150;
                    statusBar.plusLevel();
                }
            }

            handler.tick();
            statusBar.tick();

        } else if (state == State.INFO) {
            info.tick();
        }
    }

    // Call render methods according to current game state
    private void render(){
        BufferStrategy buffStrat = getBufferStrategy();
        if (buffStrat == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics graph = buffStrat.getDrawGraphics();
        graph.setColor(Color.black);
        graph.fillRect(0,0,WIDTH,HEIGHT);

        if (state == State.GAME || state == State.BOSSFIGHT) {
            handler.render(graph);
            statusBar.render(graph);
        } else if (state == State.MENU) {
            menu.render(graph);
        } else if (state == State.END) {
            handler.render(graph);
            statusBar.render(graph);
            end.render(graph);
        } else if (state == State.INFO) {
            info.render(graph);
        }

        graph.dispose();
        buffStrat.show();
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
}
