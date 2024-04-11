package LWeb.Common;


public class Counter {
    public int c;
    public boolean ended = false;
    public Counter(){
        c=0;
    }
    public Counter(int c){
        this.c=c;
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
