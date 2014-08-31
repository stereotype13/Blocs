package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/28/14.
 */
public class LShapeRight extends Block {

    public LShapeRight() {


        int[][] first = new int[][]{{0,1,0},
                 {0,1,0},
                 {0,1,1}};

        int[][] second = new int[][]{{0,0,0},
                {1,1,1},
                {1,0,0}};

        int[][] third = new int[][]{{1,1,0},
                {0,1,0},
                {0,1,0}};

        int[][] fourth = new int[][]{{0,0,1},
                {1,1,1},
                {0,0,0}};


        this.addConfiguration(first);
        this.addConfiguration(second);
        this.addConfiguration(third);
        this.addConfiguration(fourth);

        //Orange
        this.setColor(new float[]{1.0f, 0.65f, 0.0f});
    }

}
