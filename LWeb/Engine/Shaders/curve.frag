#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform sampler1D points;
uniform int length;
uniform vec2 p0;
uniform vec2 scale;
uniform float width=0.004;
uniform int rez=25;
uniform vec4 color = vec4(0.0,0.0,0.0,1.0); 

vec4 line(vec2 c,vec2 p1, vec2 p2, float t, vec4 col1, vec4 col2){    
    float a = p2.x-p1.x;
    float b = p1.y-p2.y;
    float k = -b/a;
    float x = c.x-p1.x;
    float y = c.y-p1.y;
    float dh = x*k-y;
    float dv = y/k-x;
    if((dh<=t&&dh>=0.0||dv<=t&&dv>=0.0)
            &&(c.x>=p2.x-t||c.x>=p1.x-t)
            &&(c.y>=p2.y-t||c.y>=p1.y-t)
            &&(c.x<=p2.x||c.x<=p1.x)
            &&(c.y<=p2.y||c.y<=p1.y)
        ){
       return col1;
    }else{
       return col2;
    }
}
vec2 lerp3(vec2 a, vec2 b, vec2 c, float t){
    vec2 tsf = t*t*(a+c-2.0*b);
    vec2 tof = t*2.0*(b-a);
    vec2 tzf = a;
    return tsf + tof + tzf;
}
vec4 circle(vec2 uv, vec2 pos, float r, vec4 color1, vec4 color2){
    vec2 p = pos - uv;
    if(p.x*p.x+p.y*p.y<r*r){
        return color1;
    }else{
        return color2;
    }
}
vec4 bez(vec2 tx, vec2 p0, vec2 p1, vec2 p2, vec4 col1, vec4 col2){
    vec4 col = col2;
    vec2 pp = p0;
    for(int i=0;i<rez;i++){
        float tt = (float(i)+1.0)/float(rez);
        vec2 np = lerp3(p0,p1,p2,tt);
        col= line(tx, pp, np, width*2.0, col1, col2);
        pp=np;
    }
    return col;
}

vec4 blu = vec4(0.0,0.0,1.0,1.0);
void main()
{ 
    vec4 col = vec4(0.0,0.0,0.0,0.0);
    vec2 pp = p0;
    for(int i=0;i<length;i++){
        vec4 pg = texelFetch(points, i,0);
        col = bez(TexCoords, pp, pg.xy, pg.zw, color, col);
        pp=pg.zw;
    }
    //col = circle(TexCoords, p0, 0.01, blu, col);
    //col = circle(TexCoords, p1, 0.01, blu, col);
    //col = circle(TexCoords, p2, 0.01, blu, col);

    FragColor = col;
}