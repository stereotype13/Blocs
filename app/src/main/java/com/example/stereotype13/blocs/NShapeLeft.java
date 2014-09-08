package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/28/14.
 */
public class NShapeLeft extends Block {
    public NShapeLeft() {
        int[][] first = new int[][]{
                {0,1},
                {1,1},
                {1,0}};

        int[][] second = new int[][]{
                {1,1,0},
                {0,1,1},
                };


        this.addConfiguration(first);
        this.addConfiguration(second);

        //Lime Green
        this.setColor(new float[]{0.2f, 0.8f, 0.2f});
    }
}
