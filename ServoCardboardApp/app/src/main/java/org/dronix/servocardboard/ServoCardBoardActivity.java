package org.dronix.servocardboard;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import org.dronix.servocardboard.databinding.ServoCardboardBinding;
import org.dronix.servocardboard.manager.CameraInputManager;
import org.dronix.servocardboard.presenter.ServoCardBoardPresenter;

import javax.inject.Inject;
import javax.microedition.khronos.egl.EGLConfig;

public class ServoCardBoardActivity extends CardboardActivity implements CardboardView.StereoRenderer {

    private static final float RANGE_CENTER = 0.5f;
    /**
     * reset 0, left 1, right 2
     */
    private int currentPosition;
    private float mLastX;
    private ViewGroup viewGroup;

    @Inject
    ServoCardBoardPresenter servoCardBoardPresenter;

    @Inject
    CameraInputManager cameraInputManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ServoCardApplication) getApplication()).component().inject(this);
        ServoCardboardBinding servoCardboardBinding = DataBindingUtil.setContentView(this, R.layout.servo_cardboard);
        servoCardboardBinding.setPresenter(servoCardBoardPresenter);

        CardboardView cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        cardboardView.setRenderer(this);
        setCardboardView(cardboardView);


        viewGroup = (ViewGroup) findViewById(R.id.overlay);

    }

    @Override
    protected void onStart() {
        super.onStart();
        servoCardBoardPresenter.load();
    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {
        // Build the Model part of the ModelView matrix.
        float[] value = new float[3];
        headTransform.getEulerAngles(value, 0);
        float x = value[1];
        if (x > RANGE_CENTER && currentPosition != 1) {
            Log.i("EUL", "left");
            currentPosition = 1;
            servoCardBoardPresenter.move(currentPosition);
            mLastX = x;
        } else if (x < -RANGE_CENTER && currentPosition != 2) {
            Log.i("EUL", "right");
            currentPosition = 2;
            servoCardBoardPresenter.move(currentPosition);
            mLastX = x;
        } else if ((x < RANGE_CENTER && x > -RANGE_CENTER) && currentPosition != 0) {
            Log.i("EUL", "center");
            currentPosition = 0;
            servoCardBoardPresenter.move(currentPosition);
        }

    }

    @Override
    public void onDrawEye(Eye eye) {

    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {

    }

    @Override
    public void onRendererShutdown() {

    }
}
