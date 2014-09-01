package com.example.stereotype13.blocs;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import java.util.Random;

/**
 * Created by stereotype13 on 8/24/14.
 */
public class Blocs extends SimpleBaseGameActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    public static final int CAMERA_HEIGHT = 720;
    public static final int CAMERA_WIDTH = 480;

    private static final float DEMO_VELOCITY = 100.0f;

    private static final float MOVE_TIMER_THRESHOLD_SECS = 0.5f;
    private static final float MOVE_TOUCH_THRESHOLD = 50;
    private static final long TAP_TOUCH_THRESHOLD_MS = 100;

    // ===========================================================
    // Fields
    // ===========================================================

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TiledTextureRegion mFaceTextureRegion;

    private Boolean mMovingRight = true;
    private Scene mScene;
    private BlocsModel mBlocsModel;

    public static VertexBufferObjectManager mVertexBufferObjectManager;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public EngineOptions onCreateEngineOptions() {

        //mVertexBufferObjectManager = this.getVertexBufferObjectManager();

        final Camera camera = new Camera(0, 0, Blocs.CAMERA_WIDTH, Blocs.CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(Blocs.CAMERA_WIDTH, Blocs.CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 32, TextureOptions.BILINEAR);
        this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "face_circle_tiled.png", 0, 0, 2, 1);
        this.mBitmapTextureAtlas.load();
    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());
        mVertexBufferObjectManager = this.getVertexBufferObjectManager();

        mScene = new Scene();

        mScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

        mBlocsModel = new BlocsModel(mScene);
        Block block = chooseRandomBlock();

        mBlocsModel.addBlock(block);

       // Log.d("LOG", mBlocsModel.toString());

        float xSeconds = 0.5f; // meaning 5 and a half second
        boolean repeat = true; // true to reset the timer after the time passed and execute again
        final TimerHandler myTimer = new TimerHandler(xSeconds, repeat, new ITimerCallback() {
            public void onTimePassed(TimerHandler pTimerHandler) {
                update();
            }
        });

        mScene.registerUpdateHandler(myTimer);

        mScene.setTouchAreaBindingOnActionDownEnabled(true);
        mScene.setTouchAreaBindingOnActionMoveEnabled(true);
        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {

            private float originX = 0.0f;
            private long startTime = 0;
            private long deltaTime = 0;

            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

                if(pSceneTouchEvent.isActionDown()) {
                    originX = pSceneTouchEvent.getX();
                    startTime = pSceneTouchEvent.getMotionEvent().getEventTime();
                }
                else if(pSceneTouchEvent.isActionUp()) {
                    deltaTime = startTime - pSceneTouchEvent.getMotionEvent().getEventTime();
                    if(deltaTime <= TAP_TOUCH_THRESHOLD_MS) {

                        mBlocsModel.rotate();
                        if(BlocsModel.collisionDetected()) {

                            mBlocsModel.addBlock(chooseRandomBlock());
                        }
                        mScene = mBlocsModel.render(mScene);
                    }
                }
                return true;
            }
        });

        //delete this line!!!!!!
        //mScene = mBlocsModel.render(mScene);
        return mScene;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public Block chooseRandomBlock() {
        //Choose a random block type
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int blockType = rand.nextInt((7 - 1) + 1) + 1;
        Block block = new Block();

        switch (blockType) {
            case BlockTypesContract.L_SHAPE_RIGHT:
                block = new LShapeRight();
                break;

            case BlockTypesContract.T_SHAPE:
                block = new TShape();
                break;

            case BlockTypesContract.L_SHAPE_LEFT:
                block = new LShapeLeft();
                break;

            case BlockTypesContract.N_SHAPE_RIGHT:
                block = new NShapeRight();
                break;

            case BlockTypesContract.N_SHAPE_LEFT:
                block = new NShapeLeft();
                break;

            case BlockTypesContract.STICK_SHAPE:
                block = new StickShape();
                break;

            case BlockTypesContract.SQUARE_SHAPE:
                block = new SquareShape();
                break;

            default:
                block = new NShapeRight();
        }
        return block;
    }

    public void update() {
        if(!mBlocsModel.updateModel()) {

            if(!mBlocsModel.addBlock(chooseRandomBlock())) {
                mBlocsModel.reset();

                mBlocsModel.addBlock(chooseRandomBlock());
            }
        }

       // Log.d("LOG", mBlocsModel.toString());
        mScene = mBlocsModel.render(mScene);

    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================


}
