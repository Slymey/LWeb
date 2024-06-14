package LWeb.Compiler.Components;

import LWeb.Common.Common;
import static LWeb.Common.Common.ats;
import static LWeb.Common.Common.itb;
import java.util.ArrayList;


public class AsmGroup{
    public ArrayList<Instr> inits = new ArrayList<>();
    public ArrayList<Instr> runs = new ArrayList<>();
    public ArrayList<Instr> enders = new ArrayList<>();
    public int[] inind;
    public int[] outind;
    public int parBuff;
    
    
    public static class Instr{
        public byte[] bytes;
        public int[] ioints;
        public int[] iopos;
        
        public Instr(byte[] b, int[] iop, int[] ios){
            bytes=b;
            ioints=iop;
            iopos=ios;
        }
        
        public void replace(int ind, int newi){
            int i = Common.inList(ind, ioints);
            for(int d = 0; d<ioints.length&&i!=-1; d+=i,i = Common.inList(ind, ioints, d,ioints.length)){
                if(i!=-1){
                    int p = iopos[i];
                    byte n[] = itb(newi);
                    bytes[p]=n[0];
                    bytes[p+1]=n[1];
                    bytes[p+2]=n[2];
                    bytes[p+3]=n[3];
                    ioints[i]=newi;
                }
            }
        }
        public String toString(){
            return ats(bytes)+"+"+ats(ioints)+"+"+ats(iopos);
        }
    }
    public String toString(){
        return "inits:"+(inits)+", runs"+(runs)+", ends"+(enders);
    }
}
