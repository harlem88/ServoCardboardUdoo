package org.dronix.servocardboard.di;

import org.dronix.servocardboard.ServoCardApplication;
import org.dronix.servocardboard.manager.CameraInputManager;
import org.dronix.servocardboard.manager.ServerApiManager;
import org.dronix.servocardboard.presenter.ServoCardBoardPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServoCardModule {
    private final ServoCardApplication application;

    public ServoCardModule(ServoCardApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    CameraInputManager provideCameraInputManager() {
        return new CameraInputManager();
    }

    @Provides
    @Singleton
    ServoCardBoardPresenter provideServoCardBoardPresenter() {
        return new ServoCardBoardPresenter(application);
    }

    @Provides
    @Singleton
    ServerApiManager provideServerApiManager() {
        return new ServerApiManager();
    }
}
