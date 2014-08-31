package com.example.stereotype13.blocs;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stereotype13 on 8/25/14.
 */
public class Square {

    private Rectangle mRectangle;
    private float mXPixels;
    private float mYPixels;
    private int mX;
    private int mY;
    private float mSideLength;


    public Square() {

        mSideLength = Blocs.CAMERA_HEIGHT / 18;
        mRectangle = new Rectangle(0, 0, mSideLength, mSideLength, Blocs.mVertexBufferObjectManager);
        mRectangle.setColor(1.0f, 1.0f, 1.0f);

    }

    public Square(float[] color) {
        mSideLength = Blocs.CAMERA_HEIGHT / 18;
        mRectangle = new Rectangle(0, 0, mSideLength, mSideLength, Blocs.mVertexBufferObjectManager);
        mRectangle.setColor(color[0], color[1], color[2]);
    }

    public void setXY(int x, int y) {
        mX = x;
        mY = y;
        mXPixels = mSideLength * mX;
        mYPixels = mSideLength * mY;

        //x and y need to be flip-flopped in order to map shapes to the screen properly
        mRectangle.setX(mYPixels);
        mRectangle.setY(mXPixels);
    }

    public Rectangle getSquare() {
        return mRectangle;
    }

    public Scene render(Scene scene) {
        scene.attachChild(mRectangle);
        return scene;
    }


}
