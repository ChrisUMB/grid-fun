#version 330 core

uniform sampler2D uTexture;

out vec4 fColor;
in vec2 vTexCoord;

void main() {
    fColor = texture(uTexture, vTexCoord);
}