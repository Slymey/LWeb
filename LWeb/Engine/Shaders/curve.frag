#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform usampler1D points;
uniform int length;
uniform vec2 p0;
uniform vec2 scale;
uniform float width=0.004;
uniform float asp=1.0;
uniform int rez=25;
uniform vec4 color = vec4(0.0,0.0,0.0,1.0); 

bool betweeni(float t, float a, float b){
    return t>=a&&t<=b || t>=b&&t<=a;
}
vec4 line(vec2 uv, vec2 p1, vec2 p2, float t, float asp, vec4 c1, vec4 c2){
    //uv = vec2((uv.x-0.5/iResolution.x), (uv.y-0.5/iResolution.y));
    //p1 = vec2(p1.x*asp, p1.y);
    //p2 = vec2(p2.x*asp, p2.y);
    //t = t*0.5;
    float a = p2.x-p1.x;
    float b = p1.y-p2.y;
    float k = -a/b;
    if(isinf(k)){
        if(abs(uv.y-p1.y)<t && betweeni(uv.x, p1.x, p2.x)){
            return c1;
        }
    }else if(k==0.0){
        if(abs(uv.x-p1.x)<t && betweeni(uv.y, p1.y, p2.y)){
            return c1;
        }
    }else if(abs(k*asp)>=1.0){
        float y = (uv.x-p1.x)/k;
        float x = uv.y-p1.y;
        float l = (uv.x-p1.x)/a;
        if(abs(y-x)<t && betweeni(l, 0.0, 1.0)){
            return c1;
        }
    }else{
        float y = (uv.y-p1.y)*k;
        float x = uv.x-p1.x;
        float l = -(uv.y-p1.y)/b;
        if(abs(y-x)<t/asp && betweeni(l, 0.0, 1.0)){
            return c1;
        }
    }
    return c2;
}
vec2 lerp3(vec2 a, vec2 b, vec2 c, float t){
    vec2 tsf = t*t*(a+c-2.0*b);
    vec2 tof = t*2.0*(b-a);
    vec2 tzf = a;
    return tsf + tof + tzf;
}
vec4 circle(vec2 uv, vec2 pos, float r, float asp, vec4 color1, vec4 color2){
    vec2 p = vec2((pos.x - uv.x)*asp, pos.y - uv.y);
    if(p.x*p.x+p.y*p.y<r*r){
        return color1;
    }else{
        return color2;
    }
}
vec4 blu = vec4(0.0,0.0,1.0,1.0);
vec4 red = vec4(1.0,0.0,0.0,1.0);
vec4 grn = vec4(0.0,1.0,0.0,1.0);
vec4 bez(vec2 tx, vec2 p0, vec2 p1, vec2 p2, float width, float asp, vec4 col1, vec4 col2){
    vec4 col = col2;
    vec2 pp = p0;
    for(int i=0;i<rez;i++){
        float tt = (float(i)+1.0)/float(rez);
        vec2 np = lerp3(p0,p1,p2,tt);
        //col = circle(tx, np, 0.01, asp, blu, col);
        col = line(tx, pp, np, width, asp, col1, col);
        pp=np;
    }
    return col;
}

void main()
{ 
    vec4 col = vec4(0.0,0.0,0.0,0.0);
    vec2 pp = p0/scale;
    //col = circle(TexCoords, pp, 0.01, asp, red, col);
    for(int i=0;i<length;i++){
        vec4 pg = vec4(texelFetch(points, i*4, 0).r,texelFetch(points, i*4+1,0).r,texelFetch(points, i*4+2,0).r,texelFetch(points, i*4+3,0).r)/vec4(scale,scale);
        //vec4 pg = vec4(texelFetch(points, i, 0).rgba)/vec4(scale,scale);
        //col = circle(TexCoords, pg.xy, 0.01, asp, grn, col);
        //col = circle(TexCoords, pg.zw, 0.01, asp, blu, col);
        //col = line(TexCoords, pg.xy, pg.zw, width, asp, color, col);
        //col = line(TexCoords, pp, pg.xy, width, asp, color, col);
        col = bez(TexCoords, pp, pg.xy, pg.zw, width, asp, color, col);
        pp=pg.zw;
    }
    //col = circle(TexCoords, p1, 0.01, blu, col);
    //col = circle(TexCoords, p2, 0.01, blu, col);

    FragColor = col;
}