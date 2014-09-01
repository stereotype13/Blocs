package com.example.stereotype13.blocs;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by stereotype13 on 8/24/14.
 */
public class Block {

    private ArrayList<Configuration> mConfigurations;
    private int mCurrentConfiguration = 0;
    private int mBlockID = 1;
    private boolean mInPlay = true;
    private float[] mColor;

    public int x = 0;
    public int y = 0;


    public Block() {


        mConfigurations = new ArrayList<Configuration>();
        mColor = new float[]{1.0f, 1.0f, 1.0f};

        //Set initial horizontal position
        this.y = 4;

        mBlockID = 1;

    }

    public void setColor(float[] color) {
        mColor = color;
    }

    public float[] getColor() {
        return mColor;
    }

    public void addConfiguration(int[][] configArray) {
        Configuration configuration = new Configuration(configArray);
        mConfigurations.add(configuration);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean placeOnBoard() {
        int[][] frontBuffer = BlocsModel.getFrontBuffer();
        int[][] backBuffer = BlocsModel.getBackBuffer();
        boolean itWorked = true;

        removeBlock(backBuffer);

        Configuration configuration = mConfigurations.get(mCurrentConfiguration);
        int[][] shape = configuration.getShape();

        int BOARD_ROWS = frontBuffer.length;
        int BOARD_COLUMNS = frontBuffer[0].length;

        //Loop through the shape
        for(int i = 0; i < shape.length; ++i) {
            for(int j = 0; j < shape[i].length; ++j) {
                 if(shape[i][j] > 0) {
                     if(backBuffer[this.x + i][this.y + j] == 0) {
                         backBuffer[this.x + i][this.y + j] = mBlockID;
                     }
                     else {

                         itWorked = false;
                         break;
                     }

                 }
            }
        }

        if(itWorked) {
            //if the rotation worked fine, set the backbuffer as the new buffer
            BlocsModel.setBoard(backBuffer);
        }
        else {
            //otherwise, keep the front buffer
            BlocsModel.setBoard(frontBuffer);
        }


        return itWorked;
    }



    public class Configuration {

        private int[][] mShape;



        public Configuration(int[][] shape) {
            mShape = shape;
        }

        public int[][] getShape() {
            return mShape;
        }

    }

    public boolean detectCollision() {
        int[][] board = BlocsModel.getBoard();

        int BOARD_ROWS = board.length;
        int BOARD_COLUMNS = board[0].length;

        int[][] backBuffer = new int[BOARD_ROWS][BOARD_COLUMNS];

        //Create a deep copy of the board
        final boolean[][] result = new boolean[board.length][];
        for (int i = 0; i < board.length; i++) {
            backBuffer[i] = Arrays.copyOf(board[i], board[i].length);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }

        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                if(board[i][j] == mBlockID) {

                    if(i + 1 == BOARD_ROWS) {
                        return true;
                    }

                    //Look below each square of the block, and see if it's touching anything except empty space.
                    if(board[i + 1][j] != 0 && board[i + 1][j] != mBlockID) {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean detectOverlap() {
        int[][] board = BlocsModel.getBoard();

        int BOARD_ROWS = board.length;
        int BOARD_COLUMNS = board[0].length;

        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                if(board[i][j] == mBlockID) {

                    if(i + 1 == BOARD_ROWS) {
                        return true;
                    }

                    //Check if any square overlaps with another block
                    if(board[i][j] != 0 && board[i][j] != mBlockID) {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public void removeBlock(int[][] buffer) {

        //Remove the in-play block
        int BOARD_ROWS = buffer.length;
        int BOARD_COLUMNS = buffer[0].length;

        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                if(buffer[i][j] == mBlockID) {

                    //Set the current square to 0.
                    buffer[i][j] = 0;

                }

            }

        }

    }

    public void update() {

        int[][] frontBuffer = BlocsModel.getFrontBuffer();
        int[][] backBuffer = BlocsModel.getBackBuffer();

        int BOARD_ROWS = frontBuffer.length;
        int BOARD_COLUMNS = frontBuffer[0].length;

        removeBlock(backBuffer);

        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                if(frontBuffer[i][j] == mBlockID) {

                    //Look at the back buffer and shift each of the in-play's blocks down 1 unit
                    backBuffer[i + 1][j] = mBlockID;



                }

            }

        }

        this.x++;

        BlocsModel.setFrontBuffer(backBuffer);


    }

    public int[][] getCurrentConfiguration() {
        return mConfigurations.get(mCurrentConfiguration).mShape;
    }

    public int[][] getNextConfiguration() {
        int noOfConfigurations = mConfigurations.size();
        mCurrentConfiguration++;
        int nextConfiguration = mCurrentConfiguration + 1;
        if(nextConfiguration >= noOfConfigurations) {
            nextConfiguration = 0;
        }
        return mConfigurations.get(nextConfiguration).mShape;
    }

    public void rotate() {

        int noOfConfigurations = mConfigurations.size();
        mCurrentConfiguration++;
        if(mCurrentConfiguration >= noOfConfigurations) {
            mCurrentConfiguration = 0;
        }

        //Place the new configuration on the board
        if(!placeOnBoard()) {
            mCurrentConfiguration--;
            if(mCurrentConfiguration < 0) {
                mCurrentConfiguration = noOfConfigurations - 1;
            }
        }
    }



    public boolean isInPlay() {
        return mInPlay;
    }

    public void setIsInPlay(boolean inPlay) {
        mInPlay = inPlay;
    }

    public void setID(int id) {
        mBlockID = id;
    }

    public int getID() {
        return mBlockID;
    }


}
