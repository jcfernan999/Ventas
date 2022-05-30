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

public class CarritoAdapterVer extends RecyclerView.Adapter<CarritoAdapterVer.ViewHolder> {
    private Context context;
    private List<Carrito> oListaCarritoAdapter;

    private OnItemClickListener listener;//nuevo
    private int cantidad;
    private double precio, total;
    public CarritoAdapterVer(Context context, List<Carrito> oCarritoItem) {
        this.oListaCarritoAdapter = oCarritoItem;
        this.context = context;

    }
    public interface OnItemClickListener{
        void onMasClick(int position);
        void onMenosClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_carrito, parent, false);
        ViewHolder evh = new ViewHolder(view,listener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carrito carritoItem = oListaCarritoAdapter.get(position);
        holder.proNombre.setText(carritoItem.getProducto().getNombre());
        holder.proPrecio.setText(carritoItem.getPrecio()+"");
        holder.proCantidad.setText(carritoItem.getCantidad()+"");
        Glide.with(context).load(carritoItem.getProducto().getImagen()).into(holder.ProImagen);
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

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            proNombre = itemView.findViewById(R.id.tvCarNombre);
            proPrecio = itemView.findViewById(R.id.tvCarPrecio);
            proCantidad = itemView.findViewById(R.id.etCarCantidad);
            ProImagen = itemView.findViewById(R.id.ivCarImg);
            menosCar = itemView.findViewById(R.id.btnCarProMenos);
            masCar = itemView.findViewById(R.id.btnCarProMas);
            cartView = itemView.findViewById(R.id.cvCarritoItems);



            masCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CarritoAdapterVer.this.listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            CarritoAdapterVer.this.listener.onMasClick(position);

                        }
                    }
                }
            });
            menosCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CarritoAdapterVer.this.listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            CarritoAdapterVer.this.listener.onMenosClick(position);
                        }
                    }
                }
            });
        }
    }

    public Carrito getCarrito(int position){

        return oListaCarritoAdapter.get(position);
    }
    public int getCanttidad(){
        return cantidad;
    }

    public double getPrecio(){
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getTotalItems(){
        int total=0;
        for (Carrito item: oListaCarritoAdapter) {
            total+=item.getPrecio();
        }

        return total;
    }


    public void setPrecioTotal(double total){
        this.total = total;
    }
    public List<Carrito> getPrecioTotal(){
        return oListaCarritoAdapter;
    }

}