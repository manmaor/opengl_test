#version 400 core

// this requires glBindAttribLocation
//in vec3 position;
//in vec2 textureCoords;

layout(location=0) in vec2 position;
layout(location=1) in vec2 textureCoords;
layout(location=2) in vec4 color;

out vec2 frgTextureCoords;
out vec4 frgColor;

uniform vec2 scale;

void main(void) {
    frgTextureCoords = textureCoords;
    frgColor = color;

    gl_Position = vec4(position * scale + vec2(-1.0, 1.0), 0.0, 1.0);
}