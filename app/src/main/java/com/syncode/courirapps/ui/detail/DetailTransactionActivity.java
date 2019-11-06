package com.syncode.courirapps.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.syncode.courirapps.R;
import com.syncode.courirapps.data.local.SystemDataLocal;
import com.syncode.courirapps.data.model.MessageOnly;
import com.syncode.courirapps.data.model.Transaction;
import com.syncode.courirapps.data.network.repository.FirebaseRepository;
import com.syncode.courirapps.ui.maps.MapsViewModel;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class DetailTransactionActivity extends AppCompatActivity {

    private ImageView imgPhone, imgMaps, imgSms;
    private TextView txtName, txtAddress, txtProductName, txtDistance, txtProductQuality;

    private String coordinate;

    private SystemDataLocal systemDataLocal;
    private FirebaseRepository firebaseRepository;
    private MapsViewModel mapsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        ProSwipeButton proSwipeBtn = findViewById(R.id.swipeEnd);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Pengantaran");
        }
        imgMaps = findViewById(R.id.imgMaps);
        imgPhone = findViewById(R.id.imgPhone);
        imgSms = findViewById(R.id.imgSms);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductQuality = findViewById(R.id.txtProductQuality);
        systemDataLocal = new SystemDataLocal(this);
        firebaseRepository = new FirebaseRepository(this);
        Intent intent = getIntent();
        Transaction transaction = intent.getParcelableExtra("transaction");
        if (transaction != null) {
            txtName.setText("Nama : ".concat(transaction.getFname()).concat(transaction.getLname()));
            txtAddress.setText("Alamat : ".concat(transaction.getStreet()));
            txtProductName.setText("Nama : ".concat(transaction.getProductName()));
            coordinate = transaction.getCoordinate();
        }

        imgMaps.setOnClickListener(view -> {
            String[] latLon = coordinate.split(",");
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLon[0] + "," + latLon[1]);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        imgSms.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:089506277248"));
            startActivity(smsIntent);
        });

        imgPhone.setOnClickListener(view -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:089506277284"));
            startActivity(phoneIntent);
        });

        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel.class);
        proSwipeBtn.setOnSwipeListener(() -> new Handler().postDelayed(() -> {
            if (transaction != null) {
                firebaseRepository.deleteTrackingCoordinate(transaction.getIdTransaction());
                mapsViewModel.getResponsesUpdateStatus(transaction.getIdTransaction(), 3).observe(this, new Observer<MessageOnly>() {
                    @Override
                    public void onChanged(MessageOnly messageOnly) {
                        if (messageOnly.isStatus()) {
                            onBackPressed();
                        }
                    }
                });
                systemDataLocal.destroyStatus();
            }
            proSwipeBtn.showResultIcon(true, true);
        }, 2000));
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (systemDataLocal.getStatusCourier()) {
            Toast.makeText(this, "Selesaikan Pengiriman Terlebih Dahulu", Toast.LENGTH_LONG).show();
        } else {
            onBackPressed();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (systemDataLocal.getStatusCourier()) {
            Toast.makeText(this, "Selesaikan Pengiriman Terlebih Dahulu", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }
}
