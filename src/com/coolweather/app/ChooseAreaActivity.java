package com.coolweather.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseAreaActivity extends Activity {

	private TextView titleText;
	private ListView listView;
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;

	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<String>();
	private List<String> provinceList = new ArrayList<String>();
	private List<String> cityList = new ArrayList<String>();
	private List<String> countyList = new ArrayList<String>();
	private String selectProvince;
	private String selectCity;
	// private String selectCounty;

	private int currentLevel;
	private String CityName;
	// 是否从天气界面跳转过来
	private boolean isFromWeatherActivity;
	// 之前选中的城市
	private String isFromWeatherCityName=null;
	private Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		isFromWeatherCityName = getIntent().getStringExtra("CityName");
		titleText = (TextView) findViewById(R.id.title_text);
		listView = (ListView) findViewById(R.id.list_view);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		btnBack = (Button) findViewById(R.id.Back_btn);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (currentLevel == LEVEL_CITY) {
					queryProvinces();
				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
				if (currentLevel == LEVEL_PROVINCE) {
					selectProvince = provinceList.get(index);
					Log.i("JSON", selectProvince);
					queryCites();
				} else if (currentLevel == LEVEL_CITY) {
					selectCity = cityList.get(index);
					// queryCounties();
					getCityName();
					if (!CityName.equals(isFromWeatherCityName)) {
						Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
						intent.putExtra("cityName", CityName);
						startActivity(intent);
						finish();
					}
				}
			}
		});
		queryProvinces();
	}

	@Override
	public void onBackPressed() {
		if (currentLevel == LEVEL_CITY) {
			queryProvinces();
		}
	}

	private void queryProvinces() {
		// TODO Auto-generated method stub

		try {
			InputStreamReader reader = new InputStreamReader(getAssets().open("city.json"));
			BufferedReader bufferReader = new BufferedReader(reader);
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferReader.close();
			reader.close();
			JSONObject jsonObject = new JSONObject(stringBuffer.toString());
			JSONArray jsonList = jsonObject.getJSONArray("city");
			dataList.clear();
			for (int i = 0; i < jsonList.length(); i++) {
				JSONObject object = jsonList.getJSONObject(i);
				String name = object.getString("item_name");
				String code = object.getString("item_code");
				// Log.i("JSon",code.toString().substring(2, 6));
				if (code.toString().substring(2, 6).equals("0000")) {
					dataList.add(name);
					provinceList.add(code);
					// Log.i("JSon", name);
				}

			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException jsone) {
			// TODO Auto-generated catch block
			jsone.printStackTrace();
		}
	}

	private void queryCites() {
		// TODO Auto-generated method stub

		try {
			InputStreamReader reader = new InputStreamReader(getAssets().open("city.json"));
			BufferedReader bufferReader = new BufferedReader(reader);
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferReader.close();
			reader.close();
			JSONObject jsonObject = new JSONObject(stringBuffer.toString());
			JSONArray jsonList = jsonObject.getJSONArray("city");
			dataList.clear();
			String titel_Text = null;
			for (int i = 0; i < jsonList.length(); i++) {
				JSONObject object = jsonList.getJSONObject(i);
				String name = object.getString("item_name");
				String code = object.getString("item_code");
				// Log.i("JSon",code.toString().substring(2, 6));
				if (code.toString().substring(0, 2).equals(selectProvince.substring(0, 2))
						&& !code.toString().substring(2, 6).equals("0000")
						&& code.toString().substring(4, 6).equals("00")) {
					dataList.add(name);
					cityList.add(code);
					// Log.i("JSon", name);
				}
				if (code.toString().equals(selectProvince.toString())) {
					titel_Text = name;
				}

			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(titel_Text);
			currentLevel = LEVEL_CITY;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException jsone) {
			// TODO Auto-generated catch block
			jsone.printStackTrace();
		}
	}

	private void queryCounties() {
		// TODO Auto-generated method stub

		try {
			InputStreamReader reader = new InputStreamReader(getAssets().open("city.json"));
			BufferedReader bufferReader = new BufferedReader(reader);
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferReader.close();
			reader.close();
			JSONObject jsonObject = new JSONObject(stringBuffer.toString());
			JSONArray jsonList = jsonObject.getJSONArray("city");
			dataList.clear();
			String titel_Text = null;
			for (int i = 0; i < jsonList.length(); i++) {
				JSONObject object = jsonList.getJSONObject(i);
				String name = object.getString("item_name");
				String code = object.getString("item_code");
				if (code.toString().equals(selectCity.toString())) {
					titel_Text = name;
				}
				if (selectCity.equals("110100") || selectCity.equals("120100") || selectCity.equals("310100")
						|| selectCity.equals("500100")) {
					// Log.i("JSon",code.toString().substring(2, 6));
					if (code.toString().substring(0, 3).equals(selectCity.substring(0, 3))
							&& !code.toString().substring(4, 6).equals("00")) {
						dataList.add(name);
						countyList.add(code);
						// Log.i("JSon", name);
					}
				} else if (code.toString().substring(0, 4).equals(selectCity.substring(0, 4))
						&& !code.toString().substring(4, 6).equals("00")) {
					dataList.add(name);
					countyList.add(code);
					// Log.i("JSon", name);
				}

			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(titel_Text);
			currentLevel = LEVEL_COUNTY;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException jsone) {
			// TODO Auto-generated catch block
			jsone.printStackTrace();
		}
	}

	private void getCityName() {
		try {
			InputStreamReader reader = new InputStreamReader(getAssets().open("city.json"));
			BufferedReader bufferReader = new BufferedReader(reader);
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = bufferReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferReader.close();
			reader.close();
			JSONObject jsonObject = new JSONObject(stringBuffer.toString());
			JSONArray jsonList = jsonObject.getJSONArray("city");
			for (int i = 0; i < jsonList.length(); i++) {
				JSONObject object = jsonList.getJSONObject(i);
				String name = object.getString("item_name");
				String code = object.getString("item_code");
				if (code.toString().equals(selectCity.toString())) {
					CityName = name;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException jsone) {
			// TODO Auto-generated catch block
			jsone.printStackTrace();
		}
	}
}
