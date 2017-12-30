package com.example.application.demoinstagram;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("49eaca829c12b5c30de372555c29aefce2f3e5ff")
                .clientKey("2e1b84da73bb527327c5a9f2787d8dae522e95f9")
                .server("http://18.218.28.142:80/parse/")
                .build()
        );
//    orWgp6KLanF1

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
