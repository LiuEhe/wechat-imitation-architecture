package com.example.myapplication;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static com.qweather.sdk.view.QWeather.getGeoCityLookup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Range;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;


import androidx.fragment.app.Fragment;


public class contacts extends Fragment {
    Button btn1;
    EditText et1;

    TextView tv_wd,tv_tq,tv_fx,tv_id;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contracts, container, false);

        btn1=(Button)view.findViewById(R.id.btn1);
        et1=(EditText)view.findViewById(R.id.et1);
        tv_fx=(TextView) view.findViewById(R.id.tv_fx);
        tv_tq=(TextView) view.findViewById(R.id.tv_tq);
        tv_wd=(TextView) view.findViewById(R.id.tv_wd);
        tv_id=(TextView) view.findViewById(R.id.tv_id);
        final String[] area = {et1.getText().toString()};
        HeConfig.init("HE2306101421311691", "49e269f5762d42389d42942bef4cb8f3");
        HeConfig.switchToDevService();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string="";
                //如果为空值无动作，非空开始查询。
                if(string.equals(et1.getText().toString())){
                }else{

                    getCity();

                }
            }
        });
//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void getCity() {
        //获取输入框内容
        String inputct = et1.getText().toString();
        //此方法为和风提供
        QWeather.getGeoCityLookup(getActivity(), inputct, new QWeather.OnResultGeoListener() {
            public static final String TAG = "he_feng_city";

            //如果提供数据有问题显示
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ", e);
                System.out.println("Weather Now Error:" + new Gson());
            }

            //如果返回结果正确则执行
            @Override
            public void onSuccess(GeoBean geoBean) {
                if (Code.OK == geoBean.getCode()) {//getLocationBean
                    String id = geoBean.getLocationBean().get(0).getId();
                    String name = geoBean.getLocationBean().get(0).getName();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_id.setText(id);
                            QWeather.getWeatherNow(getActivity(), id, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
                                public static final String TAG = "he_feng_now";

                                @Override
                                public void onError(Throwable e) {
                                    Log.i(TAG, "onError: ", e);
                                    System.out.println("Weather Now Error:");
                                }

                                @Override
                                public void onSuccess(WeatherNowBean weatherBean) {
                                    //Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
                                    System.out.println("获取天气成功： ");
                                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                                    String tianqi = now.getText();
                                    String wendu = now.getTemp() + "℃";
                                    String fengxiang = now.getWindDir();
                                    tv_tq.setText("当前天气:" + tianqi);
                                    tv_wd.setText("当前温度:" + wendu);
                                    tv_fx.setText("风向：" + fengxiang);
                                }
                            });
                        }
                    });
                } else {
                    //在此查看返回数据失败的原因
                    Code code = geoBean.getCode();
                    System.out.println("失败代码: " + code);
                    //Log.i(TAG, "failed code: " + code);
                }
            }
        });
    }

//    public void getCity(){
//        //获取输入框内容
//        String inputct =et1.getText().toString();
//        //此方法为和风提供
//        QWeather.getGeoCityLookup(contacts.this, inputct,  new QWeather.OnResultGeoListener(){
//            public static final String TAG="he_feng_city";
//            //如果提供数据有问题显示
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError: ", e);
//                System.out.println("Weather Now Error:"+new Gson());
//            }
//            //如果返回结果正确则执行
//            @Override
//            public void onSuccess(GeoBean geoBean) {
//                if (Code.OK == geoBean.getCode()) {//getLocationBean
//                    String id=geoBean.getLocationBean().get(0).getId();
//                    String name = geoBean.getLocationBean().get(0).getName();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tv_id.setText(id);
//                            QWeather.getWeatherNow(contacts.this, id, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
//                                public static final String TAG = "he_feng_now";
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    Log.i(TAG, "onError: ", e);
//                                    System.out.println("Weather Now Error:");
//                                }
//
//                                @Override
//
//
//                                public void onSuccess(WeatherNowBean weatherBean) {
//                                    //Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
//                                    System.out.println("获取天气成功： ");
//                                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
//                                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
//                                    String tianqi = now.getText();
//                                    String wendu = now.getTemp() + "℃";
//                                    String fengxiang = now.getWindDir();
//                                    tv_tq.setText("当前天气:" + tianqi);
//                                    tv_wd.setText("当前温度:" + wendu);
//                                    tv_fx.setText("风向：" + fengxiang);
//
//                                }
//                            });
//
//                        }
//                    });
//                }else{
//                    //在此查看返回数据失败的原因
//                    Code code = geoBean.getCode();
//                    System.out.println("失败代码: " + code);
//                    //Log.i(TAG, "failed code: " + code);
//                }
//            }
//        });
//    }
}