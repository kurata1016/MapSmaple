package example.android.mapsample;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LocationListener, OnCameraChangeListener, OnMyLocationButtonClickListener {
	// �������W(�X�J�C�c���[)
	private static final LatLng START_POS = new LatLng(35.710184468125725, 139.81106221675873);
	// �|�W�V����
	private static LatLng pos;
	// �n�}
	private GoogleMap map;
	// �J����
	private CameraUpdate camera;
	// �}�[�J�[��ݒu����ݒ�
	private MarkerOptions markers;
	// �J���[�^�O
	private static final int RED = 0;
	private static final int BLUE = 1;
	private static final int YELLOW = 2;
	private static final int GREEN = 3;

	// locationManager�擾
	// private LocationManager mgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		setup();

		map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location location) {
				// �o�x�E�ܓx�擾
				double lat = location.getLatitude(); // latitude
				double lon = location.getLongitude(); // longitude
				pos = new LatLng(lat, lon);
			}
		});
	}

	// �n�}�̐ݒ�
	private void setup() {
		// ���ݒn�\���{�^����L���ɂ���
		map.setMyLocationEnabled(true);
		// �J�����̊�{�I�Ȑݒ���Z�b�g
		final CameraPosition pos = new CameraPosition(START_POS, 16, 0, 0);
		// �J�����ɃZ�b�g
		camera = CameraUpdateFactory.newCameraPosition(pos);
		// �J�����̈ʒu�Ɉړ�
		map.moveCamera(camera);
		// �}�[�J�[�̏���
		markers = new MarkerOptions();
		// �}�[�J�[�̍��W������
		markers.position(START_POS);
		// �}�[�J�[�F�ݒ�
		BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		// �}�[�J�[�F�ύX
		markers.icon(icon);
		// �}�[�J�[��ǉ�
		map.addMarker(markers);
	}

	// XML���C�A�E�g�̃��[�h
	private void findView() {
		// FragmentManager�̃��[�h
		final FragmentManager manager = getSupportFragmentManager();
		// MapFragment�̃��[�h
		final SupportMapFragment frag = (SupportMapFragment) manager.findFragmentById(R.id.map);
		// Map���e�̃��[�h
		map = frag.getMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ���j���[�I�����̓���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.marker:
			// �n�}�̒��S���W���擾
			CameraPosition cameraPos = map.getCameraPosition();
			pos = new LatLng(cameraPos.target.latitude, cameraPos.target.longitude);
			// �}�[�J�[�̏���
			markers = new MarkerOptions();
			// �}�[�J�[�̍��W������
			markers.position(pos);

			// �F�I���_�C�A���O�\��
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("�}�[�J�[�̐F��I�����Ă�������");
			String[] color = { "red", "blue", "yellow", "green" };
			builder.setSingleChoiceItems(color, 0, colorListener);
			// ����E�L�����Z���p�Ƀ{�^�����z�u //
			builder.setPositiveButton("OK", btnListener);
			builder.setNeutralButton("Cancel", btnListener);
			// �_�C�A���O�\��
			AlertDialog dialog = builder.create();
			dialog.show();

			// //�@���ݒn�擾
			// case R.id.location:
			// mgr = (LocationManager)
			// getSystemService(Context.LOCATION_SERVICE);
			// mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
			// 0,
			// (LocationListener) MainActivity.this);
			// return true;
		}
		return false;
	}

	DialogInterface.OnClickListener colorListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			BitmapDescriptor icon = null;
			switch (which) {
			case RED:
				icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
				break;
			case BLUE:
				icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
				break;
			case YELLOW:
				icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
				break;
			case GREEN:
				icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
				break;
			}
			markers.icon(icon);
		}
	};

	DialogInterface.OnClickListener btnListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				// �}�[�J�[��ǉ�
				map.addMarker(markers);
				Toast.makeText(MainActivity.this, "�}�[�J�[��ǉ����܂���", Toast.LENGTH_SHORT).show();
				break;
			case AlertDialog.BUTTON_NEUTRAL:
				Toast.makeText(MainActivity.this, "�L�����Z�����܂���", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public void onLocationChanged(Location location) {
		// // �o�x�E�ܓx�擾
		// double lat = location.getLatitude(); // latitude
		// double lon = location.getLongitude(); // longitude
		// pos = new LatLng(lat, lon);
		// // �J�����|�W�V�����ݒ�
		// CameraPosition cameraPos = new
		// CameraPosition.Builder().target(pos).zoom(16.0f).bearing(0).build();
		// // �ړ�
		// map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));

	}

	@Override
	public boolean onMyLocationButtonClick() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return false;
	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
