package com.example.ventas.cliente;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ventas.R;
import com.example.ventas.databinding.ActivityClienteBinding;
import com.example.ventas.model.Usuario;
import com.example.ventas.usuario.UsuarioViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class ClienteActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityClienteBinding binding;
    private UsuarioViewModel usuarioViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarCliente.toolbar);
        binding.appBarCliente.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_producto_lista)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_cliente);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cliente, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_cliente);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarVista();
    }
    public void configurarVista(){

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarioMLD().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario oUsuario) {

                final View vistaHeader = binding.navView.getHeaderView(0);

                final TextView menu_Usuario = vistaHeader.findViewById(R.id.tv_menu_nom_ape);
                final TextView menu_Email = vistaHeader.findViewById(R.id.tv_menu_email);
                final ImageView menu_Img = vistaHeader.findViewById(R.id.iv_menu_img);

                menu_Usuario.setText(oUsuario.getApellido()+" "+oUsuario.getNombre());
                menu_Email.setText(oUsuario.getEmail());
                Glide.with(vistaHeader).load(oUsuario.getAvatar()).into(menu_Img);
            }
        });
        usuarioViewModel.leer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_carrito:
                Navigation.findNavController(this, R.id.nav_host_fragment_content_cliente).navigate(R.id.nav_carrito);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}