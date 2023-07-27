package com.example.chapter21.engine.graph;

import org.joml.Matrix4f;
import com.example.chapter21.engine.scene.Entity;

import java.util.*;

public class Model {

    private final String id;
    private List<Animation> animationList;
    private List<Entity> entitiesList;
    private List<MeshData> meshDataList;
    private List<RenderBuffers.MeshDrawData> meshDrawDataList;

    public Model(String id, List<MeshData> meshDataList, List<Animation> animationList) {
        entitiesList = new ArrayList<>();
        this.id = id;
        this.meshDataList = meshDataList;
        this.animationList = animationList;
        meshDrawDataList = new ArrayList<>();
    }

    public List<Animation> getAnimationList() {
        return animationList;
    }

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public String getId() {
        return id;
    }

    public List<MeshData> getMeshDataList() {
        return meshDataList;
    }

    public List<RenderBuffers.MeshDrawData> getMeshDrawDataList() {
        return meshDrawDataList;
    }

    public boolean isAnimated() {
        return animationList != null && !animationList.isEmpty();
    }

    public static class AnimatedFrame {
        private Matrix4f[] bonesMatrices;
        private int offset;

        public AnimatedFrame(Matrix4f[] bonesMatrices) {
            this.bonesMatrices = bonesMatrices;
        }

        public void clearData() {
            bonesMatrices = null;
        }

        public Matrix4f[] getBonesMatrices() {
            return bonesMatrices;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
    }

    public static class Animation {

        public String name;
        public double duration;
        public List<AnimatedFrame> frames;
        public Animation(String name, double duration, List<AnimatedFrame> frames){
            this.name = name;
            this.duration = duration;
            this.frames = frames;
        }
    }

}