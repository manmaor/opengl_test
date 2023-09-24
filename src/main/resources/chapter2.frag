#version 400 core

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;
const float SPECULAR_POWER = 10;

// These structs coresponds to the engine/scene/lights
struct Material {
    vec4 ambient;
    vec4 diffuse;
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


vec4 calcAmbient(AmbientLight ambientLight, vec4 ambient) {
    return vec4(ambientLight.factor * ambientLight.color, 1) * ambient;
}

vec4 calcLightColor(
    vec4 diffuse,
    vec4 specular,
    vec3 lightColor,
    float light_intensity,
    vec3 position,
    vec3 to_light_dir,
    vec3 normal) {

    vec4 diffuseColor = vec4(0, 0, 0, 1);
    vec4 specColor = vec4(0, 0, 0, 1);

    // Diffuse = (diffuseColor * lightColor * diffuseFactor * intencity)
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
     diffuseColor = diffuse * vec4(lightColor, 1) * light_intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflected_light = normalize(reflect(from_light_dir, normal));
    float specularFactor = max(dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, SPECULAR_POWER);
    specColor = specular * light_intensity * specularFactor * material.reflectance * vec4(lightColor, 1.0);

    return (diffuseColor + specColor);
}

vec4 calcDirLight(vec4 diffuse, vec4 specular, DirLight light, vec3 position, vec3 normal) {
    vec3 to_light_vector = normalize(light.direction - position);
    return calcLightColor(diffuse, specular, light.color, light.intensity, position, to_light_vector, normalize(normal));
}

void main(void) {
    vec4 texture_color = texture(textureSampler, pass_textureCoords);
    vec4 ambient = calcAmbient(ambientLight, texture_color * material.ambient); //
    vec4 diffuse = texture_color + material.diffuse;
    vec4 specular = material.specular;

    vec4 diffuseSpecularColor = calcDirLight(diffuse, specular, dirLight, pass_position, pass_normal);

    out_Color = ambient + diffuseSpecularColor;
    // texture(textureSampler, pass_textureCoords); // vec4(colour, 1.0);
}
