package resource;

import android.app.Activity;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKSearch;

public class MyPoiOverlay extends PoiOverlay {
    
    MKSearch mSearch;
    String resultAddr;

    public MyPoiOverlay(Activity activity, MapView mapView, MKSearch search,String addr) {
        super(activity, mapView);
        mSearch = search;
        resultAddr = addr;
    }
    /**   
     * 使用PoiOverlay 展示poi点，在poi被点击时发起短串请求.
     */
    @Override
    protected boolean onTap(int i) {
        super.onTap(i);
        MKPoiInfo info = getPoi(i);
        resultAddr = info.address;  	
        mSearch.poiDetailShareURLSearch(info.uid);
        if (info.hasCaterDetails) {
            mSearch.poiDetailSearch(info.uid);
        }
        return true;
    }    
}

