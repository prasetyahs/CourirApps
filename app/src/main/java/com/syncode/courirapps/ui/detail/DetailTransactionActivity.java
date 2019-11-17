package com.syncode.courirapps.ui.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.syncode.courirapps.R;
import com.syncode.courirapps.data.local.SystemDataLocal;
import com.syncode.courirapps.data.model.Transaction;
import com.syncode.courirapps.data.network.repository.FirebaseRepository;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class DetailTransactionActivity extends AppCompatActivity {

    private ImageView imgPhone, imgMaps, imgSms;
    private TextView txtName, txtAddress, txtProductName, txtProductQuality, txtOrderAmount, txtPriceAll;
    private SystemDataLocal systemDataLocal;
    private ProSwipeButton proSwipeBtn;
    private FirebaseRepository firebaseRepository;
    private DetailTransactionViewModel detailTransactionViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        proSwipeBtn = findViewById(R.id.swipeEnd);
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
        txtOrderAmount = findViewById(R.id.txtOrderAmount);
        txtPriceAll = findViewById(R.id.txtPriceAll);
        systemDataLocal = new SystemDataLocal(this);
        firebaseRepository = new FirebaseRepository(this);
        detailTransactionViewModel = ViewModelProviders.of(this).get(DetailTransactionViewModel.class);
        Intent intent = getIntent();
        Transaction transaction = intent.getParcelableExtra("transaction");
        if (transaction != null) {
           setDetailTransaction(transaction);
        } else {
            detailTransactionViewModel.getSingleTransaction(systemDataLocal.getIdTransaction()).observe(this, transaction1 -> {
                if (transaction1 != null) {
                    setDetailTransaction(transaction1);
                }
            });
        }


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


    @SuppressLint("SetTextI18n")
    private void setDetailTransaction(Transaction transaction) {
        txtName.setText("Nama : ".concat(transaction.getFname()+" "+transaction.getLname()));
        txtAddress.setText("Alamat : ".concat(transaction.getStreet()));
        txtProductName.setText("Nama Product : ".concat(transaction.getProductName()));
        txtOrderAmount.setText("Jumlah Order : ".concat(String.valueOf(transaction.getOrderAmount())));
        txtProductQuality.setText("Quality : ".concat(transaction.getQuality()));
        txtPriceAll.setText("Harga Total : Rp.".concat(String.valueOf(transaction.getTotalTransaction())));
        imgSms.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:" + transaction.getPhone()));
            startActivity(smsIntent);
        });

        imgPhone.setOnClickListener(view -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:" + transaction.getPhone()));
            startActivity(phoneIntent);
        });

        imgMaps.setOnClickListener(view -> {
            String[] latLon = transaction.getCoordinate().split(",");
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLon[0] + "," + latLon[1]);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });


        proSwipeBtn.setOnSwipeListener(() -> new Handler().postDelayed(() -> {
            firebaseRepository.deleteTrackingCoordinate(transaction.getIdTransaction());
            detailTransactionViewModel.getResponsesUpdateStatus(transaction.getIdTransaction(), 3).observe(this, messageOnly -> {
                if (messageOnly.isStatus()) {
                    onBackPressed();
                }
            });
            systemDataLocal.destroyStatus();
            proSwipeBtn.showResultIcon(true, true);
        }, 2000));
    }
}
