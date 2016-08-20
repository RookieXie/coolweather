/**
 * 
 */
package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author xie
 *
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	//创建Province表语句
	public static final String CREATE_PROVINCE="create table Province("
			+"id integer primary key autoincrement,"
			+"province_name text,"
			+"province_code text)";
	
	//创建City表语句
	public static final String CREATE_CITY="create table City("
			+"id integer primary key autoincrement"
			+"city_name text,"
			+"city_code text,"
			+"province_id integer";
	
	//创建County表语句
		public static final String CREATE_COUNTY="create table County("
				+"id integer primary key autoincrement"
				+"county_name text,"
				+"county_code text,"
				+"city_id integer";
	public CoolWeatherOpenHelper(Context context,String name,CursorFactory factory,int version){
		super(context,name,factory,version);
		
	}
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//执行语句
		db.execSQL(CREATE_PROVINCE); 
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);

	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
