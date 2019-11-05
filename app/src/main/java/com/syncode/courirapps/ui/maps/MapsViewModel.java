package com.syncode.courirapps.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.syncode.courirapps.data.model.direction.Direction;
import com.syncode.courirapps.data.network.repository.DirectionRepository;
import com.syncode.courirapps.data.network.repository.TransactionRepository;
import com.syncode.courirapps.data.response.TransactionResponse;

public class MapsViewModel extends ViewModel {

    private DirectionRepository directionRepository;
    private TransactionRepository transactionRepository;

    public MapsViewModel() {
        this.directionRepository = new DirectionRepository();
        transactionRepository = new TransactionRepository();
    }

    public LiveData<Direction> getDirection(String wayPoints, String mode, String apiKey) {
        return directionRepository.getDirection(wayPoints, mode, apiKey);
    }

    public LiveData<TransactionResponse> getTransaction(String idCourier) {
        return transactionRepository.getResponseMutableLiveData(idCourier);
    }

}
