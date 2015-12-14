package org.dronix.servocardboard;

import android.app.Application;

import org.dronix.servocardboard.di.DaggerServoCardComponent;
import org.dronix.servocardboard.di.ServoCardComponent;
import org.dronix.servocardboard.di.ServoCardModule;

public class ServoCardApplication extends Application{
    private ServoCardComponent servoCardComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        servoCardComponent = DaggerServoCardComponent.builder()
                .servoCardModule(new ServoCardModule(this)).
                        build();
    }

    public ServoCardComponent component() {
        return servoCardComponent;
    }

}
