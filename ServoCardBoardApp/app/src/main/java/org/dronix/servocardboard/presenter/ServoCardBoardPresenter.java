package org.dronix.servocardboard.presenter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.android.databinding.library.baseAdapters.BR;

import org.dronix.servocardboard.ServoCardApplication;
import org.dronix.servocardboard.interfaces.IServoCardBoardView;
import org.dronix.servocardboard.manager.CameraInputManager;
import org.dronix.servocardboard.manager.ServerApiManager;
import org.dronix.servocardboard.model.Direction;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServoCardBoardPresenter extends BaseObservable {
    private IServoCardBoardView mIServoCardBoardView;
    private Subscription mCameraSubscription;

    private BitmapDrawable image;

    @Inject
    CameraInputManager cameraInputManager;

    @Inject
    ServerApiManager serverApiManager;

    public ServoCardBoardPresenter(ServoCardApplication servoCardApplication){
        servoCardApplication.component().inject(this);
    }

    public void load(){
        if (mCameraSubscription == null)
            mCameraSubscription = cameraInputManager.getBytes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Bitmap>() {
                        @Override
                        public void onCompleted() {}

                        @Override
                        public void onError(Throwable e) {}

                        @Override
                        public void onNext(Bitmap bitmap) {
                            image = new BitmapDrawable(bitmap);
                            notifyPropertyChanged(BR.image);
                        }
                    });
    }

    @Bindable
    public BitmapDrawable getImage() {
        return this.image;
    }

    public void move(int currentPosition) {
        Direction direction;
        switch (currentPosition) {
            case 1:
                direction = Direction.Builder(Direction.Directions.LEFT.name());
                break;
            case 2:
                direction = Direction.Builder(Direction.Directions.RIGHT.name());
                break;
            case 0:
            default:
                direction = Direction.Builder(Direction.Directions.CENTER.name());
                break;
        }
        serverApiManager.move(direction);
    }
}
