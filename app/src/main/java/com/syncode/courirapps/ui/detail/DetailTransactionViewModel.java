package com.syncode.courirapps.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.syncode.courirapps.data.model.MessageOnly;
import com.syncode.courirapps.data.model.Transaction;
import com.syncode.courirapps.data.network.repository.TransactionRepository;

public class DetailTransactionViewModel extends ViewModel {

    private TransactionRepository transactionRepository;

    public DetailTransactionViewModel() {
        this.transactionRepository = new TransactionRepository();
    }

    public LiveData<MessageOnly> getResponsesUpdateStatus(String idTransaction, int status) {
        return transactionRepository.requestUpdateStatus(idTransaction, status);
    }

    public LiveData<Transaction> getSingleTransaction(String id_transaction) {
        return transactionRepository.getSingleTransaction(id_transaction);
    }

}
