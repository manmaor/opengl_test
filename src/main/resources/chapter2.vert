#version 400 core

// this requires glBindAttribLocation
//in vec3 position;
//in vec2 textureCoords;

layout(location=0) in vec3 position;
layout(location=1) in vec3 normal;
layout(location=2) in vec2 textureCoords;

out vec3 pass_position;
out vec3 pass_normal;
out vec2 pass_textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

void main(void) {
    mat4 modelViewMatrix = viewMatrix * modelMatrix;
    vec4 mvPosition = modelViewMatrix * vec4(position.xyz, 1.0);

    pass_position = (modelViewMatrix * vec4(position.xyz, 1.0)).xyz;
    pass_normal = transpose(inverse(mat3(modelViewMatrix))) * normal;
    pass_textureCoords = textureCoords;

    gl_Position = projectionMatrix * mvPosition;
}