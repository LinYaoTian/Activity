package rdc.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rdc.bean.Activity;
import rdc.bean.Trip;

import static rdc.configs.TripItemType.sACTIVITY;

/**
 * Created by asus on 18-4-17.
 */

public class SeparateActivityUtil {
    private static SeparateActivityUtil separateActivityUtil;
    private static final String TAG = "SeparateActivityUtil";
    private  Map<String,List<Trip>> mMap = new HashMap<>();

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

    public  Map separate(List<Activity> unSeparatedList){
          for (int i=0;i<unSeparatedList.size();i++){
              Activity activity = unSeparatedList.get(i);
              String time = activity.getTime().substring(5,10);
              Log.e(TAG, "separate: "+time );
              Trip trip = new Trip();
              trip.setTitle(activity.getTitle());
              trip.setTime(activity.getTime());
              trip.setSawNum(activity.getSawnum());
              trip.setLocation(activity.getPlace());
              trip.setType(sACTIVITY);
              Log.e(TAG, "separate: "+mMap.containsKey(time) );

              if (!mMap.containsKey(time)){
                  List<Trip> newList = new ArrayList<>();
                  newList.add(trip);
                  mMap.put(time,newList);
              }else {
                  mMap.get(time).add(trip);
              }

          }
          return mMap;
    }

    public  List<Trip> getListByDate(String date){

       return mMap.containsKey(date)==true?mMap.get(date):new ArrayList<Trip>();
    }

}
