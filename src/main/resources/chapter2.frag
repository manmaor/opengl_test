#version 400 core

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;
const float SPECULAR_POWER = 10;

// These structs coresponds to the engine/scene/lights
struct Material {
    vec4 ambient;
    vec4 specular;
    float reflectance;
};
struct Attenuation {
    float constant;
    float linear;
    float exponent;
};
struct AmbientLight {
    float factor;
    vec3 color;
};
struct PointLight {
    vec3 position;
    vec3 color;
    float intensity;
    Attenuation att;
};
struct SpotLight {
    PointLight pl;
    vec3 conedir;
    float cutoff;
};
struct DirLight {
    vec3 color;
    vec3 direction;
    float intensity;
};

in vec3 pass_position;
in vec3 pass_normal;
in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform Material material;
uniform AmbientLight ambientLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
uniform DirLight dirLight;

uniform mat4 viewMatrix;

vec4 calcAmbient(AmbientLight ambientLight, vec4 ambient) {
    return vec4(ambientLight.factor * ambientLight.color, 1) * ambient;
}

vec4 calcLightColor(
    vec4 diffuse,
    vec4 specular,
    vec3 lightColor,
    float light_intencity,
    vec3 position,
    vec3 to_light_dir,
    vec3 normal) {

    vec4 diffuseColor = vec4(0, 0, 0, 1);
    vec4 specColor = vec4(0, 0, 0, 1);

    // Diffuse = (diffuseColor * lightColor * diffuseFactor * intencity)
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
    // diffuseColor = diffuse * vec4(lightColor, 1) * light_intencity * diffuseFactor;
    diffuseColor = min(1.0, diffuseFactor) * vec4(1);

    return (diffuseColor );
}

vec4 calcDirLight(vec4 diffuse, vec4 specular, DirLight light, vec3 position, vec3 normal) {
//    vec3 lightDirection = (vec4(light.direction, 1) /* * viewMatrix*/).xyz;
    vec3 light_direction = light.direction - position;
    vec3 to_light_dir  = normalize(light_direction);
    return calcLightColor(diffuse, specular, light.color, light.intensity, position, to_light_dir, normalize(normal));
}

void main(void) {
//    vec4 texture_color = texture(textureSampler, pass_textureCoords);
//    vec4 ambient = calcAmbient(ambientLight, texture_color + material.ambient);
//    vec4 diffuse = texture_color; // + material.diffuse;
//    vec4 specular = texture_color + material.specular;
//
//    vec4 diffuseSpecularColor = calcDirLight(diffuse, specular, dirLight, pass_position, pass_normal);

    vec3 to_light_vector = ((viewMatrix * vec4(dirLight.direction, 1)).xyz - pass_position);

    vec3 unitNormal = pass_normal;
    vec3 unitLight = normalize(to_light_vector);

    float nDotl = dot(unitNormal, unitLight);
    float b = max(nDotl, 0.0);

    vec3 d = vec3(b);
    out_Color = vec4(d, 1);
//    out_Color = diffuseSpecularColor;
//    out_Color = ambient + diffuseSpecularColor;
    // texture(textureSampler, pass_textureCoords); // vec4(colour, 1.0);
}
