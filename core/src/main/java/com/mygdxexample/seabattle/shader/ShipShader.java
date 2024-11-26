package com.mygdxexample.seabattle.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShipShader extends BaseShader {
    private ShaderProgram shaderProgram;

    public ShipShader(Renderable renderable) {
        String vertexShaderSource = Gdx.files.internal("shaders/vertexShips.glsl").readString();
        String fragmentShaderSource = Gdx.files.internal("shaders/fragmentShips.glsl").readString();

        shaderProgram = new ShaderProgram(vertexShaderSource, fragmentShaderSource);
        if (!shaderProgram.isCompiled()) {
            throw new IllegalArgumentException("Shader compilation failed: " + shaderProgram.getLog());
        }
    }

    @Override
    public void init() {

    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
        shaderProgram.bind(); // Bind the shader program
        shaderProgram.setUniformMatrix("u_projTrans", camera.combined); // Set the projection matrix
    }

    @Override
    public void render(Renderable renderable, Attributes combinedAttributes) {
        super.render(renderable, combinedAttributes);

        Material material = renderable.material;


        renderable.meshPart.render(shaderProgram);
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    public void dispose() {
        shaderProgram.dispose();
        super.dispose();
    }
}
