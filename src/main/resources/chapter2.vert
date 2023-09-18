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

out vec3 to_light_vector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

void main(void) {
    mat4 modelViewMatrix = viewMatrix * modelMatrix;
    vec4 mvPosition = modelViewMatrix * vec4(position.xyz, 1.0);

    pass_position = (modelMatrix * vec4(position.xyz, 1.0)).xyz;
    pass_normal = normalize(modelMatrix * vec4(normal, 0.0)).xyz;
    pass_textureCoords = textureCoords;

    gl_Position = projectionMatrix * mvPosition;
}