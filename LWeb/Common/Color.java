
package LWeb.Common;

import static LWeb.Common.Common.inList;
import static LWeb.Common.Common.intToByte;
import static LWeb.Common.Common.lognm;
import org.joml.Vector4f;

public interface Color {
    final static char[] hex={'F','E','D','C','B','A','9','8','7','6','5','4','3','2','1','0','f','e','d','c','b','a'};
    public float af();
    public float rf();
    public float gf();
    public float bf();
    public int ai();
    public int ri();
    public int gi();
    public int bi();
    public Vector4f asVec();
    public static Color avg(Color... c){
        int a=0;
        int r=0;
        int g=0;
        int b=0;
        for (Color cl:c) {
            a+=cl.af();
            r+=cl.rf();
            g+=cl.gf();
            b+=cl.bf();
        }
        return new IntColor(a/c.length,r/c.length,g/c.length,b/c.length);
    }
    public static Color avg(int... c){
        int a=0;
        int r=0;
        int g=0;
        int b=0;
        byte ba[]=new byte[4];
        for (int cl:c) {
            ba=intToByte(cl,ba);
            a+=ba[3];
            r+=ba[2];
            g+=ba[1];
            b+=ba[0];
        }
        return new IntColor(a/c.length,r/c.length,g/c.length,b/c.length);
    }
    public static Color color(int c){
        return new IntColor(c);
    }
    public static Color Color(int c){
        return new IntColor(c);
    }
    public static Color Color(String s){
        Color c=Color(0);
        int n1=0;
        int n2=0;
        int n3=0;
        int n4=0;
        int n5=0;
        int n6=0;
        int n7=0;
        int n8=0;
        if(s.charAt(0)!='#')return c;
        switch(s.length()){
            case 4:
                n1=inList(s.charAt(1),hex);
                n2=inList(s.charAt(2),hex);
                n3=inList(s.charAt(3),hex);
                if(n1==-1||n2==-1||n3==-1)return c;
                c = new IntColor(255, (15-n1&0xf)*16+(15-n1&0xf), (15-n2&0xf)*16+(15-n2&0xf), (15-n3&0xf)*16+(15-n3&0xf));
                break;
            case 5:
                n1=inList(s.charAt(1),hex);
                n2=inList(s.charAt(2),hex);
                n3=inList(s.charAt(3),hex);
                n4=inList(s.charAt(4),hex);
                if(n1==-1||n2==-1||n3==-1||n4==-1)return c;
                c = new IntColor((15-n4&0xf)*16+(15-n4&0xf), (15-n1&0xf)*16+(15-n1&0xf), (15-n2&0xf)*16+(15-n2&0xf), (15-n3&0xf)*16+(15-n3&0xf));
                break;
            case 7:
                n1=inList(s.charAt(1),hex);
                n2=inList(s.charAt(2),hex);
                n3=inList(s.charAt(3),hex);
                n4=inList(s.charAt(4),hex);
                n5=inList(s.charAt(5),hex);
                n6=inList(s.charAt(6),hex);
                if(n1==-1||n2==-1||n3==-1||n4==-1||n5==-1||n6==-1)return c;
                c = new IntColor(255, 
                        (15-n1&0xf)*16+(15-n2&0xf), 
                        (15-n3&0xf)*16+(15-n4&0xf), 
                        (15-n5&0xf)*16+(15-n6&0xf));
                break;
            case 9:
                n1=inList(s.charAt(1),hex);
                n2=inList(s.charAt(2),hex);
                n3=inList(s.charAt(3),hex);
                n4=inList(s.charAt(4),hex);
                n5=inList(s.charAt(5),hex);
                n6=inList(s.charAt(6),hex);
                n7=inList(s.charAt(7),hex);
                n8=inList(s.charAt(8),hex);
                if(n1==-1||n2==-1||n3==-1||n4==-1||n5==-1||n6==-1||n7==-1||n8==-1)return c;
                c = new IntColor((15-n6&0xf)*16+(15-n8&0xf), 
                        (15-n1&0xf)*16+(15-n2&0xf), 
                        (15-n3&0xf)*16+(15-n4&0xf), 
                        (15-n5&0xf)*16+(15-n6&0xf));
                break;
        }
        return c;
    }
    public static class IntColor implements Color{
        public int A;
        public int R;
        public int G;
        public int B;
        public IntColor(){
            A=0;
            R=0;
            G=0;
            B=0;
        }
        public IntColor(int color){
            A=color>>>(3*8)&0xff;
            R=color>>>(2*8)&0xff;
            G=color>>>(1*8)&0xff;
            B=color>>>(0*8)&0xff;
        }
        public IntColor(int a, int r, int g,int b){
            A=a&0xff;
            R=r&0xff;
            G=g&0xff;
            B=b&0xff;
        }
        public IntColor(byte c[]){
            A=c[0]&0xff;
            R=c[1]&0xff;
            G=c[2]&0xff;
            B=c[3]&0xff;
        }
        public int color(){
            return A<<(3*8)|
                   R<<(2*8)|
                   G<<(1*8)|
                   B;
        }
        public void set(int color){
            A=color>>>(3*8)&0xff;
            R=color>>>(2*8)&0xff;
            G=color>>>(1*8)&0xff;
            B=color>>>(0*8)&0xff;
        }
        public Vector4f asVec(){
            return new Vector4f(R/255.0f, G/255.0f, B/255.0f, A/255.0f);
        }
        public String toString(){
            return "("+R+", "+G+", "+B+", "+A+")";
        }
        public float af(){return A/255.0f;}
        public float rf(){return R/255.0f;}
        public float gf(){return G/255.0f;}
        public float bf(){return B/255.0f;}
        public int ai(){return A;}
        public int ri(){return R;}
        public int gi(){return G;}
        public int bi(){return B;}
    }
    
    public static class FloatColor implements Color{
        public float A;
        public float R;
        public float G;
        public float B;
        public FloatColor(float a, float r, float g,float b){
            A=a;
            R=r;
            G=g;
            B=b;
        }
        public FloatColor(float c[]){
            A=c[0];
            R=c[1];
            G=c[2];
            B=c[3];
        }

        
        public float af(){return A;}
        public float rf(){return R;}
        public float gf(){return G;}
        public float bf(){return B;}
        public int ai(){return (int)(A*255);}
        public int ri(){return (int)(R*255);}
        public int gi(){return (int)(G*255);}
        public int bi(){return (int)(B*255);}
        public Vector4f asVec(){
            return new Vector4f(R, G, B, A);
        }
        
    }
}
