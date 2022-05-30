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
import com.example.ventas.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Producto>> allProductos;
    private MutableLiveData <Producto> unProducto;

    private final Context context;
    public ProductoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Producto>> getProductos() {
        if (allProductos == null) {
            allProductos = new MutableLiveData<>();
        }
        return allProductos;
    }

    public LiveData<Producto> getUnProducto() {
        if (unProducto == null) {
            unProducto = new MutableLiveData<>();
        }
        return unProducto;
    }


    public void vmListarProductos(){
        SharedPreferences sp=context.getSharedPreferences("token",0);
        String accessToken=sp.getString("token","");

        Call<List<Producto>> dates= ApiClient.getMyApiClient().getApiListarProductos(accessToken);
        dates.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {

                if(response.isSuccessful()){
                    List<Producto> producto = response.body();
                    allProductos.setValue(producto);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void vmBuscarUnProducto(int id){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Producto> buscarProducto = ApiClient.getMyApiClient().getApiBuscarUnProducto(accessToken, id);
        buscarProducto.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Producto producto = response.body();
                    unProducto.setValue(producto);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}