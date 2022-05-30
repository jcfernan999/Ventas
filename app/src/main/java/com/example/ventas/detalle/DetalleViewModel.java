package com.example.ventas.detalle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ventas.model.Detalle;
import com.example.ventas.model.Usuario;
import com.example.ventas.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> usuarioMLD;

    public DetalleViewModel(@NonNull Application application){
        super(application);
        context = application.getApplicationContext();
    }




    public void vmAltaDetalle(Detalle oDetalle){

        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");
        Call<Detalle> callCrearDetalle = ApiClient.getMyApiClient().getApiAltaDetalle(accessToken, oDetalle);
        callCrearDetalle.enqueue(new Callback<Detalle>() {
            @Override
            public void onResponse(Call<Detalle> call, Response<Detalle> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Usuario agregado correctamente!", Toast.LENGTH_LONG).show();
                }
                else{Toast.makeText(context, "Usuario no agregado!", Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Detalle> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}