package com.example.ventas.cliente.ui.producto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ventas.R;
import com.example.ventas.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder>{
    private Context context;
    private List<Producto> productList;

    public static List<Producto> buscarProductList;




    public ProductoAdapter(Context context, List<Producto> productList) {
        this.productList = productList;
        this.context = context;
        buscarProductList = new ArrayList<>();
        buscarProductList.addAll(productList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_producto, parent,false);
        //------------------------------

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto oproducto = productList.get(position);
        holder.tvPINombre.setText(oproducto.getNombre());//tv_name
        holder.tvPIPrecio.setText(oproducto.getPrecio()+"");
        holder.tvPIStock.setText(oproducto.getStock().getCantidad()+"");
        holder.tvPITipo.setText(oproducto.getTipoProducto().getNombre()+"");
        Glide.with(context).load(oproducto.getImagen()).into(holder.ivPIImagen);
        if(oproducto.getStock().getCantidad()<10){
            holder.flCambiarDeColorStock.setBackgroundResource(R.drawable.stock_redondo_rojo);
        }else if(oproducto.getStock().getCantidad()<20 && oproducto.getStock().getCantidad()>=10){
            holder.flCambiarDeColorStock.setBackgroundResource(R.drawable.stock_redondo_amarillo);
        } else{

          holder.flCambiarDeColorStock.setBackgroundResource(R.drawable.stock_redondo_verde);
       }

        holder.btnProductoDetalle.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
             bundle.putInt("fragProId",oproducto.getId());
             Navigation.findNavController(view).navigate(R.id.nav_producto_detalle, bundle); //ir a otro fragment

         });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        //variables from my itemlayout
        TextView tvPINombre, tvPIPrecio, tvPICantidad,tvPITipo,tvPIStock;
        ImageView ivPIImagen;
        LinearLayout btnProductoDetalle;
        FrameLayout flCambiarDeColorStock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPINombre = itemView.findViewById(R.id.tvProductoItemNombre);
            tvPIPrecio = itemView.findViewById(R.id.tvProductoItemPrecio);
            tvPIStock = itemView.findViewById(R.id.tvProductoItemStock);
            tvPITipo = itemView.findViewById(R.id.tvProductoItemTipo);
            ivPIImagen = itemView.findViewById(R.id.ivProductoItemImagen);
            btnProductoDetalle = itemView.findViewById(R.id.llBotonDetalle);;
            flCambiarDeColorStock = itemView.findViewById(R.id.flStockColor);
        }
    }

    // private Bitmap getBitmapFromAsset(String strName)
    // {
    //    AssetManager assetManager = context.getAssets();
    //     InputStream istr = null;
    //    try {
    //        istr = assetManager.open(strName);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     Bitmap bitmap = BitmapFactory.decodeStream(istr);
    //     return bitmap;
    //  }


    public void filtrado(String tv_Buscar){
        int longitud = tv_Buscar.length();
        if(longitud==0){
            productList.clear();
            productList.addAll(buscarProductList);
        } else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Producto> collecion = productList.stream()
                        .filter(i->i.getNombre().toLowerCase().contains(tv_Buscar.toLowerCase()))
                        .collect(Collectors.toList());
                productList.clear();
                productList.addAll(collecion);
            }else{
                productList.clear();
                for (Producto c: buscarProductList) {
                    if(c.getNombre().toLowerCase().contains(tv_Buscar.toLowerCase())){
                        productList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.count = buscarProductList.size();
                    results.values = buscarProductList;
                } else{
                    String searchStr = constraint.toString().toUpperCase();
                    List<Producto> resultsData = new ArrayList<>();
                    for (Producto pet :
                            buscarProductList) {
                        if(pet.getNombre().toUpperCase().contains(searchStr))
                            resultsData.add(pet);
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productList = (List<Producto>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void filterList(ArrayList<Producto> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        productList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}