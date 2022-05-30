package com.example.ventas.cliente.ui.producto;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ventas.model.Producto;
import com.example.ventas.model.Stock;
import com.example.ventas.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends AndroidViewModel {

    private MutableLiveData<List<Producto>> allProductos;
    private MutableLiveData <Stock> mldStockDeUnProducto;

    private final Context context;
    public StockViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Producto>> getProductos() {
        if (allProductos == null) {
            allProductos = new MutableLiveData<>();
        }
        return allProductos;
    }

    public LiveData<Stock> getStockDeUnProducto() {
        if (mldStockDeUnProducto == null) {
            mldStockDeUnProducto = new MutableLiveData<>();
        }
        return mldStockDeUnProducto;
    }



    public void vmBuscarStockUnProducto(int id){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Stock> buscarProducto = ApiClient.getMyApiClient().getApiBuscarStockDeUnProducto(accessToken, id);
        buscarProducto.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if (response.isSuccessful()) {
                    Stock stock = response.body();
                    mldStockDeUnProducto.setValue(stock);
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void vmEditarStock(Stock oStock){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Stock> callStockCarritoItem = ApiClient.getMyApiClient().getApiEditarStock(accessToken, oStock.getId(), oStock);
        callStockCarritoItem.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if (response.isSuccessful()) {
                    Stock stock = response.body();
                    mldStockDeUnProducto.setValue(stock);

                }
                else{
                    Toast.makeText(context, "Usuario no editado!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}