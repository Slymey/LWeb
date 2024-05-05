#version 330 core
out vec4 FragColor;
  
in vec2 TexCoords;

uniform vec4 pos = vec4(1.0, 1.0, 0.0, 0.0);
uniform vec4 red = vec4(1.0, 0.0, 0.0, 0.0);
uniform vec4 green = vec4(1.0, 0.0, 0.0, 0.0);
uniform vec4 blue = vec4(1.0, 0.0, 0.0, 0.0);
uniform vec4 alpha = vec4(1.0, 0.0, 0.0, 0.0);
uniform vec4 box = vec4(0.0, 0.0, 1.0, 1.0);

const float TAU = 6.28318530718;

vec4 palette(float t, vec4 o, vec4 a, vec4 f, vec4 p){
    //interpolation, dc offset, amplitude, frequency, phase
    return o + a * cos(TAU*(f*t + p));
}

void main()
{ 
    if(TexCoords.x < box.x || TexCoords.x > box.z || TexCoords.y < box.y || TexCoords.y > box.w){discard;}

    vec4 o = vec4(red.x, green.x, blue.x, alpha.x);
    vec4 a = vec4(red.y, green.y, blue.y, alpha.y);
    vec4 f = vec4(red.z, green.z, blue.z, alpha.z);
    vec4 p = vec4(red.w, green.w, blue.w, alpha.w);
    float lenD = length( pos.zw - pos.xy);
    float lenP = length(pos.xy - TexCoords );
    float t = clamp((lenP/lenD)*(dot(pos.zw-pos.xy, TexCoords-pos.xy)/(lenP*lenD)), 0.0, 1.0);

    FragColor = palette(t, o, a, f, p);
}
