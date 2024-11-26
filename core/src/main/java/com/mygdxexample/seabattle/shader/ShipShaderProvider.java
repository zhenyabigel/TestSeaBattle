package com.mygdxexample.seabattle.shader;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;

public class ShipShaderProvider extends BaseShaderProvider {
    @Override
    public Shader createShader(Renderable renderable) {
        // Create a new ShipShader instance for the given renderable
        return new ShipShader(renderable);
    }

}
