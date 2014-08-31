package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/28/14.
 */
public class StickShape extends Block {
    public StickShape() {

        int[][] first = new int[][]{{0,1,0,0},
                {0,1,0,0},
                {0,1,0,0},
                {0,1,0,0}};

        int[][] second = new int[][]{{0,0,0,0},
                {1,1,1,1},
                {0,0,0,0},
                {0,0,0,0}};


        this.addConfiguration(first);
        this.addConfiguration(second);


    }
}
