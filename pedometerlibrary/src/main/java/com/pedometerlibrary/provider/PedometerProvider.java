package com.pedometerlibrary.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pedometerlibrary.data.db.PedometerDBHelper;
import com.pedometerlibrary.data.source.PedometerPersistenceContract;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/9/21 9:29
 * <p>
 * PedometerProvider
 */

public class PedometerProvider extends ContentProvider {
    public static final String SEPARATOR = "/";
    public static final String SCHEME = "content";
    public static final String AUTHORITY = "com.pedometerlibrary.PedometerProvider";
    public static final String STEP_PATH = "step";
    public static final String STEP_PART_PATH = "stepPart";
    public static final int STEP_CODE = 1;
    public static final int STEP_PART_CODE = 2;
    private static final String TAG = PedometerProvider.class.getSimpleName();
    private UriMatcher uriMatcher;
    private PedometerDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, STEP_PATH, STEP_CODE);
        uriMatcher.addURI(AUTHORITY, STEP_PART_PATH, STEP_PART_CODE);
        dbHelper = new PedometerDBHelper(getContext());
        return false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.v(TAG, "PedometerProvider low memory");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case STEP_CODE:
                cursor = db.query(PedometerPersistenceContract.StepEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, sortOrder);
                break;
            case STEP_PART_CODE:
                cursor = db.query(PedometerPersistenceContract.StepPartEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type = null;
        switch (uriMatcher.match(uri)) {
            case STEP_CODE:
                type = "vnd.android.cursor.dir/step";
                break;
            case STEP_PART_CODE:
                type = "vnd.android.cursor.dir/stepPart";
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri insertUri = null;
        long id = -1;
        switch (uriMatcher.match(uri)) {
            case STEP_CODE:
                id = db.insert(PedometerPersistenceContract.StepEntry.TABLE_NAME, null, contentValues);
                insertUri = ContentUris.withAppendedId(uri, id);
                break;
            case STEP_PART_CODE:
                id = db.insert(PedometerPersistenceContract.StepPartEntry.TABLE_NAME, null, contentValues);
                insertUri = ContentUris.withAppendedId(uri, id);
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case STEP_CODE:
                count = db.delete(PedometerPersistenceContract.StepEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STEP_PART_CODE:
                count = db.delete(PedometerPersistenceContract.StepPartEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case STEP_CODE:
                count = db.update(PedometerPersistenceContract.StepEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case STEP_PART_CODE:
                count = db.update(PedometerPersistenceContract.StepPartEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return count;
    }
}
