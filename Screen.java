package com.vulture;
import java.util.ArrayList;
import  java.util.List;
import java.util.Map;

public class Screen {
    private  static  final int WIDTH=64;
    private  static  final int MAP_WIDTH_MASK=WIDTH - 1;
    private static   final int SPRITE_SHEET_SIZE=256;
    private List <Sprite> sprites = new ArrayList<Sprite>();
    public  int xScroll;
    public   int yScroll;
    private  int [] tiles = new int[WIDTH*WIDTH*2];
    private int [] colors = new int[WIDTH*WIDTH*4];
    private int [] databits = new int[WIDTH*WIDTH];
    public final int w,h;
    private SpriteSheet sheet;
    public Screen(int w,int h,SpriteSheet sheet) {
        this.h=h;
        this.w=w;
        this.sheet=sheet;
        for(int i=0;i<WIDTH*WIDTH;i++) {
            colors[i*4] = 0x0f0fff ;
            //0xffff00  0x0f0fff pink
            colors[i*4+1] = 0x228B22;
            colors[i*4+2] = 0xffff00;
            colors[i*4+3] = 0x0f0fff;
        }
    }
    public void render(int[] pixels,int offs,int rows){
        for(int yt=yScroll>>3;yt<=(yScroll+h)>>3;yt++)
        {
            int y0=yt*8-yScroll;
            int y1=y0+8;
            if(y0<0) y0=0;
            if(y1>h) y1=h;
            for(int xt=xScroll >> 3 ;xt<=(xScroll+w) >>3;xt++){
                int x0=xt*8 - xScroll;
                int x1=x0+8;
                if(x0<0) x0=0;
                if(x1>h) x1=w;
                int titleIdx=(xt & (MAP_WIDTH_MASK) )+ (yt & (MAP_WIDTH_MASK))*WIDTH;
                for(int y=y0;y<y1;y++){
                    int sp=((y+yScroll)&7)* sheet.width + ((x0+xScroll)&7);//sourcepointer
                    int tp=offs+x0+y*rows;//like a target pointer to keep track of  pixels
                    for(int x=x0;x<x1;x++){
                        int col = titleIdx * 4 + sheet.pixels[sp++];
                        pixels[tp++] = colors[col];
                    }
                }
            }
        }
    }
}
