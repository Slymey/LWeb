
package LWeb.Common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.imageio.ImageIO;


public class ColorSource {
    int type=0;// 0=color 1=gradient 2=radial 3=conic 4=image 5=buffer
    Color c= null;
    BufferedImage img = null;
    int x=0;
    int y=0;
    public ColorSource(int c){
        this.c=new Color(c);
    }
    public ColorSource(byte[] c){
        this.c=new Color(c);
    }
    public ColorSource(URI imgFile){
        try{
            type = 4;
            img = ImageIO.read(imgFile.toURL());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public ColorSource(BufferedImage buffer){
        type=5;
        img=buffer;
    }
    public Color get(int x, int y){
        switch(type){
            case 0:
                return c;
            case 4:case 5:
                return new Color(img.getRGB((x+this.x)%img.getWidth(), (y+this.y)%img.getHeight()));
            default:
                return null;
        }
    }
    public BufferedImage getBuffer(){
        return img;
    }
}
