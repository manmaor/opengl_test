#version 400 core

//in vec3 colour;
in vec2 frgTextureCoords;
in vec4 frgColor;

uniform sampler2D textureSampler;

out vec4 out_Color;

void main(void) {
    out_Color = frgColor * texture(textureSampler, frgTextureCoords);
}