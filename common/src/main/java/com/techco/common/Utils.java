package com.techco.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Nhat on 4/16/17.
 */

public class Utils {

    /**
     * @param context
     * @return
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    /**
     * @param context
     */
    public static void printKeyHash(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo("com.techco.homehome", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }


    /**
     * @param bitmap
     * @return
     */
    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        Bitmap newBitmap = resizeBitmap(bitmap, 500, 400);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }


    /**
     * @param photoPath
     * @return
     */
    public static String getEncoded64ImageStringFromPath(String photoPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = resizeBitmap(BitmapFactory.decodeFile(photoPath, options), 700, 400);
        return getEncoded64ImageStringFromBitmap(bitmap);
    }

    /**
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * @param inputDate
     * @return
     */
    public static String getDateFormatted(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String outputDay = inputDate;
        try {
            date = format.parse(outputDay);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            outputDay = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return outputDay;
    }

    /**
     * @param distance
     * @return
     */
    public static String getDistanceFormatted(double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (distance >= 1) {
            return String.valueOf(decimalFormat.format(distance)) + " km";
        }
        int meterValue = (int) (distance * 1000);
        return String.valueOf(meterValue) + " m";
    }

    /**
     * @param context
     * @param resDrawable
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createBitmapWithSize(Context context, int resDrawable, int width, int height) {
        BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(resDrawable);
        Bitmap bitmap = bitmapdraw.getBitmap();
        Bitmap outBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return outBitmap;
    }

    /**
     * @param context
     * @return
     */
    public static boolean deviceHasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param parentDir
     * @param fileExtension
     * @return
     */
    public static List<File> getListFiles(File parentDir, String fileExtension) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file, fileExtension));
            } else {
                if (file.getName().endsWith("." + fileExtension)) {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    /**
     * @param folderName
     * @param context
     */
    public static String getCacheFolder(String folderName, Context context) {
        File fileDir = new File(context.getCacheDir() + "/" + folderName);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {

            }
        }
        return fileDir.getPath();
    }

    /**
     * @param context
     * @param uri
     * @return
     */
    public static String getImagePathFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        String path = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        proj, sel, new String[]{id}, null);
            } else {
                cursor = context.getContentResolver().query(uri, proj, null, null, null);
            }
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                path = cursor.getString(column_index);
            }
        } finally {
            cursor.close();
        }
        return path;
    }

    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
