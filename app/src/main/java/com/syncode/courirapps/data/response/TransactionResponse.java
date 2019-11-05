package com.syncode.courirapps.data.response;

import com.google.gson.annotations.SerializedName;
import com.syncode.courirapps.data.model.Transaction;

import java.util.List;

public class TransactionResponse {

    @SerializedName("data_transaksi")
    private List<Transaction> dataTransaction;

    @SerializedName("status")
    private boolean status;

    public TransactionResponse(List<Transaction> dataTransaction, boolean status) {
        this.dataTransaction = dataTransaction;
        this.status = status;
    }

    public List<Transaction> getDataTransaction() {
        return dataTransaction;
    }

    public boolean getStatus() {
        return status;
    }
}
