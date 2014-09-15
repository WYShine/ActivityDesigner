package resource;


import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

   //继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay{

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}
		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			//处理点击事件,弹出泡泡
			return true;
		}

}
