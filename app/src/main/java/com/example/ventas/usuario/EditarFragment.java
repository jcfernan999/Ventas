package com.example.ventas.usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.ventas.R;
import com.example.ventas.model.Usuario;

public class EditarFragment extends Fragment {
    private EditText etNombre,etApellido,etDni,etTelefono,etEmail,etDireccion,etAvatar;
    private ImageView ivFoto;
    private Button btnEditar;

    private UsuarioViewModel usuarioViewModel;
    private Usuario oUsuarioCopia;

    public static EditarFragment newInstance() {
        return new EditarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivFoto = view.findViewById(R.id.ivEditarImg);
        etNombre = view.findViewById(R.id.etEditarNombre);
        etApellido = view.findViewById(R.id.etEditarApellido);
        etDni = view.findViewById(R.id.etEditarDni);
        etTelefono = view.findViewById(R.id.etEditarTelefono);
        etEmail = view.findViewById(R.id.etEditarEmail);
        etDireccion = view.findViewById(R.id.etEditarDirrecion);
        btnEditar = view.findViewById(R.id.btnEditarUsuario);
        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioMLD().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario oUsuario) {
                oUsuarioCopia = oUsuario;

                fijarDatos(oUsuario);

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Editar Usuario?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        oUsuarioCopia.setNombre(etNombre.getText()+"");
                        oUsuarioCopia.setApellido(etApellido.getText()+"");
                        oUsuarioCopia.setDni(etDni.getText()+"");
                        oUsuarioCopia.setTelefono(etTelefono.getText()+"");
                        oUsuarioCopia.setEmail(etEmail.getText()+"");
                        oUsuarioCopia.setDireccion(etDireccion.getText()+"");

                        usuarioViewModel.vmEditarUsuario(oUsuarioCopia);
                        view.refreshDrawableState();

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).show();
            }
        });
        usuarioViewModel.leer();

    }
    //cargamos los datos ala vista
    public void fijarDatos(Usuario oUsuarioConectado){

        Glide.with(this).load(oUsuarioConectado.getAvatar()).into(ivFoto);
        etNombre.setText(oUsuarioConectado.getNombre());
        etApellido.setText(oUsuarioConectado.getApellido());
        etDni.setText(oUsuarioConectado.getDni());
        etTelefono.setText(oUsuarioConectado.getTelefono());
        etEmail.setText(oUsuarioConectado.getEmail());
        etDireccion.setText(oUsuarioConectado.getDireccion());

    }
}