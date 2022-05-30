package com.example.ventas.cliente.ui.carrito;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ventas.model.Carrito;
import com.example.ventas.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Carrito>> listaCarritoItemMLD;

    private SharedPreferences sp;
    private final Context context;

    public CarritoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        sp = context.getSharedPreferences("token",0);
    }

    public LiveData<List<Carrito>> getListaCarritoItemMLD() {
        if (listaCarritoItemMLD == null) {
            listaCarritoItemMLD = new MutableLiveData<>();
        }
        return listaCarritoItemMLD;
    }

    public void vmCarritoIdUsuario(int id){
        String accessToken = sp.getString("token", "");
        Call<List<Carrito>> carritoItemCall = ApiClient.getMyApiClient().getApiListarCarrito(accessToken,id);
        carritoItemCall.enqueue(new Callback<List<Carrito>>() {
            @Override
            public void onResponse(Call<List<Carrito>> call, Response<List<Carrito>> response) {

                if (response.isSuccessful()) {
                    List<Carrito> oCarrito = response.body();
                    listaCarritoItemMLD.postValue(oCarrito);
                }
            }
            @Override
            public void onFailure(Call<List<Carrito>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void vmCrearCarrito(Carrito oCarrito) {
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Carrito> callAltaCarritoItem = ApiClient.getMyApiClient().getApiAltaCarrito(accessToken, oCarrito);
        callAltaCarritoItem.enqueue(new Callback<Carrito>() {
            @Override
            public void onResponse(Call<Carrito> call, Response<Carrito> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "Error no agrego", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Carrito> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void vmEditarCarrito(Carrito oCarrito){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Carrito> callStockCarritoItem = ApiClient.getMyApiClient().getApiEditarCarrito(accessToken, oCarrito.getId(), oCarrito);
        callStockCarritoItem.enqueue(new Callback<Carrito>() {
            @Override
            public void onResponse(Call<Carrito> call, Response<Carrito> response) {
                if (response.isSuccessful()) {
                    vmCarritoIdUsuario(response.body().getUsuarioId());
                }
                else{
                    Toast.makeText(context, "Stock no Modificado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Carrito> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void vmEliminarCarritoItems(int id){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Carrito> callBajaCarritoItem = ApiClient.getMyApiClient().getApiBajaCarritoItem(accessToken, id);
        callBajaCarritoItem.enqueue(new Callback<Carrito>() {
            @Override
            public void onResponse(Call<Carrito> call, Response<Carrito> response) {
                if (response.isSuccessful()) {
                    vmCarritoIdUsuario(response.body().getUsuarioId());
                }
                else{
                    Toast.makeText(context, "Producto no Eliminado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Carrito> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void vmEliminarCarritoAllItems(int id){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<List<Carrito>> callBajaCarritoItemAll = ApiClient.getMyApiClient().getApiBajaCarritoAllItem(accessToken, id);
        callBajaCarritoItemAll.enqueue(new Callback<List<Carrito>>() {
            @Override
            public void onResponse(Call<List<Carrito>> call, Response<List<Carrito>> response) {
                List<Carrito> oCar = response.body();
                if (response.isSuccessful()) {
                    vmCarritoIdUsuario(oCar.get(0).getUsuarioId());
                }

            }
            @Override
            public void onFailure(Call<List<Carrito>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}