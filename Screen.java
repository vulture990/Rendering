package com.vulture;
import java.util.ArrayList;
import  java.util.List;
import java.util.Map;

public class Screen {
    private  static  final int WIDTH=64;
    private  static  final int MAP_WIDTH_MASK=WIDTH - 1;
    private static   final int SPRITE_SHEET_SIZE=256;
    private List <Sprite> sprites = new ArrayList<Sprite>();
    private  int xScroll;
    private  int yScroll;
    private  int [] tiles = new int[WIDTH*WIDTH*2];
    private int [] colors = new int[WIDTH *WIDTH*3];
    private int [] databits = new int[WIDTH*WIDTH];
    public final int w,h;
    private SpriteSheet sheet;
    public Screen(int w,int h,SpriteSheet sheet) {
        this.h=h;
        this.w=w;
    }
    public void render(int[] pixels,int offs,int rows){
        for(int yt=yScroll>>3;yt<=(yScroll+8)>>3;yt++)
        {
            int y0=yt-yScroll;
            int y1=y0+8;
            if(y0<0) y0=0;
            if(y1>h) y1=h;
            for(int xt=xScroll >> 3 ;xt<=(xScroll+8) >>3;xt++){
                int x0=xt - xScroll;
                int x1=x0+8;
                if(x0<0) x0=0;
                if(x1>h) x1=h;
                int titleIdx=(xt & (MAP_WIDTH_MASK) + (yt & (MAP_WIDTH_MASK))*WIDTH
                for(int y=y0;y<y1;y++){
                    int sp=((y-yScroll)&7)* sheet.width + ((x0-xScroll)&7);//sourcepointer
                    int tp=offs+x0+y*rows;//target pointer
                    for(int x=x0;x<x1;x++){
                        //int xs=(x0-xScroll)&7;
                        pixels[tp++]=colors[titleIdx * 4 +sheet.pixels[sp++]];












                    }
                }
            }
        }
    }
}
