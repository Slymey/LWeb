package LWeb.Engine.Util;

import java.util.Arrays;
import java.util.Iterator;


public class SimpleRemoteThread implements Runnable{
    private final Object lock = new Object();
    private volatile boolean paused = false , stopped = false;
    private Iterator<Runnable> work;
    public SimpleRemoteThread(Runnable[] work){
        this.work = Arrays.asList(work).iterator();
    }
    @Override
    public void run() {
        while(!stopped){
            while (!paused&&work.hasNext()){
                work.next().run();
            }
            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public void pause(){
        paused = true;
    }
    public void stop(){
        paused = true;stopped = true;
        synchronized (lock){
            lock.notifyAll();
        }
    }

    public void resume(){
        paused = false;
        synchronized (lock){
            lock.notifyAll();
        }
    }

}
