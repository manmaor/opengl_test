#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

//out vec3 colour;

void main(void) {
    gl_Position = vec4(position.xyz, 1.0);
//    colour = vec3(position.x+0.5, 1.0, position.y+0.5);
    pass_textureCoords = textureCoords;
}