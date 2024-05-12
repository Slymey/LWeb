package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import LWeb.Engine.Util.GLEU.IntLineTexture;

import static LWeb.Common.Common.*;

public class Curve {
    public int x;
    public int y;
    public IntLineTexture tex;
    public int pts[];
    public int length;

    /**
     *
     * @param points n*4 of points, n=1,2,3,...
     */
    public static ResourceInst.RByteCol getBytes(int p0x, int p0y, int... points){
        int len = points.length/4;
        return new ResourceInst.RByteCol(23,  itb(len), itb(p0x), itb(p0y), istb(points));
    }
    public static Object getRsc(ByteCounter i, Core c){
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int x = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int y = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int pt[] = new int[length*4];
        for (int j=0; j<length;j++){
            pt[j * 4] = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            pt[j*4+1] = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            pt[j*4+2] = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            pt[j*4+3] = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        }

        return new Curve(x, y, pt);
    }
    public Curve(int x, int y, int[] pt) {
        this.x=x;
        this.y=y;
        length=pt.length;
        this.pts=pt;

    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getLen(){
        return length;
    }
    public IntLineTexture getTex(){
        if(tex==null){
            tex = new IntLineTexture()
                    .setImage(length, pts);
        }
        return tex;
    }

}
