package com.example.ventas.vendedor.ui.venta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ventas.R;


public class VentaDetalleVendedorFragment extends Fragment {

    public VentaDetalleVendedorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venta_detalle_vendedor, container, false);
    }
}