package com.nfc;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class PBSCHCEManagerModule extends ReactContextBaseJavaModule {
    private PBSCTokenStorage tokenStorage;

    public PBSCHCEManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);

        tokenStorage = PBSCTokenStorage.getInstance();
    }

    @Override
    public String getName() {
        return "PBSCHCEManager";
    }

    @ReactMethod
    public void addUniqueToken(String uniqueToken, Promise promise) {
        tokenStorage.set("token", uniqueToken);

        promise.resolve(uniqueToken);
    }

    @ReactMethod
    public void removeUniqueToken(Promise promise) {
        promise.resolve(tokenStorage.clear("token"));
    }

    @ReactMethod
    public void getStoredUniqueToken(Promise promise) {
        promise.resolve(tokenStorage.get("token"));
    }

    @ReactMethod
    public void addApduCmd(String uniqueToken, Promise promise) {
        tokenStorage.set("cmd", uniqueToken);

        promise.resolve(uniqueToken);
    }

    @ReactMethod
    public void removeApduCmd(Promise promise) {
        promise.resolve(tokenStorage.clear("cmd"));
    }

    @ReactMethod
    public void getApduCmd(Promise promise) {
        promise.resolve(tokenStorage.get("cmd"));
    }
}
