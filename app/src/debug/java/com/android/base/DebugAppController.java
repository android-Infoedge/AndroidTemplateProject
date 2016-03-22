package com.android.base;

import com.facebook.stetho.Stetho;

/**
 * Created by gagandeep on 18/3/16.
 */
public class DebugAppController extends AppController {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.Initializer initializer = Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)) // Enable Chrome DevTools
                .build();  // Use the InitializerBuilder to generate an Initializer

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }
}
