package com.nfc;

import java.util.concurrent.atomic.AtomicReference;

public class PBSCTokenStorage {
    public static PBSCTokenStorage sInstance = null;

    private AtomicReference<String> atomicToken;
    private AtomicReference<String> atomicApduCmd;

    public PBSCTokenStorage() {
        this.atomicToken = new AtomicReference<String>(null);
        this.atomicApduCmd = new AtomicReference<String>(null);
    }

    public void set(String type, String token)  {
        if (type == "cmd") {
            this.atomicApduCmd.set(token);

            return;
        }

        this.atomicToken.set(token);
    }

    public String get(String type) {
        if (type == "cmd") {
            return this.atomicApduCmd.get();
        }

        return this.atomicToken.get();
    }

    public String clear(String type) {
        if (type == "cmd") {
            return this.atomicApduCmd.getAndSet(null);
        }
        return this.atomicToken.getAndSet(null);
    }

    public static PBSCTokenStorage getInstance() {
        if (sInstance == null) {
            sInstance = new PBSCTokenStorage();
        }

        return sInstance;
    }
}
