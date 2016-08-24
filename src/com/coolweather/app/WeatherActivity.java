package com.coolweather.app;

import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import util.HttpCallbackListener;
import util.HttpUtil;

public class WeatherActivity extends Activity implements OnClickListener {

	private LinearLayout weatherInfoLayout;
	private TextView cityName;
	private TextView publishText;
	private TextView weatherDesp;
	private TextView currentDate;
	private Button refreshWeather;
	private Button switchCity;
	private String city_Name;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String res = (String) msg.obj;
				String[] list = res.split(",");
				cityName.setText(list[0]);
				publishText.setText(list[1]);
				weatherDesp.setText(list[2]);

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);

		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);

		cityName = (TextView) findViewById(R.id.city_name);

		publishText = (TextView) findViewById(R.id.publish_text);

		weatherDesp = (TextView) findViewById(R.id.weather_desp);

		currentDate = (TextView) findViewById(R.id.current_data);
		switchCity = (Button) findViewById(R.id.Switch_city);
		refreshWeather = (Button) findViewById(R.id.refresh_weather);
		city_Name = getIntent().getStringExtra("cityName");
		// Log.i("CityName", city_Name);
		getWeatherFromUrl();
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Switch_city:
			Intent intent =new Intent(this,ChooseAreaActivity.class);
			intent.putExtra("CityName", city_Name);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("同步中.....");
			getWeatherFromUrl();
			break;
		default:
			break;
		}
	}

	private void getWeatherFromUrl() {
		String city_Name_Url = URLEncoder.encode(city_Name);
		String address = "http://api.map.baidu.com/telematics/v3/weather?location=" + city_Name_Url
				+ "&output=json&ak=7QhGeeoOs4lKVSyacvggBXkahTNjjAjm";
		Log.i("URL", address);
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("ssss", response.toString());
				String Msg = "";
				try {
					JSONObject jsonObject = new JSONObject(response);
					String error = jsonObject.getString("error");
					String status = jsonObject.getString("status");
					String date = jsonObject.getString("date");
					JSONArray results = jsonObject.getJSONArray("results");
					if (results.length() > 0) {
						JSONObject jsonObject1 = results.getJSONObject(0);
						String currentCity = jsonObject1.getString("currentCity");
						String pm25 = jsonObject1.getString("pm25");
						JSONArray weather_data = jsonObject1.getJSONArray("weather_data");
						if (weather_data.length() > 0) {
							JSONObject jsonWeather_data = weather_data.getJSONObject(0);
							String date00 = jsonWeather_data.getString("date");

							Msg = currentCity + ",时间：" + date + "," + date00 + ",pm2.5:" + pm25 + ".";
						}
						Log.i("WEATHER", "城市：" + currentCity + ",时间：" + date + ",pm2.5:" + pm25 + ".");

					}
					Message msg = new Message();
					msg.what = 0;
					msg.obj = Msg;
					handler.sendMessage(msg);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub

			}

		});
	}

}
