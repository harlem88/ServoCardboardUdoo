package org.dronix.servocardboard.manager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.dronix.servocardboard.BuildConfig;
import org.dronix.servocardboard.utils.MjpegInputStream;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Subscriber;


public class CameraInputManager {

    private AtomicBoolean isStopped;

    public CameraInputManager() {
        isStopped = new AtomicBoolean(false);
    }

    public Bitmap decodeBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public Observable<Bitmap> getBytes(){
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                MjpegInputStream mjpegInputStream = MjpegInputStream.read(BuildConfig.SERVER_URL + ":8090/test.mjpeg");
                if (mjpegInputStream == null)
                    subscriber.onError(new Throwable("mjpegInputStream == null"));
                else {
                    while (!isStopped.get()) {
                        try {
                            subscriber.onNext(mjpegInputStream.readMjpegFrame());
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                }
                subscriber.onCompleted();

            }
        });
    }

    public void stop(){
        isStopped.set(true);
    }
}