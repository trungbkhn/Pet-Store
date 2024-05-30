package com.example.animalfood.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.CustomLatLng;
import com.example.animalfood.Model.LocalModel;
import com.example.animalfood.Repository.LocalDetailRepository;

public class EditLocalViewModel extends ViewModel {
    private final LocalDetailRepository repository;
    private MutableLiveData<LocalModel> _localHomeModel = new MutableLiveData<>();
    private MutableLiveData<LocalModel> _localCompanyModel = new MutableLiveData<>();
    private LiveData<LocalModel> localHomeModel = _localHomeModel;
    private LiveData<LocalModel> localCompanyModel = _localCompanyModel;
    private final MutableLiveData<Boolean> isLocalAdded = new MutableLiveData<>();

    public EditLocalViewModel() {
        this.repository = new LocalDetailRepository();
        fetchHomeLocal();
        fetchCompanyLocal();
    }

    public LiveData<Boolean> getIsLocalAdded() {
        return isLocalAdded;
    }

    public void addNewHomeLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        repository.addNewHomeLocal(name, phoneNumber, userLocation, building, bridge, note);
        isLocalAdded.setValue(true);
    }

    public void addNewCompanyLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        repository.addNewCompanyLocal(name, phoneNumber, userLocation, building, bridge, note);
        isLocalAdded.setValue(true);
    }

    public LiveData<LocalModel> getHomeLocal() {
        return localHomeModel;
    }

    public LiveData<LocalModel> getCompanyLocal() {
        return localCompanyModel;
    }

    private void fetchHomeLocal() {
        repository.getHomeAddress().observeForever(new Observer<LocalModel>() {
            @Override
            public void onChanged(LocalModel localModel) {
                _localHomeModel.setValue(localModel);
            }
        });
    }

    private void fetchCompanyLocal() {
        repository.getCompanyAddress().observeForever(new Observer<LocalModel>() {
            @Override
            public void onChanged(LocalModel localModel) {
                _localCompanyModel.setValue(localModel);
            }
        });
    }
    public void deleteLocal(String typeLocal){
        repository.deleteLocal(typeLocal);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.getHomeAddress().removeObserver(homeAddressObserver);
        repository.getCompanyAddress().removeObserver(companyAddressObserver);
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
