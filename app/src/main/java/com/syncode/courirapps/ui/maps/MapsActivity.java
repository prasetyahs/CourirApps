package com.syncode.courirapps.ui.maps;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.syncode.courirapps.R;
import com.syncode.courirapps.data.local.SystemDataLocal;
import com.syncode.courirapps.data.model.Transaction;
import com.syncode.courirapps.data.model.direction.Direction;
import com.syncode.courirapps.data.model.direction.FeatureDirection;
import com.syncode.courirapps.data.model.direction.Geometry;
import com.syncode.courirapps.ui.detail.DetailTransactionActivity;
import com.syncode.courirapps.ui.login.LoginActivity;
import com.syncode.courirapps.utils.Formula;
import com.syncode.courirapps.utils.LocationProvider;
import com.syncode.courirapps.utils.SwitchActivity;

import java.util.Collections;
import java.util.List;

import static com.syncode.courirapps.utils.LocationProvider.CODE_LOCATION_UPDATE;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, Observer<Direction> {

    private GoogleMap mapsCourier;
    private Marker courierMarker;
    private LocationRequest locationRequest;
    private LatLng locationCourier;
    private LocationProvider locationProvider;
    private MapsViewModel mapsViewModel;
    private double myLocationLat, myLocationLon;
    private Marker agentMarker;
    private LatLng locationAgent;
    private String direction;
    private SupportMapFragment mapFragment;
    private SystemDataLocal systemDataLocal;
    private ConstraintLayout rootLayout;
    private Polyline polylineDirection;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    myLocationLat = location.getLatitude();
                    myLocationLon = location.getLongitude();
                    locationCourier = new LatLng(myLocationLat, myLocationLon);
                    mapsCourier.setBuildingsEnabled(true);
                    if (courierMarker == null) {
                        courierMarker = mapsCourier.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car2)).position(locationCourier));
                    } else {
                        //MarkerAnimation.animateMarkerToGB(courierMarker, locationCourier, new LatLngInterpolator.Spherical());
                        courierMarker = mapsCourier.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car2)).position(locationCourier));
                        courierMarker.remove();
                    }
                }
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            if (locationAvailability.isLocationAvailable()) {
                mapsCourier.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocationLat, myLocationLon), 16));
            }

        }
    };

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000); //3 seconds
        locationProvider = new LocationProvider(this);
        systemDataLocal = new SystemDataLocal(this);
        mapsViewModel = ViewModelProviders.of(MapsActivity.this).get(MapsViewModel.class);
        rootLayout = findViewById(R.id.rootLayout);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationProvider.setLocationUpdate(locationRequest, locationCallback);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (polylineDirection != null) {
            polylineDirection.remove();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        mapsCourier = googleMap;
        mapsViewModel.getTransaction(systemDataLocal.getLoginData().getIdCourier()).observe(this, transactionResponse -> {
            int position = 0;
            if (transactionResponse != null) {
                if (transactionResponse.getDataTransaction().size() > 0) {
                    for (Transaction trans : transactionResponse.getDataTransaction()) {
                        String[] coor = trans.getCoordinate().split(",");
                        double latT = Double.parseDouble(coor[0]);
                        double lotT = Double.parseDouble(coor[1]);
                        double distance = Formula.haversineFormula(lotT, myLocationLon, latT, myLocationLat);
                        trans.setDistance(distance);
                        locationAgent = new LatLng(latT, lotT);
                        agentMarker = mMap.addMarker(new MarkerOptions().position(locationAgent));
                        agentMarker.setTitle(trans.getFname());
                        agentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.store));
                        agentMarker.setTag(position);
                        position++;
                    }
                    Collections.sort(transactionResponse.getDataTransaction(), (transaction, t1) -> Double.compare(transaction.getDistance(), t1.getDistance()));
                }
            }
            mMap.setOnMarkerClickListener(marker -> {
                int pos = (int) marker.getTag();
                if (pos > 1) {
                    Toast.makeText(MapsActivity.this, "Pilih Jarak Terdekat Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {
                    LatLng latLng = marker.getPosition();
                    direction = myLocationLat + "," + myLocationLon + "|" + latLng.latitude + "," + latLng.longitude;
                    mapsViewModel.getDirection(direction, "drive", "e6641af6d8454e3dbed411e83be9c0e6").observe(MapsActivity.this, MapsActivity.this);
                    if (transactionResponse != null) {
                        String fname = transactionResponse.getDataTransaction().get(pos).getFname();
                        String lname = transactionResponse.getDataTransaction().get(pos).getLname();
                        String coordinate = transactionResponse.getDataTransaction().get(pos).getCoordinate();
                        String street = transactionResponse.getDataTransaction().get(pos).getStreet();
                        String productName = transactionResponse.getDataTransaction().get(pos).getProductName();
                        String idOrder = transactionResponse.getDataTransaction().get(pos).getIdOrder();
                        String idTransaction = transactionResponse.getDataTransaction().get(pos).getIdTransaction();
                        Transaction transaction = new Transaction(fname, lname, coordinate, street, productName, idOrder, idTransaction);
                        showSnackBar(fname, street, transaction).show();
                    }
                }
                return true;
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationProvider.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_LOCATION_UPDATE) {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                locationProvider.setLocationUpdate(locationRequest, locationCallback);
            }
        }
    }

    @Override
    public void onChanged(Direction direction) {
        if (direction != null) {
            List<FeatureDirection> feature = direction.getFeatures();
            PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
            options.getPoints().clear();
            for (FeatureDirection f : feature) {
                Geometry geometry = f.getGeometry();
                List<List<List<Double>>> listCoordinate = geometry.getCoordinates();
                for (List<List<Double>> c : listCoordinate) {
                    for (List<Double> c2 : c) {
                        String[] latLotD = c2.toString().replace("[", "").replace("]", "").split(",");
                        double lotD = Double.parseDouble(latLotD[0]);
                        double latD = Double.parseDouble(latLotD[1]);
                        LatLng latLng = new LatLng(latD, lotD);
                        options.add(latLng);
                    }
                }
            }
            polylineDirection = mapsCourier.addPolyline(options);
        }
    }

    private Snackbar showSnackBar(String name, String address, Transaction transaction) {
        Snackbar snackbar = Snackbar.make(rootLayout, "", Snackbar.LENGTH_INDEFINITE);
        View view = getLayoutInflater().inflate(R.layout.snackbar_direction, null);
        snackbar.getView().setBackgroundColor(Color.WHITE);
        ((ViewGroup) snackbar.getView()).removeAllViews();
        ((ViewGroup) snackbar.getView()).addView(view);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtAddress = view.findViewById(R.id.txtAddress);
        Button btnDetail = view.findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(view1 -> SwitchActivity.mainSwitch(this, DetailTransactionActivity.class, transaction, "transaction"));
        txtName.setText(name);
        txtAddress.setText(address);

        return snackbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            systemDataLocal.destroySessionLogin();
            SwitchActivity.mainSwitch(this, LoginActivity.class);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
