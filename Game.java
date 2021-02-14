package com.vulture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
    public  static final String GameName="Across the Country";
    public static final int HEIGHT=140;
    public static final int WIDTH=180;
    public  static final  int scale=3;
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private  int[] pixels=((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private boolean running=false;
    private int tickCount;
    private Screen screen ;
    public void start(){
        running=true;
        new Thread(this).start();// for this new thread to run we must set the class in implements Runnable

    }
    public void stop(){
        running=false;
    }
    private void init() {
        try {
            screen=new Screen(WIDTH,HEIGHT,new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icon2.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void render(){
        BufferStrategy bs=getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);
            return;

        }
        screen.render(pixels,0,WIDTH);
        //Graphics g=image.getGraphics();
        //g.setColor(Color.blue);
        //g.fillRect(0,0,WIDTH/2,HEIGHT/2);
        //g.dispose();
        Graphics g=bs.getDrawGraphics();
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        g.dispose();
        bs.show();
    }
    private  void tick(){
        tickCount++;
    }
    public void run(){
        double unprocessed=0;
        int frames=0;
        double nsPerTick=100000000000.0/60;
        long lastTime=System.currentTimeMillis();
        init();
        while(running){
            long now=System.nanoTime();
            unprocessed+=(now - lastTime)/nsPerTick;
            lastTime=now;
            while(unprocessed>=1){
                tick();
                unprocessed-=1;
            }
            {
                frames++;
                render();
            }
            try {
                Thread.sleep((int)unprocessed*1000/60);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            if(System.currentTimeMillis()-lastTime>1000){
                lastTime+=100;
                System.out.println(frames + "fps");
                frames++;
            }

        }
    }


    public  static void main(String[] args){
        Game game=new Game();
        game.setMinimumSize(new Dimension(WIDTH*scale,HEIGHT*scale));
        game.setMaximumSize(new Dimension(WIDTH*scale,HEIGHT*scale));
        game.setPreferredSize(new Dimension(WIDTH*scale,HEIGHT*scale));

        JFrame frame = new JFrame(Game.GameName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();

    }
}
