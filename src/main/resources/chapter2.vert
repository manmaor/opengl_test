#version 400 core

// this requires glBindAttribLocation
//in vec3 position;
//in vec2 textureCoords;

layout(location=0) in vec3 position;
layout(location=1) in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

//out vec3 colour;

void main(void) {
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position.xyz, 1.0);
    //    colour = vec3(position.x+0.5, 1.0, position.y+0.5);
    pass_textureCoords = textureCoords;
}