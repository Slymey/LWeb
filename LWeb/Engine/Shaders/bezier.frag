#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform vec2 p0;
uniform vec2 p1;
uniform vec2 p2;
uniform float width=0.004;
uniform float asp=1.0;
uniform float weight=1.0;
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
void main()
{ 
    vec4 col = vec4(0.0,0.0,0.0,0.0);
    vec2 pp = p0;
    for(int i=0;i<rez;i++){
        float tt = (float(i)+1.0)/float(rez);
        //vec2 np = lerp3(p0,p1,p2,tt);
        float B0 = (1.0 - tt)*(1.0 - tt);
        float B1 = 2.0 * tt * (1.0 - tt) * weight;
        float B2 = tt*tt;
        float denominator = B0 + B1 + B2;
        vec2 np = (B0 * p0 + B1 * p1 + B2 * p2) / denominator;
        col= line(TexCoords, pp, np, width, asp, color, col);
        pp=np;
    }
    //col = circle(TexCoords, p0, 0.01, asp, blu, col);
    //col = circle(TexCoords, p1, 0.01, asp, blu, col);
    //col = circle(TexCoords, p2, 0.01, asp, blu, col);

    FragColor = col;
}