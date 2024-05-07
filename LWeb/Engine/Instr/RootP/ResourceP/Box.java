package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public abstract class Box {
    public abstract float xf();
    public abstract float yf();
    public abstract float zf();
    public abstract float wf();
    public abstract int xi();
    public abstract int yi();
    public abstract int zi();
    public abstract int wi();
    public Position p=null;
    public static class FloatBox extends Box{
        public static ResourceInst.RByteCol getBytes(float x, float y, float z, float w){
            return new ResourceInst.RByteCol(18, ftb(x), ftb(y), ftb(z), ftb(w));
        }
        float x;
        float y;
        float z;
        float w;
        public static Object getRsc(ByteCounter i, Core c){
            float x = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            float y = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            float z = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            float w = byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
            return new FloatBox(x, y, z, w);
        }
        public FloatBox(float x, float y, float z, float w){
            this.x=x;
            this.y=y;
            this.z=z;
            this.w=w;
        }

        public float xf(){return x;}
        public float yf(){return y;}
        public float zf(){return z;}
        public float wf(){return w;}
        public int xi(){if(p==null)return (int)(x*100); return (int)(p.xi()*x);}
        public int yi(){if(p==null)return (int)(y*100); return (int)(p.yi()*y);}
        public int zi(){if(p==null)return (int)(z*100); return (int)(p.xi()*z);}
        public int wi(){if(p==null)return (int)(w*100); return (int)(p.yi()*w);}
    }
    
    
    
    public static class IntBox extends Box{
        public static ResourceInst.RByteCol getBytes(int x, int y, int z, int w){
            return new ResourceInst.RByteCol(19, itb(x), itb(y), itb(z), itb(w));
        }
        
        int x;
        int y;
        int z;
        int w;

        public static Object getRsc(ByteCounter i, Core c){
            int x = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int y = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int z = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            int w = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
            return new IntBox(x, y, z, w);
        }

        public IntBox(int x, int y, int z, int w){
            this.x=x;
            this.y=y;
            this.z=z;
            this.w=w;
        }
        
        public int xi(){return x;}
        public int yi(){return y;}
        public int zi(){return z;}
        public int wi(){return w;}
        public float xf(){if(p==null)return x/100.0f; return x*1.0f/p.xi();}
        public float yf(){if(p==null)return y/100.0f; return y*1.0f/p.yi();}
        public float zf(){if(p==null)return z/100.0f; return z*1.0f/p.xi();}
        public float wf(){if(p==null)return w/100.0f; return w*1.0f/p.yi();}
    }
}
