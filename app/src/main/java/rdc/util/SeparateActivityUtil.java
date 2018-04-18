package rdc.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rdc.bean.Activity;
import rdc.bean.Trip;

import static rdc.configs.TripItemType.sACTIVITY;
import static rdc.configs.TripItemType.sDIVIDER;
import static rdc.configs.TripItemType.sEMPTY;

/**
 * Created by asus on 18-4-17.
 */

public class SeparateActivityUtil {
    private static SeparateActivityUtil separateActivityUtil;
    private static final String TAG = "SeparateActivityUtil";
    private  Map<String,List<Trip>> mTripMap = new HashMap<>();
    private Map<String,List<Trip>> mAllActivityMap = new HashMap<>();
    private List<Trip> mTripList = new ArrayList<>();
    private List<Trip> mAllTripList = new ArrayList<>();

    public static SeparateActivityUtil getInstance() {
        if (separateActivityUtil == null) {
            synchronized (SeparateActivityUtil.class) {
                if (separateActivityUtil == null) {
                    separateActivityUtil = new SeparateActivityUtil();
                }
            }
        }
        return separateActivityUtil;
    }

    public  Map separate(List<Trip> unSeparatedList){
        mTripList.addAll(unSeparatedList);
          for (int i=0;i<unSeparatedList.size();i++){
              Trip trip = unSeparatedList.get(i);
              String time = unSeparatedList.get(i).getTime();
              if (!mTripMap.containsKey(time)){
                  List<Trip> newList = new ArrayList<>();
                  newList.add(trip);
                  mTripMap.put(time,newList);
              }else {
                  mTripMap.get(time).add(trip);
              }

          }
          return mTripMap;
    }

    public  Map separateAll(List<Trip> unSeparatedList){
        mAllTripList.addAll(unSeparatedList);
        for (int i=0;i<mAllTripList.size();i++){
            Trip trip = mAllTripList.get(i);
            String time = trip.getTime();
            trip.setType(sACTIVITY);
            if (!mAllActivityMap.containsKey(time)){
                List<Trip> newList = new ArrayList<>();
                Trip divider = new Trip();
                divider.setType(sDIVIDER);
                newList.add(divider);
                newList.add(trip);
                mAllActivityMap.put(time,newList);
            }else {
                mAllActivityMap.get(time).add(trip);
            }

        }
        return mAllActivityMap;
    }

    public  List<Trip> getListByDate(String date){
             if (mTripMap.containsKey(date)){
                 return mTripMap.get(date);
             }else {
                 Trip trip = new Trip();
                 trip.setType(sEMPTY);
                 ArrayList arrayList = new ArrayList();
                 arrayList.add(trip);
                 return arrayList;
             }

    }

    public  List<Trip> getRecommendListByDate(String date){
        if (mAllActivityMap.containsKey(date)){
            return mAllActivityMap.get(date);
        }else {
            ArrayList arrayList = new ArrayList();
            return arrayList;
        }

    }

    public void release(){
        if (mTripMap!=null){
            mTripMap.clear();
        }
        if (mTripList!=null){
            mTripList.clear();
        }
        if (mAllTripList!=null){
            mAllTripList.clear();
        }
        if (mAllActivityMap!=null){
            mAllActivityMap.clear();
        }
    }


}
