package com.app.loyality.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import java.util.Arrays;

public class NfcUtils {

    public static boolean isNfcAvailableAndEnabled(Activity activity) {
        return activity.getPackageManager().hasSystemFeature("android.hardware.nfc");
    }

    public static void enableNfcReader(Activity activity) {
        Intent intent = new Intent(activity, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    public static void disableNfcReader(Activity activity) {
        // No specific action needed here
    }

    public static boolean isNfcIntent(Intent intent) {
        return NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction());
    }


    public static String readNfcTag(Intent intent) {
        String tagContent = null;
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    NdefMessage ndefMessage = ndef.getNdefMessage();
                    if (ndefMessage != null) {
                        NdefRecord[] records = ndefMessage.getRecords();
                        for (NdefRecord record : records) {
                            if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                    Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                                byte[] payload = record.getPayload();
                                String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
                                int languageCodeLength = payload[0] & 0063;
                                tagContent = new String(payload, languageCodeLength + 1,
                                        payload.length - languageCodeLength - 1, textEncoding);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ndef.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return tagContent;
    }
}
