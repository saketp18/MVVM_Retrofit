package com.lite.main.mvvm.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.lite.main.mvvm.repo.Repository;

import java.util.List;


public class NewsModel extends ViewModel {

    private LiveData<List<ImageSource>> mutableLiveData;
    private Repository repository = null;
    private LiveData<ErrorResponse> isError;
    private MediatorLiveData<ErrorResponse> mediatorLiveData = new MediatorLiveData<>();

    public void init(int page, int pageSize){
        repository = Repository.getRepository();
        loadData(page, pageSize);
    }

    public LiveData<List<ImageSource>> getData(){
        return mutableLiveData;
    }

    public void loadData(int page, int pageSize){
        repository.getData(page, pageSize);
        mutableLiveData = repository.getLiveData();
        isError = repository.getError();
    }

    public LiveData<ErrorResponse> getError(){
        mediatorLiveData.addSource(isError, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                if(errorResponse.getStatus() == ErrorResponse.STATUS.RESPONSE_FAIL.ordinal()){
                    errorResponse.setMessage("Response from server finished!!!");
                }else if(errorResponse.getStatus() == ErrorResponse.STATUS.FAILURE.ordinal()){
                    errorResponse.setMessage("Internet connection not stable!!!");
                }else{
                    errorResponse.setMessage("Please retry again!!!!");
                }
                mediatorLiveData.setValue(errorResponse);
            }
        });
        return mediatorLiveData;
    }
}
