package LWeb.Engine.Util;

import LWeb.Common.Color;
import LWeb.Common.Color.IntColor;
import java.util.function.BiFunction;


public enum ColorMixers{
    NORMAL((Color c1, Color c2) -> {
        int aa=c1.ai();
        int ab=c1.ri();
        int ac=c1.gi();
        int ad=c1.bi();

        int ba=c2.ai();
        int bb=c2.ri();
        int bc=c2.gi();
        int bd=c2.bi();

        int oa=(aa+ba*(255-aa)/255);
        int or=(ab*aa+bb*ba*(255-aa)/255)/oa;
        int og=(ac*aa+bc*ba*(255-aa)/255)/oa;
        int ob=(ad*aa+bd*ba*(255-aa)/255)/oa;
        return  new IntColor(oa, or,og,ob);
    }),
    MULTIPLY((Color c1, Color c2)->{
        return new IntColor(
                        (int)(c1.ai()*c2.af()),
                        (int)(c1.ri()*c2.rf()),
                        (int)(c1.gi()*c2.gf()),
                        (int)(c1.bi()*c2.bf())
        );
    });

    public final BiFunction<Color,Color,Color> func;
    private ColorMixers(BiFunction<Color,Color,Color> func){this.func = func;}
}
