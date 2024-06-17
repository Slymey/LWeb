#version 330 core
bool betweeni(float t, float a, float b){
    return t>=a&&t<=b || t>=b&&t<=a;
}
bool linem(vec2 uv, vec2 p1, vec2 p2, float t, float asp, bool mask){
    float a = p2.x-p1.x;
    float b = p1.y-p2.y;
    float k = -a/b;
    if(isinf(k)){
        if(abs(uv.y-p1.y)<t && betweeni(uv.x, p1.x, p2.x)){
            return true;
        }
    }else if(k==0.0){
        if(abs(uv.x-p1.x)<t && betweeni(uv.y, p1.y, p2.y)){
            return true;
        }
    }else if(abs(k*asp)>=1.0){
        float y = (uv.x-p1.x)/k;
        float x = uv.y-p1.y;
        float l = (uv.x-p1.x)/a;
        if(abs(y-x)<t && betweeni(l, 0.0, 1.0)){
            return true;
        }
    }else{
        float y = (uv.y-p1.y)*k;
        float x = uv.x-p1.x;
        float l = -(uv.y-p1.y)/b;
        if(abs(y-x)<t/asp && betweeni(l, 0.0, 1.0)){
            return true;
        }
    }
    return mask;
}

bool inbox(vec2 p, vec4 box){
    p = (p-box.xy)/box.zw; 
    if(p.x>=0.0&&p.x<=1.0&&p.y>=0.0&&p.y<=1.0)return true; 
    return false; 
}
bool abvline(vec2 uv, vec2 a, vec2 b){vec2 t = uv-a;a-=b;t.x/=(a.x/a.y);return sign(b.y)*t.x<sign(b.x)*t.y;}
vec2 intersection(vec2 p1, vec2 p2, vec2 p3, vec2 p4){
    float A1 = p2.y - p1.y;
    float B1 = p1.x - p2.x;
    float C1 = A1 * p1.x + B1 * p1.y;

    float A2 = p4.y - p3.y;
    float B2 = p3.x - p4.x;
    float C2 = A2 * p3.x + B2 * p3.y;

    float determinant = A1 * B2 - A2 * B1;

    vec2 p = vec2(0.0);

    p.x = (C1 * B2 - C2 * B1) / determinant;
    p.y = (A1 * C2 - A2 * C1) / determinant;
    
    return p;
}
bool qringm(vec2 uv, vec2 cor, vec2 box, vec2 pos, vec4 arc, float asp, bool mask){
    bool def = mask;
    float aspr = abs((arc.y/arc.x));
    vec2 p = vec2((uv.x - pos.x)*aspr, uv.y - pos.y);
    float aspi = abs((arc.w/arc.z));
    vec2 pi = vec2((uv.x - pos.x)*aspi, uv.y - pos.y);
    
    if(inbox(uv, vec4(box, pos - box + arc.xy)) || inbox(uv, vec4(pos, arc.xy))){
        if(inbox(uv, vec4(pos, arc.xy))
            &&p.x*p.x+p.y*p.y<arc.y*arc.y
            &&!(pi.x*pi.x+pi.y*pi.y<arc.w*arc.w)
        ){
            def = true;
        }else{
            if((inbox(uv, vec4(box.x, pos.y+arc.w, pos.x-box.x, arc.y-arc.w))
               ||inbox(uv, vec4(pos.x + arc.z, box.y, arc.x-arc.z, pos.y-box.y))
               ||inbox(uv, vec4(box.x, box.y, pos.x + arc.z-box.x, pos.y+arc.w-box.y)))
               &&!inbox(uv, vec4(box.x, box.y, cor-box))){
                def = true;
            }
            
        }
        
    }
    return def;
}

vec2 p(vec2 a){if(a.x<=0.0){a.x = 0.0;}if(a.y<=0.0){a.y = 0.0;} return a;}
vec2 p(vec2 a, vec2 r, vec2 q){if(a.x<=0.0){q.x = r.x;}if(a.y<=0.0){q.y = r.y;} return q;}

vec4 border(vec2 uv, vec4 box, float wt, float wr, float wb, float wl, vec2 bss, vec2 bse, vec2 bes, vec2 bee, vec4 ct, vec4 cr, vec4 cb, vec4 cl, float asp, vec4 def){
    wr /= asp;
    wl /= asp;
    bss.x /= asp;
    bse.x /= asp;
    bes.x /= asp;
    bee.x /= asp;
    box.xz /= asp;
    
    vec4 norm = vec4(bss.x + bse.x,
                     bse.y + bee.y,
                     bee.x + bes.x,
                     bes.y + bss.y);
    vec2 base = vec2(box.z + wl + wr,
                     box.w + wt + wb);
    
    box.zw += box.xy; 
    
    bool mask = false;
    
    vec2 a1 = box.xw;
    vec2 a2 = box.zw;
    vec2 a3 = box.xy;
    vec2 a4 = box.zy;
    
    vec2 b1 = vec2(box.x-wl,box.w+wt);//corner
    vec2 b2 = vec2(box.z+wr,box.w+wt);//corner to arc outer radious
    vec2 b3 = vec2(box.x-wl,box.y-wb);
    vec2 b4 = vec2(box.z+wr,box.y-wb);
    
    bss = p(base - norm.xw, bss*base/norm.xw, bss);
    bse = p(base - norm.xy, bse*base/norm.xy, bse);
    bes = p(base - norm.zw, bes*base/norm.zw, bes);
    bee = p(base - norm.zy, bee*base/norm.zy, bee);
    
    mask = qringm(uv,//#1
                a1,
           b1 - vec2(-max(bss.x, wl), max(bss.y, wt)),
           vec2(b1.x+bss.x, b1.y-bss.y),//pos between inner and outer radius
           vec4(-bss.x, bss.y, p(bss-vec2(wl, wt))),//outer , inner radius
                asp, mask
                );
    mask = qringm(uv,//#2
                a2,
           b2 - vec2(max(bse.x, wr), max(bse.y, wt)),
           vec2(b2.x-bse.x, b2.y-bse.y),//pos between inner and outer radius
           vec4(bse.x, bse.y, p(bse-vec2(wr, wt))),//outer , inner radius
                asp, mask
                );
    mask = qringm(uv,//#3
                a3,
           b3 - vec2(-max(bes.x, wl), -max(bes.y, wb)),
           vec2(b3.x+bes.x, b3.y+bes.y),//pos between inner and outer radius
           vec4(-bes.x, -bes.y, p(bes-vec2(wl, wb))),//outer , inner radius
                asp, mask
                );
    mask = qringm(uv,//#4
                a4,
           b4 - vec2(max(bee.x, wr), -max(bee.y, wb)),
           vec2(b4.x-bee.x, b4.y+bee.y),//pos between inner and outer radius
           vec4(bee.x, -bee.y, p(bee-vec2(wr, wb))),//outer , inner radius
                asp, mask
                );
    
    mask = linem(uv, 
                vec2(b1.x+bss.x, box.w + wt/2.0), 
                vec2(b2.x-bse.x, box.w + wt/2.0), 
                wt/2.0, 1.0, mask);
    mask = linem(uv, 
                vec2(box.z + wr/2.0, b2.y-bse.y), 
                vec2(box.z + wr/2.0, b4.y+bee.y), 
                wr/2.0, 1.0, mask);        
    mask = linem(uv, 
                vec2(b3.x+bes.x, box.y - wb/2.0), 
                vec2(b4.x-bee.x, box.y - wb/2.0), 
                wb/2.0, 1.0, mask);
    mask = linem(uv, 
                vec2(box.x - wl/2.0, b1.y-bss.y), 
                vec2(box.x - wl/2.0, b3.y+bes.y), 
                wl/2.0, 1.0, mask);
    
    vec2 p1 = intersection(a1,b1,a3,b3); 
    vec2 p2 = intersection(a3,b3,a4,b4); 
    vec2 cc = vec2(p2.x, p1.y);
    if(mask){
        if(abvline(uv, a1, b1)&&abvline(uv, a2, b2)&&uv.y>cc.y){def = ct;}
        if(!abvline(uv, a2, b2)&&abvline(uv, a4, b4)&&uv.x>cc.x){def = cr;}
        if(!abvline(uv, a3, b3)&&!abvline(uv, a4, b4)&&uv.y<cc.y){def = cb;}
        if(abvline(uv, a3, b3)&&!abvline(uv, a1, b1)&&uv.x<cc.x){def = cl;}
    }
    
    /*
    def = circle(uv, vec2(0.8-0.3, 0.2+0.4), 0.01, asp, vec4(0.5,0.5,0.5,1), def);
    def = circle(uv, vec2(0.8-0.15, 0.2+0.15), 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, vec2(0.8, 0.2), 0.01, asp, vec4(0,0,0,1), def);
    
    
    //def = circle(uv, vec2(box.x, pos.y+arc.w), 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    def = circle(uv, cc, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    def = circle(uv, cc, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    def = circle(uv, cc, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    *//*
     if(inbox(uv, vec4(box.x, pos.y+arc.w, pos.x-box.x, arc.y-arc.w))
               ||inbox(uv, vec4(pos.x + arc.z, box.y, arc.x-arc.z, pos.y-box.y))){
                def = true;
            }
    */
    /*
    //debug
    def = circle(uv, a1, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, b1, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, a2, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, b2, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, a3, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, b3, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, a4, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    def = circle(uv, b4, 0.01, asp, vec4(1.0,0.5,0.0,1.0), def);
    //def = circle(uv, c1, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    //def = circle(uv, c2, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    def = circle(uv, cc, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    def = circle(uv, p1, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    def = circle(uv, p2, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    //def = circle(uv, p34, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    //def = circle(uv, p41, 0.01, asp, vec4(1.0,0.0,0.5,1.0), def);
    
    
    def = line1(uv, a1, b1, 0.0012, asp, vec4(0), def);
    def = line1(uv, a2, b2, 0.0012, asp, vec4(0), def);
    def = line1(uv, a3, b3, 0.0012, asp, vec4(1), def);
    def = line1(uv, a4, b4, 0.0012, asp, vec4(0), def);
    
    
    //def += debugFloat(uv - vec2(0.1, 0.1), k1, 3);
    
    
    
    def = circle(uv, vec2(box.x-bss.x,box.w+bss.y), 0.01, asp, vec4(0.0,0.5,1.0,1.0), def);
    def = circle(uv, vec2(box.z+bse.x,box.w+bse.y), 0.01, asp, vec4(0.0,0.5,1.0,1.0), def);
    def = circle(uv, vec2(box.x-bes.x,box.y-bes.y), 0.01, asp, vec4(0.0,0.5,1.0,1.0), def);
    def = circle(uv, vec2(box.z+bee.x,box.y-bee.y), 0.01, asp, vec4(0.0,0.5,1.0,1.0), def);
    
    def = circle(uv, box.xy, 0.01, asp, vec4(0,0,0,1), def);
    def = circle(uv, box.xw, 0.01, asp, vec4(0,0,0,1), def);
    def = circle(uv, box.zw, 0.01, asp, vec4(0,0,0,1), def);
    def = circle(uv, box.zy, 0.01, asp, vec4(0,0,0,1), def);
    
    def = circle(uv, b1, 0.01, asp, vec4(0.5,0.5,0.5,1), def);
    def = circle(uv, b2, 0.01, asp, vec4(0.5,0.5,0.5,1), def);
    def = circle(uv, b3, 0.01, asp, vec4(0.5,0.5,0.5,1), def);
    def = circle(uv, b4, 0.01, asp, vec4(0.5,0.5,0.5,1), def);
    
    def = circle(uv, b2 - max(bse, vec2(wr, wt)), 0.01, asp, vec4(0.5,0.0,0.5,1), def);
    */
    
    return def;
}


out vec4 FragColor;
  
in vec2 TexCoords;

uniform sampler2D tex;
uniform vec4 box;
uniform vec2 bss = vec2(0);
uniform vec2 bse = vec2(0);
uniform vec2 bes = vec2(0);
uniform vec2 bee = vec2(0);
uniform float wt=0.0;
uniform float wr=0.0;
uniform float wb=0.0;
uniform float wl=0.0;
uniform float asp=1.0;
uniform vec4 ct = vec4(0.0,0.0,0.0,1.0); 
uniform vec4 cr = vec4(0.0,0.0,0.0,1.0); 
uniform vec4 cb = vec4(0.0,0.0,0.0,1.0); 
uniform vec4 cl = vec4(0.0,0.0,0.0,1.0); 

void main(){
    vec4 col = vec4(0);
    if(inbox(TexCoords, box)){
        col = texture(tex, (TexCoords - box.xy)/box.zw);
    }
    col = border(TexCoords, 
                 box,
                 wt, wr, wb, wl,
                 bss, bse, bes, bee,
                 ct, cr, cb, cl,
                 asp, col);
    
    
    
    

    // Output to screen
    FragColor = col;
}