package com.example.stereotype13.blocs;

/**
 * Created by stereotype13 on 8/31/14.
 */
public class SquareShape extends Block {
    public SquareShape() {

        int[][] first = new int[][]{{1,1},
                {1,1}
                };

        this.addConfiguration(first);

        //Cadet Blue
        this.setColor(new float[]{0.37f, 0.62f, 0.62f});
    }
}
