package com.example.ventas.vendedor.ui.venta;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ventas.R;

public class VentaListaVendedorFragment extends Fragment {

    private VentaVendedorViewModel mViewModel;

    public static VentaListaVendedorFragment newInstance() {
        return new VentaListaVendedorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venta_lista_vendedor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VentaVendedorViewModel.class);
        // TODO: Use the ViewModel
    }

}