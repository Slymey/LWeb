#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform vec2 p1;
uniform vec2 p2;
uniform float width=0.004;
uniform vec4 color = vec4(0.0,0.0,0.0,1.0); 
vec4 circle(vec2 uv, vec2 pos, float r, vec4 color1, vec4 color2){
    vec2 p = pos - uv;
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
    float t = width*2.0;
    vec2 c = TexCoords;
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
        col = color;
    }
    //col = circle(c, p1, 0.01, blu, col);
    //col = circle(c, p2, 0.01, blu, col);

    FragColor = col;
}