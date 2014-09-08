package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/28/14.
 */
public class LShapeLeft extends Block {

    LShapeLeft() {
        int[][] first = new int[][]{
                {0,1},
                {0,1},
                {1,1}};

        int[][] second = new int[][]{
                {1,0,0},
                {1,1,1}};

        int[][] third = new int[][]{
                {1,1},
                {1,0},
                {1,0}};

        int[][] fourth = new int[][]{
                {1,1,1},
                {0,0,1}};


        this.addConfiguration(first);
        this.addConfiguration(second);
        this.addConfiguration(third);
        this.addConfiguration(fourth);

        //Purple
        this.setColor(new float[]{0.63f, 0.13f, 0.94f});
    }
}
