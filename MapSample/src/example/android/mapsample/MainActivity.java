package example.android.mapsample;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements LocationListener, OnCameraChangeListener, OnMyLocationButtonClickListener {
	// 初期座標(スカイツリー)
	private static final LatLng START_POS = new LatLng(35.710184468125725, 139.81106221675873);
	// ポジション
	private static LatLng pos;
	// 地図
	private GoogleMap map;
	// カメラ
	private CameraUpdate camera;
	// マーカーを設置する設定
	private MarkerOptions markers;
	// locationManager取得
	private LocationManager mgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		setup();

		map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location location) {
				// 経度・緯度取得
				double lat = location.getLatitude(); // latitude
				double lon = location.getLongitude(); // longitude
				pos = new LatLng(lat, lon);
			}
		});
	}

	// 地図の設定
	private void setup() {
		// 現在地表示ボタンを有効にする
		map.setMyLocationEnabled(true);
		// カメラの基本的な設定をセット
		final CameraPosition pos = new CameraPosition(START_POS, 16, 0, 0);
		// カメラにセット
		camera = CameraUpdateFactory.newCameraPosition(pos);
		// カメラの位置に移動
		map.moveCamera(camera);
		// マーカーの準備
		markers = new MarkerOptions();
		// マーカーの座標を決定
		markers.position(START_POS);
		// マーカーを追加
		map.addMarker(markers);
	}

	// XMLレイアウトのロード
	private void findView() {
		// FragmentManagerのロード
		final FragmentManager manager = getSupportFragmentManager();
		// MapFragmentのロード
		final SupportMapFragment frag = (SupportMapFragment) manager.findFragmentById(R.id.map);
		// Map内容のロード
		map = frag.getMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// メニュー選択時の動作
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.location:
			mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) MainActivity.this);
			return true;
		case R.id.marker:
			// 地図の中心座標を取得
			CameraPosition cameraPos = map.getCameraPosition();
			pos = new LatLng(cameraPos.target.latitude,cameraPos.target.longitude);
			// マーカーの準備
			markers = new MarkerOptions();
			// マーカーの座標を決定
			markers.position(pos);
			// マーカーを追加
			map.addMarker(markers);
		default:
			break;
		}
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// 経度・緯度取得
		double lat = location.getLatitude(); // latitude
		double lon = location.getLongitude(); // longitude
		pos = new LatLng(lat, lon);
		// カメラポジション設定
		CameraPosition cameraPos = new CameraPosition.Builder().target(pos).zoom(16.0f).bearing(0).build();
		// 移動
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));

	}

	@Override
	public boolean onMyLocationButtonClick() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
