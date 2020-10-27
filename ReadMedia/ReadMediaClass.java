package appdevin.com.read_photos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

class ReadMediaClass {

    public static int READ_PERMISSION_CODE = 101;
    private int type;
    private ArrayList<Media> mediaList = new ArrayList<>();

    public ReadMediaClass(int type) {
        this.type = type;
    }


    public ArrayList<Media> getListMedia(Context context){


        Uri uri;
        Cursor cursor;
        int column_index_data, column_date;
        ArrayList<Media> mediaList = new ArrayList<>();
        String ablosutePathImage;
        String date;


        //Which type of content you want Images, Videos, Audio
        switch (type){
            case 0:
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                break;
            case 1:
                uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                break;
            case 2:
                uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                break;
            default:
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                break;
        }

        // which image properties are we querying
        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATE_TAKEN
        };

//        Query and order by the date taken
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri, projection, null, null,orderBy+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_date = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_TAKEN);




        while(cursor.moveToNext()){
            ablosutePathImage = cursor.getString(column_index_data);
            date = cursor.getString(column_date);
            Log.d("Dates", "listOfImage: " + ablosutePathImage);
            mediaList.add(new Media(ablosutePathImage, date, type));
        }

        this.mediaList = mediaList;
        return mediaList;
    }

    public int getLength(){
        return mediaList.size();
    }



    public static Boolean checkPermission(Context context){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    public static void askPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, ReadMediaClass.READ_PERMISSION_CODE);
    }

}
