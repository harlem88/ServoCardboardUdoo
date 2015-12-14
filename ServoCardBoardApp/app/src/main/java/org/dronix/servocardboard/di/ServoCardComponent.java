package org.dronix.servocardboard.di;

import org.dronix.servocardboard.ServoCardBoardActivity;
import org.dronix.servocardboard.manager.CameraInputManager;
import org.dronix.servocardboard.manager.ServerApiManager;
import org.dronix.servocardboard.presenter.ServoCardBoardPresenter;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Singleton
@Component(modules = {ServoCardModule.class})
public interface ServoCardComponent {

    void inject(ServoCardBoardActivity servoCardBoardActivity);
    void inject(ServoCardBoardPresenter servoCardBoardPresenter);

    CameraInputManager provideCameraInputManager();
    ServoCardBoardPresenter provideServoCardBoardPresenter();
    ServerApiManager provideServerApiManager();
}
