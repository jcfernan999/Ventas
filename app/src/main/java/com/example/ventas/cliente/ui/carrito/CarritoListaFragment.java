package com.example.ventas.cliente.ui.carrito;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventas.R;
import com.example.ventas.cliente.ui.compra.CompraViewModel;
import com.example.ventas.cliente.ui.producto.StockViewModel;
import com.example.ventas.detalle.DetalleViewModel;
import com.example.ventas.model.Carrito;
import com.example.ventas.model.Detalle;
import com.example.ventas.model.Stock;
import com.example.ventas.model.Usuario;
import com.example.ventas.model.Venta;
import com.example.ventas.usuario.UsuarioViewModel;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarritoListaFragment extends Fragment {

    private TextView cantidad;
    private CarritoViewModel carritoViewModel;
    private UsuarioViewModel usuarioViewModel;
    private CarritoViewModel carritoItemViewModel;
    private StockViewModel stockViewModel;
    private CompraViewModel compraViewModel;
    private DetalleViewModel detalleViewModel;

    private RecyclerView recyclerViewCarrito;
    private CarritoAdapterVer adapter;

    private Carrito oCarritoV,oCarritoUno;
    private Usuario oUsuarioCopia;
    private Venta oVentaCopia;
    private Stock oStockCopia;
    private List<Carrito> oListaCarritoCopia;


    private TextView tvTotal;

    private Button btnLimpiar, btnComprar,btnAgregar;
    private Calendar fecha;
    private int year, month, day, hour, minute, second;
    public CarritoListaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carrito_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotal = view.findViewById(R.id.tvCarTotal);
        btnComprar = view.findViewById(R.id.btnComprar);
        btnLimpiar = view.findViewById(R.id.btnLimpiar);
        btnAgregar = view.findViewById(R.id.btnAgregar);
        recyclerViewCarrito = view.findViewById(R.id.rvCarrito);
        recyclerViewCarrito.setLayoutManager(new LinearLayoutManager(getContext()));
        stockViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        stockViewModel.getStockDeUnProducto().observe(getViewLifecycleOwner(), new Observer<Stock>() {
            @Override
            public void onChanged(Stock oStock) {
                oStockCopia = oStock;
            }
        });
        carritoViewModel = ViewModelProviders.of(this).get(CarritoViewModel.class);
        carritoViewModel.getListaCarritoItemMLD().observe(getViewLifecycleOwner(), new Observer<List<Carrito>>() {
            @Override
            public void onChanged(List<Carrito> oCarritoLista) {

                adapter = new CarritoAdapterVer(getContext(),oCarritoLista);

                recyclerViewCarrito.setLayoutManager(new GridLayoutManager(getContext(),1));
                recyclerViewCarrito.setAdapter(adapter);

                double total=0;
                for (Carrito item: adapter.getPrecioTotal()) {
                    total+=item.getPrecio();
                }
                tvTotal.setText(total+"");

                adapter.setOnItemClickListener(new CarritoAdapterVer.OnItemClickListener() {


                    @Override
                    public void onMasClick(int position) {
                        Carrito aux = adapter.getCarrito(position);
                        if(aux.getProducto().getStock().getCantidad() > aux.getCantidad())
                        {
                            aux.setCantidad(aux.getCantidad()+1);
                            aux.setPrecio(Double.valueOf(aux.getProducto().getPrecio() * aux.getCantidad()));
                            carritoViewModel.vmEditarCarrito(aux);
                        }
                        double total=0;
                        for (Carrito item: adapter.getPrecioTotal()) {
                            total+=item.getPrecio();
                        }
                        tvTotal.setText(total+"");

                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onMenosClick(int position) {
                        Carrito aux = adapter.getCarrito(position);
                        if(aux.getCantidad()>1)
                        {
                            aux.setCantidad(aux.getCantidad()-1);
                            aux.setPrecio(Double.valueOf(aux.getProducto().getPrecio() * aux.getCantidad()));
                            carritoViewModel.vmEditarCarrito(aux);
                            tvTotal.setText(adapter.getPrecioTotal()+"");

                        }
                        double total=0;
                        for (Carrito item: adapter.getPrecioTotal()) {
                            total+=item.getPrecio();
                        }
                        tvTotal.setText(total+"");
                        adapter.notifyDataSetChanged();
                    }
                });


                oListaCarritoCopia = oCarritoLista;
            }
        });

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioMLD().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario oUsuario) {

                oUsuarioCopia = oUsuario;
                carritoViewModel.vmCarritoIdUsuario(oUsuario.getId());

            }
        });

        compraViewModel = ViewModelProviders.of(this).get(CompraViewModel.class);
        compraViewModel.getCompraBuscar().observe(getViewLifecycleOwner(), new Observer<Venta>() {
            @Override
            public void onChanged(Venta venta) {
                oVentaCopia = venta;
            }
        });
        usuarioViewModel.leer();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Carrito aux = adapter.getCarrito(viewHolder.getAdapterPosition());

                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Eliminar Producto?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carritoViewModel.vmEliminarCarritoItems(aux.getId());
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        adapter.notifyDataSetChanged();
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerViewCarrito);

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carritoViewModel.vmEliminarCarritoAllItems(oUsuarioCopia.getId());

            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.nav_producto_lista); //ir a otro fragment
            }
        });
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detalle oDetalle = new Detalle();
                Venta oVenta = new Venta();
                oVenta.setId(0);
                oVenta.setFecha(new Date());
                oVenta.setTotal(0);
                oVenta.setUsuarioId(oUsuarioCopia.getId());
                oVenta.setPago(null);
                oVenta.setUsuario(null);
                compraViewModel.vmCrearVenta(oVenta);


            }
        });

    }

}