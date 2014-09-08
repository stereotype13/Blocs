package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/28/14.
 */
public class NShapeRight extends Block {
    public NShapeRight() {
        int[][] first = new int[][]{
                {1,0},
                {1,1},
                {0,1}};

        int[][] second = new int[][]{
                {0,1,1},
                {1,1,0},
                };


        this.addConfiguration(first);
        this.addConfiguration(second);

        //Yellow
        this.setColor(new float[]{1.0f, 1.0f, 0.0f});
    }
}
