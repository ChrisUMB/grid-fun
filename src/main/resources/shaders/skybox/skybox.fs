#version 330 core

out vec3 fColor;
in vec3 vPos;

uniform samplerCube uSkybox;

void main() {
    fColor = texture(uSkybox, normalize(vPos)).rgb;
    //    fColor = normalize(vPos) * .5 + .5;
}