package com.example.ventas.cliente.ui.compra;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ventas.model.Carrito;
import com.example.ventas.model.Producto;
import com.example.ventas.model.Venta;
import com.example.ventas.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompraViewModel extends AndroidViewModel {
    private MutableLiveData<Venta> buscarCompraMLD;

    private final Context context;

    public CompraViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Venta> getCompraBuscar() {
        if (buscarCompraMLD == null) {
            buscarCompraMLD = new MutableLiveData<>();
        }
        return buscarCompraMLD;
    }

    public void vmBuscarVenta(int id){
        SharedPreferences sp=context.getSharedPreferences("token",0);
        String accessToken=sp.getString("token","");

        Call<Venta> dates= ApiClient.getMyApiClient().getApiBuscarVenta(accessToken, id);
        dates.enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {

                if(response.isSuccessful()){
                    Venta oVenta = response.body();
                    buscarCompraMLD.setValue(oVenta);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Venta> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void vmCrearVenta(Venta oVenta) {
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Venta> callAltaVentaItem = ApiClient.getMyApiClient().getApiAltaVenta(accessToken, oVenta);
        callAltaVentaItem.enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {
                if (response.isSuccessful()) {
                    vmBuscarVenta(response.body().getUsuarioId());

                }
                else{
                    Toast.makeText(context, "Error no agrego", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}