package com.example.stereotype13.blocs;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by stereotype13 on 8/24/14.
 */
public class BlocsModel {
    public final static int BOARD_COLUMNS = 12;
    public final static int BOARD_ROWS = 18;

    private static int[][] mBoard;
    private static int mCurrentBlockID = 1;

    private static ArrayList<Block> mBlocks;

    private Scene mScene;

    public BlocsModel(Scene scene) {

        mScene = scene;
        mScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

        mBlocks = new ArrayList<Block>();

        mBoard = new int[BOARD_ROWS][BOARD_COLUMNS];

        /*for(int i = 0; i < BOARD_ROWS; ++i) {
            for(int j = 0; j < BOARD_COLUMNS; ++j) {
                mBoard[i][j] = 0;
            }
        }*/

    }

    public boolean updateModel() {
        boolean updateWorked = true;
        //Look for the in-play block and move it down, unless there is a collision.
        for(Block block : mBlocks) {
            if(block.isInPlay()) {
                block.update();

                if(block.detectCollision()) {
                    block.setIsInPlay(false);
                    updateWorked = false;
                }


            }
        }

        return updateWorked;
    }

    public static boolean collisionDetected() {
        boolean detected = false;
        //Get the current block
        Block block = mBlocks.get(mBlocks.size() - 1);
        if(block.detectCollision()) {
            detected = true;
            block.setIsInPlay(false);
        }

        return detected;
    }

    public boolean addBlock(Block block) {
        boolean result = true;
        block.setID(mCurrentBlockID);
        mBlocks.add(block);
        if(!block.placeOnBoard()) {
            //overlap detected
            result = false;
        }



        mCurrentBlockID++;

        return result;
    }

    public float[] getColorByBlockID(int ID) {
        Block block = mBlocks.get(ID - 1);
        return block.getColor();
    }
    
    public Scene render(Scene scene) {
        //Clear the scene first
        scene.detachChildren();

        //Redraw the scene, block by block
        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                if(mBoard[i][j] > 0) {
                    float[] color = getColorByBlockID(mBoard[i][j]);
                    Square square = new Square(color);
                    square.setXY(i, j);
                    scene.attachChild(square.getSquare());
                }
            }
        }

        return scene;
    }

    public static int[][] getBoard() {
        return mBoard;
    }

    public static void setBoard(int[][] board) {
        mBoard = board;
    }

    public String toString() {
        String output = new String();

        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                if(mBoard[i][j] > 0) {
                    output += "X";
                }
                else {
                    output += ".";
                }
            }
            output += "\n";
        }

        return output;
    }

    public void reset() {

        //Set all elements of the board to 0
        for(int i = 0; i < BOARD_ROWS; i++) {
            for(int j = 0; j < BOARD_COLUMNS; j++) {
                mBoard[i][j] = 0;

            }

        }

        //Clear the list of blocks and reset the ID to 1
        mBlocks.clear();
        mCurrentBlockID = 1;
    }

    public boolean rotate() {
        Block block = mBlocks.get(mBlocks.size() - 1);
        block.rotate();

        return true;



    }

    public static int[][] getFrontBuffer() {
        return mBoard;
    }

    public static void setFrontBuffer(int[][] frontBuffer) {
        mBoard = frontBuffer;
    }

    public static int[][] getBackBuffer() {

        int[][] backBuffer = new int[BOARD_ROWS][BOARD_COLUMNS];

        //Create a deep copy of the board
        final boolean[][] result = new boolean[mBoard.length][];
        for (int i = 0; i < mBoard.length; i++) {
            backBuffer[i] = Arrays.copyOf(mBoard[i], mBoard[i].length);

        }

        return backBuffer;
    }

    public static boolean move(int x) {
        boolean canMove = true;

        Block block = mBlocks.get(mBlocks.size() - 1);
        //Flip flop x and y
        block.setY(x);
        block.placeOnBoard();
        return canMove;
    }

}
