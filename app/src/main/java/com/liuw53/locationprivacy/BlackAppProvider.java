package com.liuw53.locationprivacy;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


public class BlackAppProvider extends ContentProvider {
	public static final Uri BLACK_APP_NAME_URI=Uri.parse("content://com.sysu.locationPri/blackApp");
	private DBHelper db=null;
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Log.d("BlackAppProvider_DEBUG", "onCreate()");
		//db.getWritableDatabase();
		db=new DBHelper(getContext());
		return (null!=db);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Log.d("BlackAppProvider_DEBUG", "query()");
		if(uri.equals(BLACK_APP_NAME_URI)){//δ����ָ���쳣
			return db.getWritableDatabase().query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);	
		}
		Log.d("BlackAppProvider_DEBUG", "query()___null");
		return null;
	}

	@Override
	public String getType(Uri uri) {
		Log.d("BlackAppProvider_DEBUG", "getType()");
		return uri.toString();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		Log.d("BlackAppProvider_DEBUG", "insert()");
		if(uri.equals(BLACK_APP_NAME_URI)){
			Log.d("BlackAppProvider_DEBUG", "insert()__uri.equals(BLACK_APP_NAME_URI)");
			db.getWritableDatabase().insert(DBHelper.TABLE_NAME, null, values);
		}
		return uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		Log.d("BlackAppProvider_DEBUG", "delete()");
		if(uri.equals(BLACK_APP_NAME_URI))
			return db.getWritableDatabase().delete(DBHelper.TABLE_NAME, selection, selectionArgs);
		Log.d("BlackAppProvider_DEBUG", "delete()__return 0");
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		Log.d("BlackAppProvider_DEBUG", "update()");
		if(uri.equals(BLACK_APP_NAME_URI))
			return db.getWritableDatabase().update(DBHelper.TABLE_NAME, values, selection, selectionArgs);
		Log.d("BlackAppProvider_DEBUG", "update()__return 0");
		return 0;
	}
}
