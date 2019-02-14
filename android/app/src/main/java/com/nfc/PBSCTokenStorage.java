package com.nfc;

import java.util.concurrent.atomic.AtomicReference;

public class PBSCTokenStorage {
    public static PBSCTokenStorage sInstance = null;

    private AtomicReference<String> atomicToken;

    public PBSCTokenStorage() {
        this.atomicToken = new AtomicReference<String>(null);
    }

    public void set(String token)  {
        this.atomicToken.set(token);
    }

    public String get() {
        return this.atomicToken.get();
    }

    public String clear() {
        return this.atomicToken.getAndSet(null);
    }

    public static PBSCTokenStorage getInstance() {
        if (sInstance == null) {
            sInstance = new PBSCTokenStorage();
        }

        return sInstance;
    }
}
