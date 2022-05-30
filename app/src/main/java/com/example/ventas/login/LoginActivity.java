package com.example.ventas.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ventas.R;
import com.example.ventas.cliente.ClienteActivity;
import com.example.ventas.model.Usuario;
import com.example.ventas.usuario.RegistroActivity;
import com.example.ventas.vendedor.VendedorActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    private EditText etUsuario, etClave;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configurarVista();

        //--------------Login google
        googleBtn = findViewById(R.id.google_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    public void configurarVista() {
        etUsuario = findViewById(R.id.etLoginUsuario);
        etClave = findViewById(R.id.etLoginContraseña);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != "error") {
                    loginViewModel.leer();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        });
        loginViewModel.getUsuarioMLD().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario oUsuario) {
                if (oUsuario.getRol().getNombre().equals("Cliente")) {
                    Intent i = new Intent(getApplicationContext(), ClienteActivity.class);
                    startActivity(i);
                }
                else if (oUsuario.getRol().getNombre().equals("Vendedor")){
                    Intent i = new Intent(getApplicationContext(), VendedorActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Nada", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickIniciarSesion(View view) {
        String email = etUsuario.getText().toString();
        String clave = etClave.getText().toString();

        if (email.equals("") || clave.equals("")) {

            Toast.makeText(getApplicationContext(), "Completar", Toast.LENGTH_LONG).show();
        } else {
            loginViewModel.vmLogin(email, clave);
        }
    }

    public void onClickCrearUsuario(View view){


    }
    //----------------------metodos login google
    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }
    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(LoginActivity.this,ClienteActivity.class);
        startActivity(intent);
    }
    //----------------------Fin metodos login google
}