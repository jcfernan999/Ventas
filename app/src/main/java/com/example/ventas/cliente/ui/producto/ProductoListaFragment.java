package com.example.ventas.cliente.ui.producto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventas.R;
import com.example.ventas.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoListaFragment extends Fragment {

    private ProductoViewModel productoViewModel;
    private RecyclerView recyclerViewListaProducto;
    private SearchView searchView ;
    private ArrayList<Producto> productoModalArrayList;
    private ProductoAdapter adapter;


    public static ProductoListaFragment newInstance() {
        return new ProductoListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_producto_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //searchView = view.findViewById(R.id.etBuscar);

        recyclerViewListaProducto = view.findViewById(R.id.rvProductoLista);
        recyclerViewListaProducto.setLayoutManager(new LinearLayoutManager(getContext()));

        productoViewModel = ViewModelProviders.of(this).get(ProductoViewModel.class);
        productoViewModel.getProductos().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                adapter = new ProductoAdapter(getContext(),productos);
                recyclerViewListaProducto.setAdapter(adapter);

                recyclerViewListaProducto.setLayoutManager(new GridLayoutManager(getContext(),2));
                //setSearchView();

            }

        });
        productoViewModel.vmListarProductos();
    }
}