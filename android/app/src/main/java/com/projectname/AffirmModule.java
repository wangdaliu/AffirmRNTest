package com.projectname;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class AffirmModule extends ReactContextBaseJavaModule {
    AffirmModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "AffirmModule";
    }

    @ReactMethod
    public void checkout() {
        Intent intent = new Intent(MainApplication.getInstance(), RoutineNoUIActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainApplication.getInstance().startActivity(intent);
    }
}