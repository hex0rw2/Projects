package com.example.root.sm_logger;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Handler;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class SMSObserver extends ContentObserver {
    private Context context;
    private static final String TAG = "SMSObserver";
    private static final Uri SMS_URI = Telephony.Sms.CONTENT_URI;    //get uri depending on different devices

    public SMSObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    //this onChange method will be fired up when you send or receive message
    @Override
    public void onChange(boolean selfChange) {

        Cursor cursor = context.getContentResolver().query(SMS_URI, null, null, null, Telephony.Sms.DATE + " DESC");
        String phNumber = null;
        //make sure there is a message being operating
        if (cursor != null && cursor.moveToFirst()) {
            try {
                //the meanings of those variables are quite straight forward
                String type = cursor.getString(cursor.getColumnIndex(Telephony.Sms.TYPE));
                String content = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
                Date SMSDate = new Date(Long.valueOf(date));
                phNumber = cursor.getString(cursor.getColumnIndex("address"));   //this is phone number rather than address

                String contact = getContactDisplayNameByNumber(phNumber);    //call the metod that convert phone number to contact name in your contacts

                int typeCode = Integer.parseInt(type);
                String direction = "";
                //get the right direction
                switch (typeCode) {
                    case Telephony.Sms.MESSAGE_TYPE_INBOX:
                        direction = "INCOMING";
                        break;

                    case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                        direction = "OUTGOING";
                        break;

                    case Telephony.Sms.MESSAGE_TYPE_SENT:
                        direction = "SENT";
                        break;

                    default:
                        direction = "UNKNOWN";
                        Log.e(TAG, typeCode + " is unknown");
                        break;
                }

            } catch (CursorIndexOutOfBoundsException e) {
                Log.e(TAG, "SMSHelper: outgoingSMS", e);
            } finally {
                cursor.close();
            }
        }
        Toast.makeText(context, "\nName: " + contact + "\nPhone Number:--- " + phNumber + " \nContent:--- "
                + content + " \nCall Date:--- " + SMSDate
                + " Direction: " + direction, Toast.LENGTH_LONG).show();

        Log.i(TAG, "\nName: " + contact + "\nPhone Number:--- " + phNumber + " \nContent:--- "
                + content + " \nCall Date:--- " + SMSDate
                + " Direction: " + direction + "\n----------------------------------");

        super.onChange(selfChange);
    }


    /**
     * http://stackoverflow.com/questions/3712112/search-contact-by-phone-number
     * Look up phone number
     *
     * @param number  phone number
     * @return the name matched with the phone number
     */
    private String getContactDisplayNameByNumber(String number) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = "<Not in contact list>";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToFirst();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
                Log.i(TAG, "Found number in contacts: " + number + " = " + name);
            } else {
                Log.e(TAG, "Cursor is null or empty " + number + " not found in contacts");
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }
        return name;
    }
}