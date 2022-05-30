package com.example.ventas.cliente.ui.carrito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ventas.R;
import com.example.ventas.model.Carrito;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {
    private Context context;
    private List<Carrito> oListaCarritoAdapter;

    public List<Carrito> getoListaCarritoMoficada;

    private int cantidad,precio,total;
    public CarritoAdapter(Context context, List<Carrito> oCarritoItem) {
        this.oListaCarritoAdapter = oCarritoItem;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_carrito, parent, false);
        //------------------------------

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carrito carritoItem = oListaCarritoAdapter.get(position);

        holder.proNombre.setText(carritoItem.getProducto().getNombre());
        holder.proPrecio.setText(carritoItem.getPrecio()+"");

        holder.proCantidad.setText(carritoItem.getCantidad()+"");

        Glide.with(context).load(carritoItem.getProducto().getImagen()).into(holder.ProImagen);
        cantidad = carritoItem.getCantidad();




    }

    @Override
    public int getItemCount() {
        return oListaCarritoAdapter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //variables from my itemlayout
        TextView proNombre, proPrecio, proCantidad, proStock;
        ImageView ProImagen;
        AppCompatButton menosCar;
        AppCompatButton masCar;
        CardView cartView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proNombre = itemView.findViewById(R.id.tvCarNombre);
            proPrecio = itemView.findViewById(R.id.tvCarPrecio);
            proCantidad = itemView.findViewById(R.id.etCarCantidad);
            ProImagen = itemView.findViewById(R.id.ivCarImg);
            menosCar = itemView.findViewById(R.id.btnCarProMenos);
            masCar = itemView.findViewById(R.id.btnCarProMas);
            cartView = itemView.findViewById(R.id.cvCarritoItems);

        }
    }


    public int getCant(){
        return cantidad;
    }
    public int getPrecio(){
        return precio;
    }
    public int getTotal(){
        return total;
    }

    public Carrito getCarritoItem(int position){

        return oListaCarritoAdapter.get(position);
    }
    public int getTotalItems(){
        int total=0;
        for (Carrito item: oListaCarritoAdapter) {
            total+=item.getPrecio();
        }

        return total;
    }


}
