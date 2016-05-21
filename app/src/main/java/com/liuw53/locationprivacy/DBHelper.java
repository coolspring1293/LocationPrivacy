package com.liuw53.locationprivacy;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	// 数据库名称常量
	private static final String DATABASE_NAME = "BlackAppList.db";
	// 数据库版本常量
	private static final int DATABASE_VERSION = 1;
	// 表名称常量
	public static final String TABLE_NAME = "blackAppList";
	// 主关键字
	public static final String KEY = "packageName";
	// 值
	public static final String FIRST_VALUE = "appLabel";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 *  onCreate方法
		 *  1、在第一次打开数据库的时候才会走
		 *  2、在清除数据之后再次运行-->打开数据库，这个方法会走
		 *  3、没有清除数据，不会走这个方法
		 *  4、数据库升级的时候这个方法不会走
		 */
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE blackAppList(packageName TEXT PRIMARY KEY,appLabel NTEXT)");
		Log.d("DBHelper__DEBUG", "onCreate__CREATE TABLE blackAppList(packageName TEXT PRIMARY KEY,appLabel NTEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/**
		 * onUpgrade方法 
		 * 1、第一次创建数据库的时候，这个方法不会走 
		 * 2、清除数据后再次运行(相当于第一次创建)这个方法不会走
		 * 3、数据库已经存在，而且版本升高的时候，这个方法才会调用
		 */
		db.execSQL("DROP TABLE IF EXISTS blackAppList");
		onCreate(db);
	}
}
