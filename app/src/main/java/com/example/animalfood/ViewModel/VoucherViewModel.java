package com.example.animalfood.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.VoucherModel;
import com.example.animalfood.Repository.VoucherRepository;

import java.util.List;

public class VoucherViewModel extends ViewModel {
    private MutableLiveData<List<VoucherModel>> _listVoucher = new MutableLiveData<>();
    public LiveData<List<VoucherModel>> listVoucher = _listVoucher;
    private VoucherRepository voucherRepository = new VoucherRepository();
    public void VoucherRepository(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    public LiveData<List<VoucherModel>> getListVoucher(){
        listVoucher = voucherRepository.getAllListVoucher();
        return listVoucher;
    }
}
