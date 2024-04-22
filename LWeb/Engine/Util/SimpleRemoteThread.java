package LWeb.Engine.Util;

import java.util.Arrays;
import java.util.Iterator;


public class SimpleRemoteThread implements Runnable{
    public final Object lock = new Object();
    public volatile boolean paused = false , stopped = false;
    public SimpleRemoteThread(){
    }
    @Override
    public void run() {
        while(!stopped){
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
