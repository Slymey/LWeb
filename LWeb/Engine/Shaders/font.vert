#version 330 core
layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aTexCoord;

out vec4 fontColor;
out vec2 TexCoord;
out vec2 bPos;
out vec4 paintBox;

uniform vec4 drawBox;
uniform vec4 aColor;

void main()
{
    gl_Position = vec4(aPos, 0.0f, 1.0f);
    TexCoord = aTexCoord;
    fontColor = aColor;
    vec2 bPos = aPos-drawBox.xy;
    paintBox=drawBox;
} 