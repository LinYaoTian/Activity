package rdc.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rdc.app.App;

/**
 * Created by Lin Yaotian on 2018/4/14.
 */

public class UniversityUtils {

    /**
     * 读取 assets 目录下的 JSON 文件
     * @param fileName 文件名
     * @param context 上下文
     * @return String
     */
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取全国的省级行政区
     * @return Provinces类
     */
    public static List<String> getProvinces(){
        String json = getJson("provinces.json", App.getmContext());
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray provinceArray = jsonObj.getJSONArray("provinces");
            Gson gson = new Gson();
            return gson.fromJson(provinceArray.toString(),List.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取指定省级行政区的大学列表
     * @param province 省名
     * @return Universities类
     */
    public static List<String> getUniversities(String province){
        String json = getJson("campus.json", App.getmContext());
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonArray = jsonObj.getJSONArray("campus");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject provinceObj = jsonArray.getJSONObject(i);
                if (provinceObj.has(province)){
                    JSONArray universityArray = provinceObj.getJSONArray(province);
                    Gson gson = new Gson();
                    return gson.fromJson(universityArray.toString(),List.class);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  new ArrayList<>();
    }




}
