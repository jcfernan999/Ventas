package com.example.ventas.cliente.ui.producto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.ventas.R;
import com.example.ventas.cliente.ui.carrito.CarritoViewModel;
import com.example.ventas.model.Carrito;
import com.example.ventas.model.Producto;
import com.example.ventas.model.Stock;
import com.example.ventas.model.Usuario;
import com.example.ventas.usuario.UsuarioViewModel;


public class ProductoDetalleFragment extends Fragment {
    private TextView nombre, dercripcion, precio, cantidad, tvProductoDetalleItemStock;
    private ImageView imagen;

    private ProductoViewModel productoViewModel;
    private UsuarioViewModel usuarioViewModel;
    private StockViewModel stockViewModel;
    private CarritoViewModel carritoViewModel;

    private Producto oProductoCopia;
    private Stock oStockCopia;
    private Usuario oUsuarioCopia;
    private FrameLayout flDetalleStockColor;

    private DrawerLayout drawerLayout;
    public ProductoDetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_producto_detalle, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //id = view.findViewById(R.id.tvProDetId);
        nombre = view.findViewById(R.id.tvProDetNombre);
        dercripcion = view.findViewById(R.id.tvProDetDescripcion);
        precio = view.findViewById(R.id.tvProDetPrecio);
        imagen = view.findViewById(R.id.ivProDetImg);

        tvProductoDetalleItemStock = view.findViewById(R.id.tvProductoDetalleItemStock);
        flDetalleStockColor = view.findViewById(R.id.flDetalleStockColor);

        AppCompatButton menos = view.findViewById(R.id.btnProDetMenos);
        cantidad = view.findViewById(R.id.etProDetCantidad);
        AppCompatButton mas = view.findViewById(R.id.btnProDetMas);
        Button agregar = view.findViewById(R.id.btnProDetAgregarCarrito);


        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioMLD().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario oUsuario) {

                oUsuarioCopia = oUsuario;


            }
        });

        usuarioViewModel.leer();

        productoViewModel = ViewModelProviders.of(this).get(ProductoViewModel.class);
        productoViewModel.getUnProducto().observe(getViewLifecycleOwner(), new Observer<Producto>()  {
            @Override
            public void onChanged(Producto oProducto) {
                oProductoCopia = oProducto;
                fijarDatos(oProducto);
                stockViewModel.vmBuscarStockUnProducto(oProducto.getStockId());
            }
        });
        stockViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        stockViewModel.getStockDeUnProducto().observe(getViewLifecycleOwner(), new Observer<Stock>()  {
            @Override
            public void onChanged(Stock oStock) {
                oStockCopia = oStock;
                //fijarDatos(oStock);
            }
        });
        productoViewModel.vmBuscarUnProducto(getArguments().getInt("fragProId"));
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.valueOf(cantidad.getText().toString()) > 0 &&  Integer.valueOf(cantidad.getText().toString()) < oStockCopia.getCantidad() ) {
                    //Suma cantidad
                    cantidad.setText(String.valueOf(Integer.valueOf(cantidad.getText().toString()) + 1));
                    //Cambia la precio:precio por cantidad
                    precio.setText((oProductoCopia.getPrecio() *
                            Integer.parseInt(cantidad.getText() + "")) + "");
                    //Cambia la cantidad
                    tvProductoDetalleItemStock.setText(((Integer.valueOf(tvProductoDetalleItemStock.getText().toString())) - 1) + "");
                    cambiarColorStock(Integer.parseInt(tvProductoDetalleItemStock.getText().toString()));
                }
            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.valueOf(cantidad.getText().toString())>1){
                    cantidad.setText(String.valueOf(Integer.valueOf(cantidad.getText().toString())-1));

                    precio.setText((oProductoCopia.getPrecio() *
                            Integer.parseInt(cantidad.getText()+""))+"");

                    tvProductoDetalleItemStock.setText(((Integer.valueOf(tvProductoDetalleItemStock.getText().toString()))+1)+"");
                    cambiarColorStock(Integer.parseInt(tvProductoDetalleItemStock.getText().toString()));
                }
            }
        });


        carritoViewModel = ViewModelProviders.of(this).get(CarritoViewModel.class);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea agregar al carrito?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Carrito oCarrito = new Carrito();
                        oCarrito.setId(0);
                        oCarrito.setPrecio(Double.valueOf(precio.getText()+""));
                        oCarrito.setCantidad(Integer.parseInt(cantidad.getText()+""));
                        oCarrito.setUsuarioId(oUsuarioCopia.getId());
                        oCarrito.setProductoId(oProductoCopia.getId());
                        oCarrito.setProducto(null);
                        oCarrito.setUsuario(null);
                        carritoViewModel.vmCrearCarrito(oCarrito);

                        //Navigation.findNavController(view).navigate(R.id.nav_producto);

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });

    }


    public void fijarDatos(Producto oProductoQueBusque){
        Glide.with(this).load(oProductoQueBusque.getImagen()).into(imagen);
        nombre.setText(oProductoQueBusque.getTipoProducto().getNombre()+" "+oProductoQueBusque.getNombre());
        dercripcion.setText(oProductoQueBusque.getDescripcion());
        precio.setText(oProductoQueBusque.getPrecio()+"");
        tvProductoDetalleItemStock.setText((oProductoQueBusque.getStock().getCantidad()-1)+"");


        cambiarColorStock(oProductoQueBusque.getStock().getCantidad());
    }

    public void cambiarColorStock(int stock){
        if(stock<10){
            flDetalleStockColor.setBackgroundResource(R.drawable.stock_redondo_rojo);
        }else if(stock<20 && stock>=10){
            flDetalleStockColor.setBackgroundResource(R.drawable.stock_redondo_amarillo);
        } else{

            flDetalleStockColor.setBackgroundResource(R.drawable.stock_redondo_verde);
        }
    }

    public void save(){
        oStockCopia.setId(oProductoCopia.getStockId());
        oStockCopia.setCantidad(Integer.parseInt(oStockCopia.getCantidad() +
                Integer.parseInt(cantidad.getText()+"")+""));
        stockViewModel.vmEditarStock(oStockCopia);


        oStockCopia.setId(oProductoCopia.getStockId());
        oStockCopia.setCantidad(oStockCopia.getCantidad()-1);
        stockViewModel.vmEditarStock(oStockCopia);
    }


}
