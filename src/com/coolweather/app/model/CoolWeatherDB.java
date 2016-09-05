package com.coolweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.util.CoolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	
	/**
	 * 数据库版本
	 */
	
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	public CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return  coolWeatherDB;

	}
	
	/**
	 * 将Province 实例存储到数据库
	 */
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	/**
	 * 从数据库读取全国所有的省份信息
	 */
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor  cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				int provinceId = cursor.getInt(cursor.getColumnIndex("id"));
				String provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
				String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
				province.setId(provinceId);
				province.setProvinceName(provinceName);
				province.setProvinceCode(provinceCode);
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	/**
	 * 将City实例存储到数据库
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values =  new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	/**
	 * 从数据库读取对应的省份的城市信息
	 */
	public  List<City> loadsCitys(Integer provinceId){
		List<City> list = new ArrayList<City>();
		
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
				String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
				city.setId(id);
				city.setCityName(cityName);
				city.setCityCode(cityCode);
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
		
	}
	/**
	 * 将county实例存储到数据库钟
	 */
	
	public void saveCountys(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	/**
	 * 从数据库读取所有城市对应的县城信息
	 */
	
	public List<County> loadCountys(int cityId){
		List<County> list = new ArrayList<County>();
		
		Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do {
				County county = new County();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String countyName = cursor.getString(cursor.getColumnIndex("county_name"));
				String countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
				county.setCityId(cityId);
				county.setCountyCode(countyCode);
				county.setCountyName(countyName);
				county.setId(id);
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
	
	
}
