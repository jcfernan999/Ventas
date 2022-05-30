package com.example.ventas.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.ventas.R;
import com.example.ventas.model.Usuario;
import com.example.ventas.login.LoginActivity;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNombre,etApellido,etDni,etTelefono,etEmail,etContrasena,etReContrasena,etDireccion;
    private ImageView ivFoto;
    private Button btnCrear;
    private UsuarioViewModel usuarioViewModel;
    private AwesomeValidation mAwesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        configurarVista();
    }
    public void configurarVista() {
        btnCrear = findViewById(R.id.btnCrearUsuario);
        etNombre = findViewById(R.id.tvRegistroNombre);
        etApellido = findViewById(R.id.tvRegistroApellido);
        etDni = findViewById(R.id.tvRegistroDni);
        etTelefono = findViewById(R.id.tvRegistroTelefono);
        etEmail = findViewById(R.id.tvRegistroEmail);
        etDireccion = findViewById(R.id.tvRegistroDireccion);
        etContrasena = findViewById(R.id.tvRegistroContrasena);
        etReContrasena = findViewById(R.id.tvRegistroReContrasena);
        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroNombre,
                RegexTemplate.NOT_EMPTY, R.string.invalid_nombre);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroApellido,
                RegexTemplate.NOT_EMPTY, R.string.invalid_apellido);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroDni,
                "[1-9]{1}[0-9]{7}", R.string.invalid_dni);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroTelefono,
                RegexTemplate.NOT_EMPTY, R.string.invalid_telefono);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroEmail,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroDireccion,
                RegexTemplate.NOT_EMPTY, R.string.invalid_direccion);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroContrasena,
                ".{8,}", R.string.invalid_contraseña);

        mAwesomeValidation.addValidation(this, R.id.tvRegistroReContrasena,
                R.id.tvRegistroContrasena, R.string.invalid_re_contraseña);

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioMLD().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {

            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAwesomeValidation.validate()){
                    Usuario oUsuario = new Usuario();
                    oUsuario.setNombre(etNombre.getText()+"");
                    oUsuario.setApellido(etApellido.getText()+"");
                    oUsuario.setDni(etDni.getText()+"");
                    oUsuario.setTelefono(etTelefono.getText()+"");
                    oUsuario.setEmail(etEmail.getText()+"");
                    oUsuario.setDireccion(etDireccion.getText()+"");
                    oUsuario.setClave(etContrasena.getText()+"");
                    oUsuario.setRolId(11);
                    oUsuario.setAvatar("https://img2.freepng.es/20200413/cx/transparent-icon-design-5e9514e3e94e76.6287660515868285159556.jpg");
                    oUsuario.setEstaHabilitado((byte) 1);
                    oUsuario.setRol(null);
                    usuarioViewModel.vmAltaUsuario(oUsuario);
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }

            }
        });
    }

}