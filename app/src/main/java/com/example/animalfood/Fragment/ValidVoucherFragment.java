package com.example.animalfood.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.animalfood.Adapter.VoucherValidAdapter;
import com.example.animalfood.Model.VoucherModel;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.VoucherViewModel;
import com.example.animalfood.databinding.FragmentValidVoucherBinding;

import java.util.ArrayList;
import java.util.List;


public class ValidVoucherFragment extends Fragment {
    private FragmentValidVoucherBinding binding;
    private List<VoucherModel> listVoucher = new ArrayList<>();
    private VoucherViewModel viewModel;
    private VoucherValidAdapter adapter;


    public ValidVoucherFragment() {

    }

//    public static ValidVoucherFragment newInstance(String param1, String param2) {
//        ValidVoucherFragment fragment = new ValidVoucherFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(VoucherViewModel.class);
        viewModel.getListVoucher().observe(this,listVoucher->{
            this.listVoucher = listVoucher;
            Log.d("ValidFragment","list size: "+ this.listVoucher.size());
                adapter = new VoucherValidAdapter(getContext(), listVoucher);
                binding.rcvListValidVoucher.setAdapter(adapter);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentValidVoucherBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rcvListValidVoucher.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}