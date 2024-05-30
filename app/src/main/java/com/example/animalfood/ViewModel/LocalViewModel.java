package com.example.animalfood.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.LocalModel;
import com.example.animalfood.Repository.LocalDetailRepository;

public class LocalViewModel extends ViewModel {
    private LocalDetailRepository localDetailRepository;
    private MutableLiveData<LocalModel> _localHomeModel = new MutableLiveData<>();
    private MutableLiveData<LocalModel> _localCompanyModel = new MutableLiveData<>();
    private LiveData<LocalModel> localHomeModel = _localHomeModel;
    private LiveData<LocalModel> localCompanyModel = _localCompanyModel;

    public LocalViewModel(LocalDetailRepository localDetailRepository) {
        this.localDetailRepository = localDetailRepository;
        fetchHomeLocal();
        fetchCompanyLocal();
    }

    public LiveData<LocalModel> getHomeLocal() {
        return localHomeModel;
    }

    public LiveData<LocalModel> getCompanyLocal() {
        return localCompanyModel;
    }

    private void fetchHomeLocal() {
        localDetailRepository.getHomeAddress().observeForever(new Observer<LocalModel>() {
            @Override
            public void onChanged(LocalModel localModel) {
                _localHomeModel.setValue(localModel);
            }
        });
    }

    private void fetchCompanyLocal() {
        localDetailRepository.getCompanyAddress().observeForever(new Observer<LocalModel>() {
            @Override
            public void onChanged(LocalModel localModel) {
                _localCompanyModel.setValue(localModel);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        localDetailRepository.getHomeAddress().removeObserver(homeAddressObserver);
        localDetailRepository.getCompanyAddress().removeObserver(companyAddressObserver);
    }

    private final Observer<LocalModel> homeAddressObserver = new Observer<LocalModel>() {
        @Override
        public void onChanged(LocalModel localModel) {
            _localHomeModel.setValue(localModel);
        }
    };

    private final Observer<LocalModel> companyAddressObserver = new Observer<LocalModel>() {
        @Override
        public void onChanged(LocalModel localModel) {
            _localCompanyModel.setValue(localModel);
        }
    };
}


