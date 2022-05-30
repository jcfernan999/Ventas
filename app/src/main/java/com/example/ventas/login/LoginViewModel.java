package com.example.ventas.login;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ventas.model.Usuario;
import com.example.ventas.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> token;
    private MutableLiveData<Usuario> usuarioMLD;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getToken() {
        if (token == null) {
            token = new MutableLiveData<>();
        }
        return token;
    }
    public MutableLiveData<Usuario> getUsuarioMLD(){
        if(usuarioMLD == null){
            usuarioMLD = new MutableLiveData<>();
        }
        return usuarioMLD;
    }
    public void vmLogin(final String email, final String clave) {
        Call<String> dates = ApiClient.getMyApiClient().login(email, clave);
        dates.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String res = response.body();
                    token.postValue(res);
                    SharedPreferences sp = context.getSharedPreferences("token", 0);
                    SharedPreferences.Editor editor = sp.edit();
                    String t = "Bearer " + res;
                    editor.putString("token", t);
                    editor.apply();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                token.postValue("error");
            }
        });
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
}