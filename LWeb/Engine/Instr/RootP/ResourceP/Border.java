package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Common.Color;
import LWeb.Engine.Core;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.istb;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class Border {
    Core c;
    public int wt;
    public int wr;
    public int wb;
    public int wl;
    
    public int ct;
    public int cr; 
    public int cb;
    public int cl;
     
    public int ssx;
    public int ssy;
    public int sex;
    public int sey;
    public int esx;
    public int esy;
    public int eex;
    public int eey;
    
    public Position p;
    public static ResourceInst.RByteCol getBytes(int wt, int wr, int wb, int wl, int Rct, int Rcr, int Rcb, int Rcl, int ssx, int ssy, int sex, int sey, int esx, int esy, int eex, int eey){
        return new ResourceInst.RByteCol(33, itb(wt), itb(wr), itb(wb), itb(wl), itb(Rct), itb(Rcr), itb(Rcb), itb(Rcl), itb(ssx), itb(ssy), itb(sex), itb(sey), itb(esx), itb(esy), itb(eex), itb(eey));
    }
    public static Object getRsc(ByteCounter i, Core c){
        int wt = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int wr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int wb = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int wl = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        int ct = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int cr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int cb = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int cl = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        int ssx = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int ssy = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int sex = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int sey = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int esx = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int esy = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int eex = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int eey = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        return new Border(c,wt, wr, wb, wl, ct, cr, cb, cl, ssx, ssy, sex, sey, esx, esy, eex, eey);
    }
    
    public Border(Core c, int wt, int wr, int wb, int wl, int ct, int cr, int cb, int cl, int ssx, int ssy, int sex, int sey, int esx, int esy, int eex, int eey){
        this.c=c;
        this.wt = wt;
        this.wr = wr;
        this.wb = wb;
        this.wl = wl;

        this.ct = ct;
        this.cr = cr;
        this.cb = cb;
        this.cl = cl;

        this.ssx = ssx;
        this.ssy = ssy;
        this.sex = sex;
        this.sey = sey;
        this.esx = esx;
        this.esy = esy;
        this.eex = eex;
        this.eey = eey;
    }
    
    public float wt(){
        return 1.0f*wt/p.yi();
    }
    public float wr(){
        return 1.0f*wr/p.xi();
    }
    public float wb(){
        return 1.0f*wb/p.yi();
    }
    public float wl(){
        return 1.0f*wl/p.xi();
    }

    public Color ct(){
        return c.getResource(ct, Color.class);
    }
    public Color cr(){
        return c.getResource(cr, Color.class);
    }
    public Color cb(){
        return c.getResource(cb, Color.class);
    }
    public Color cl(){
        return c.getResource(cl, Color.class);
    }

    public float ssx(){
        return 1.0f*ssx/p.xi();
    }
    public float ssy(){
        return 1.0f*ssy/p.yi();
    }
    public float sex(){
        return 1.0f*sex/p.xi();
    }
    public float sey(){
        return 1.0f*sey/p.yi();
    }
    public float esx(){
        return 1.0f*esx/p.xi();
    }
    public float esy(){
        return 1.0f*esy/p.yi();
    }
    public float eex(){
        return 1.0f*eex/p.xi();
    }
    public float eey(){
        return 1.0f*eey/p.yi();
    }

}
