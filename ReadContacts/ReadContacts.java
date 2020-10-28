package appdevin.com.readcontacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;

class ReadContacts {

    //The request code number
    public static final int REQUEST_READ_CONTACTS = 79;

    private Context mContext;
    private ArrayList<Contact> mContactArrayList;


    public ReadContacts(Context context) {
        this.mContext = context;
        mContactArrayList = getAllContacts();

    }

    public static boolean checkPermission(Context context){
        return ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED;
    }


    public static  void requestPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }


    private ArrayList<Contact> getAllContacts() {

        ArrayList<Contact> nameList = new ArrayList<>();

        ContentResolver cr = mContext.getContentResolver();

        //Get the contact uri
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        // Check if the cur got any value
        if ((cur != null ? cur.getCount() : 0) > 0) {

            //Loop through each row
            while (cur != null && cur.moveToNext()) {

                //Get data based on the column
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));


                Contact contact = new Contact(id, name);
                nameList.add(contact);

                //Check if that contact has an number
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    //Get the number based on the id
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contact.getId()}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contact.addNumber(phoneNo);

                    }


                    pCur.close();
                }
            }
        }



        if (cur != null) {
            cur.close();
        }
        return nameList;
    }

    public ArrayList<Contact> getContactList() {
        return mContactArrayList;
    }

    public void refeshContacts(){
        mContactArrayList = getAllContacts();
    }

    public boolean hasContacts(){
        return mContactArrayList.size() > 0;
    }
}
