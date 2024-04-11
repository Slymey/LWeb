package Engine.Util;

import Common.Color;
import java.util.function.BiFunction;


public enum ColorMixers{
    NORMAL((Color c1, Color c2) -> {
        int aa=c1.A;
        int ab=c1.R;
        int ac=c1.G;
        int ad=c1.B;

        int ba=c2.A;
        int bb=c2.R;
        int bc=c2.G;
        int bd=c2.B;

        int oa=(aa+ba*(255-aa)/255);
        int or=(ab*aa+bb*ba*(255-aa)/255)/oa;
        int og=(ac*aa+bc*ba*(255-aa)/255)/oa;
        int ob=(ad*aa+bd*ba*(255-aa)/255)/oa;
        return  new Color(oa, or,og,ob);
    }),
    MULTIPLY((Color c1, Color c2)->{
        return new Color(
                        c1.A*c2.A/255,
                        c1.R*c2.R/255,
                        c1.G*c2.G/255,
                        c1.B*c2.B/255
        );
    });

    public final BiFunction<Color,Color,Color> func;
    private ColorMixers(BiFunction<Color,Color,Color> func){this.func = func;}
}
