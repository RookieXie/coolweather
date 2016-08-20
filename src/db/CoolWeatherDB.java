package db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.City;
import model.County;
import model.Province;

public class CoolWeatherDB {
	public static final String DB_Name="cool_weather";
	public static final int VERSION=1;
	private SQLiteDatabase db;
	private static  CoolWeatherDB coolWeatherDB;
	
	//���췽��
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context, DB_Name, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	//��ȡʵ��
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB ==null	){
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	//��Provinceʵ���洢�����ݿ�
	public void saveProvince(Province province){
		if(province !=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	//�����ݿ��ж�ȡȫ������ʡ����Ϣ
	public List<Province> loadProvinces(){
		List<Province> list= new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToNext()){
			do{
				Province province =new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	//��Cityʵ���洢�����ݿ�
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	//�����ݿ��ȡĳ��ʡ�µ�������
	public List<City> loadCities(int provinceId){
			List<City> list=new ArrayList<City>();
			Cursor cursor=db.query("City", null, null, null, null, null, null);
			if(cursor.moveToNext()){
				do{
					City city=new City();
					city.setId(cursor.getInt(cursor.getColumnIndex("id")));
					city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
					city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
					city.setProvinceId(provinceId);
					list.add(city);
					
				}while(cursor.moveToNext());
			}
			return list;
	}
	//��Countyʵ���洢�����ݿ�
		public void saveCounty(County county){
			if(county!=null){
				ContentValues values=new ContentValues();
				values.put("county_name", county.getCountyName());
				values.put("county_code", county.getCountyCode());
				values.put("city_id", county.getCityId());
				db.insert("County", null, values);
			}
		}
		//�����ݿ��ȡĳ��ʡ�µ�������
		public List<County> loadCounties(int cityId){
				List<County> list=new ArrayList<County>();
				Cursor cursor=db.query("County", null, null, null, null, null, null);
				if(cursor.moveToNext()){
					do{
						County county=new County();
						county.setId(cursor.getInt(cursor.getColumnIndex("id")));
						county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
						county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
						county.setCityId(cityId);
						list.add(county);
						
					}while(cursor.moveToNext());
				}
				return list;
		}
}
