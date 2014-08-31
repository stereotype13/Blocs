package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/28/14.
 */
public class TShape extends Block {

    TShape() {
        int[][] first = new int[][]{{0,1,0},
                {1,1,1},
                {0,0,0}};

        int[][] second = new int[][]{{0,1,0},
                {0,1,1},
                {0,1,0}};

        int[][] third = new int[][]{{0,0,0},
                {1,1,1},
                {0,1,0}};

        int[][] fourth = new int[][]{{0,1,0},
                {1,1,0},
                {0,1,0}};


        this.addConfiguration(first);
        this.addConfiguration(second);
        this.addConfiguration(third);
        this.addConfiguration(fourth);

        //Firebrick red
        this.setColor(new float[]{0.7f, 0.13f, 0.13f});
    }

}
