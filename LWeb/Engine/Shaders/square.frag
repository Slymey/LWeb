#version 330 core

vec2 Square(in vec4 box, in vec2 texCoords){
    vec2 c2 = box.xy+box.zw;
    if(texCoords.x < box.x || texCoords.y < box.y || texCoords.x > c2.x || texCoords.y > c2.y){
        discard;
    }
    return (texCoords-box.xy);
}