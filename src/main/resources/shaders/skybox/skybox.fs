#version 330 core

out vec3 fColor;
in vec3 vPos;

uniform samplerCube uSkybox;

void main() {
    fColor = mix(texture(uSkybox, normalize(vPos)).rgb, normalize(vPos) * .5 + .5, 0.5);
    //    fColor = ;
}