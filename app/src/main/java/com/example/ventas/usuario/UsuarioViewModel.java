package com.example.ventas.usuario;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ventas.model.Usuario;
import com.example.ventas.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> usuarioMLD;

    public UsuarioViewModel(@NonNull Application application){
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Usuario> getUsuarioMLD(){
        if(usuarioMLD == null){
            usuarioMLD = new MutableLiveData<>();
        }
        return usuarioMLD;
    }
    public void leer() {
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");
        Call<Usuario> usuarioCall = ApiClient.getMyApiClient().leer(accessToken);
        usuarioCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario oUsuario = response.body();
                    usuarioMLD.postValue(oUsuario);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void vmAltaUsuario(Usuario oUsuario){

        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");
        Call<Usuario> callCrearUsuario = ApiClient.getMyApiClient().getApiAltaUsuario(accessToken, oUsuario);
        callCrearUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Usuario agregado correctamente!", Toast.LENGTH_LONG).show();
                }
                else{Toast.makeText(context, "Usuario no agregado!", Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void vmEditarUsuario(Usuario oUsuario){
        SharedPreferences sp = context.getSharedPreferences("token", 0);
        String accessToken = sp.getString("token", "");

        Call<Usuario> callStockCarritoItem = ApiClient.getMyApiClient().getApiEditarUsuario(accessToken, oUsuario.getId(), oUsuario);
        callStockCarritoItem.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    leer();
                    Toast.makeText(context, "Usuario editado correctamente!", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(context, "Usuario no editado!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}