package LWeb.Common;


public class ByteCounter {
    public byte []o;
    public int c;
    public int l;
    public boolean lc;
    public boolean ended=false;
    public ByteCounter(byte [] b, int c){
        this.o=b;
        this.c=c;
    }
    public void lock(){
        l=c;
        lc=true;
    }
    public void unlock(){
        c=l;
        lc=false;
    }
    public byte next(){
        return o[c++];
    }
    
    public int inc(){
        return c++;
    }
    public int incp(){
        return ++c;
    }
    public int dec(){
        return c--;
    }
    public int decp(){
        return --c;
    }

    public int inc(int c) {
        int ct=this.c;
        this.c+=c;
        return ct;
    }
    public int incp(int c) {
        this.c+=c;
        return this.c;
    }
    public int dec(int c) {
        int ct=this.c;
        this.c-=c;
        return ct;
    }
    public int decp(int c) {
        this.c-=c;
        return this.c;
    }
}
