package me.dane.pacman;

import me.dane.pacman.events.ButtonClickEvent;
import me.dane.pacman.events.KeyInput;
import me.dane.pacman.objects.Score;
import me.dane.pacman.screens.MapImage;
import me.dane.pacman.statics.ScreenHandler;
import me.dane.pacman.statics.StaticWindow;
import me.dane.pacman.util.Map;
import me.dane.pacman.util.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Pacman extends Canvas implements Runnable{

    private boolean running = false;
    private Thread thread;
    private GameHandler gh;

    private Map map;

    public Pacman(GameHandler gh) {
        this.gh = gh;
    }

    public static void main(String[] args) {
        Window win = new Window("Pacman");
        StaticWindow.setWindow(win);

        ScreenHandler.setScreenNum(1);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                win.checker();
            }
        });
    }

    @Override
    public void run() {
        this.requestFocus();
        this.init();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(running) {
            long now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;

            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void init() {
        map = new Map();
        new MapImage(map, gh);

        this.addKeyListener(new KeyInput(gh));

        Score score = new Score(100, 100);
        score.activateMultiplier();
    }

    private void tick() {
        gh.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.drawRect(getWidth()/10 * 2, getHeight()/10, getWidth()/10 * 6, getHeight()/10 * 8);
        g.setColor(Color.BLACK);
        g.fillRect(getWidth()/10 * 2 + 1, getHeight()/10 + 1, getWidth()/10 * 6 - 1, getHeight()/10 * 8 - 1);

        gh.render(g);

        g.dispose();
        bs.show();
    }

    public synchronized void start() {
        if(running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @SuppressWarnings("deprecation")
    public synchronized void stop() {
        running = false;
        thread.stop();
    }

}