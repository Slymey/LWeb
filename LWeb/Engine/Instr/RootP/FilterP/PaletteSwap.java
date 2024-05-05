package LWeb.Engine.Instr.RootP.FilterP;

import LWeb.Common.Color;
import LWeb.Common.Color.IntColor;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.Pair;
import LWeb.Engine.Core;
import LWeb.Common.Range.Range;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PaletteSwap {
    public static Runnable getInst(byte o[], Counter i, Core cr){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int index = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            IntColor cc = new IntColor();
            Pair<Integer, Integer> pair[]= cr.getResource(index, Pair[].class);
            BufferedImage bi=cr.getResource(id, BufferedImage.class);
            int prev = bi.getRGB(0, 0);
            final int prev_f=prev;
            int prevc = Arrays.binarySearch(pair, null, 
                               (Pair<Integer, Integer> c1, Pair<Integer, Integer> c2)->{
                return c1.getFirst()-prev_f;
            });
            //itterate every pixel and b-search colors
            if(pair.length<16){
                for(int bw:new Range(0,bi.getWidth()-1)){
                    for(int bh:new Range(0,bi.getHeight()-1)){
                        int c = bi.getRGB(bw, bh);
                        if(prev==c){
                            bi.setRGB(bw, bh, prevc);
                        }else{
                            int ind = Arrays.binarySearch(pair, null, 
                                       (Pair<Integer, Integer> c1, Pair<Integer, Integer> c2)->{
                                return c1.getFirst()-c;
                            });
                            if(ind>=0){
                                prev=c;
                                prevc=pair[ind].getSecond();
                                bi.setRGB(bw, bh, prevc);
                            }
                        }
                    }
                }
            }else{
                Pair<Integer, Integer> cache[] = new Pair[64];
                for(int bw:new Range(0,bi.getWidth()-1)){
                    for(int bh:new Range(0,bi.getHeight()-1)){
                        int c = bi.getRGB(bw, bh);
                        boolean in=false;//integrate prev
                        cc.set(c);
                        int indp = (cc.ri() * 3 + cc.gi() * 5 + cc.bi() * 7 + cc.ai() * 11)&0x40;
                        if(cache[indp]==null){in=true;}else if(cache[indp].getFirst()!=c){in=true;}
                        if(in){
                            int ind = Arrays.binarySearch(pair, null, 
                                   (Pair<Integer, Integer> c1, Pair<Integer, Integer> c2)->{
                                return c1.getFirst()-c;
                            });
                            if(ind>=0){
                                cache[indp]=pair[ind];
                                bi.setRGB(bw, bh, pair[ind].getSecond());
                            }
                        }else{
                            bi.setRGB(bw, bh, cache[indp].getSecond());
                        }
                    }
                }
            }
        }; 
    }
}
