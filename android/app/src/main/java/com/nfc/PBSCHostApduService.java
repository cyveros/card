package com.nfc;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;

import java.util.Arrays;

public class PBSCHostApduService extends HostApduService {
    private final String TAG = "NFC_CArd";
    private String STATUS_SUCCESS = "9000";
    private String STATUS_FAILED = "6F00";
    private String CLA_NOT_SUPPORTED = "6E00";
    private String INS_NOT_SUPPORTED = "6D00";
    private String SAMPLE_RESP = "FE0F0E0D0A0908070605040302010088888888888888887777777777777777777777777777777777777777FE0F0E0D0A090807060504030201008888888888888888FE0F0E0D0A0908070605040302010088888888888888887777777777777777777777777777777777777777FE0F0E0D0A090807060504030201008888888888888888";
    private String AID = "F0010203040506";
    private String SELECT_INS = "A4";
    private String DEFAULT_CLA = "00";
    private int MIN_APDU_LENGTH = 12;


    @Override
    public void onDeactivated(int reason) {
        Log.e(TAG, "on deactived " + reason);
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.e(TAG, "start process");
        Log.e(TAG, byteArrayToHexString(commandApdu));

        PBSCTokenStorage.getInstance().set("cmd", byteArrayToHexString(commandApdu));

        if (commandApdu == null) {
            return hexStringToByteArray(STATUS_FAILED);
        }

        // return hexStringToByteArray(STATUS_SUCCESS);

        String hexCommandApdu = byteArrayToHexString(commandApdu);
        if (hexCommandApdu.length() < MIN_APDU_LENGTH) {
            return hexStringToByteArray(STATUS_FAILED);
        }

        if (hexCommandApdu.substring(0, 2) != DEFAULT_CLA) {
            String resp = SAMPLE_RESP;

            if (PBSCTokenStorage.getInstance().get("token") != null) {
                resp = PBSCTokenStorage.getInstance().get("token");
            }
            return hexStringToByteArray(resp);
        }

        if (hexCommandApdu.substring(2, 4) != SELECT_INS) {
            return hexStringToByteArray(INS_NOT_SUPPORTED);
        }

        if (hexCommandApdu.substring(10, 24) == AID)  {
            return hexStringToByteArray(STATUS_SUCCESS);
        } else {
            return hexStringToByteArray(STATUS_FAILED);
        }
    }

    public String byteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    public byte[] hexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
