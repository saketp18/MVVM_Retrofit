package com.lite.main.mvvm.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.lite.main.mvvm.models.ErrorResponse;
import com.lite.main.mvvm.models.ImageSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class Repository {

    private static Repository repository = null;
    private MutableLiveData<List<ImageSource>> data = new MutableLiveData<>();
    private MutableLiveData<ErrorResponse> error = new MutableLiveData<>();

    private Repository() {
    }

    public static Repository getRepository(){
        if(repository == null){
            repository = new Repository();
        }
        return repository;
    }

    public LiveData<List<ImageSource>> getLiveData(){
        return data;
    }

    public LiveData<ErrorResponse> getError() {
        return error;
    }

    public void getData(int page, int pageSize){
        Call<List<ImageSource>> call = RetrofitClientInstance.getClient().getImages(page);
        call.enqueue(new Callback<List<ImageSource>>() {
            @Override
            public void onResponse(Call<List<ImageSource>> call, Response<List<ImageSource>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setError(response.errorBody().toString());
                    errorResponse.setStatus(ErrorResponse.STATUS.RESPONSE_FAIL.ordinal());
                    error.setValue(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<List<ImageSource>> call, Throwable t) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setError(t.getMessage());
                errorResponse.setStatus(ErrorResponse.STATUS.FAILURE.ordinal());
                error.setValue(errorResponse);
            }
        });
    }
}
