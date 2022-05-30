package com.example.ventas.request;

import com.example.ventas.model.Carrito;
import com.example.ventas.model.Detalle;
import com.example.ventas.model.Producto;
import com.example.ventas.model.Stock;
import com.example.ventas.model.Usuario;
import com.example.ventas.model.Venta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    private static final String PATH = "http://192.168.2.159:45455/Api/";

    private static MyApiInterface myApiInterface;

    public static MyApiInterface getMyApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInterface = retrofit.create(MyApiInterface.class);
        return myApiInterface;
    }

    public interface  MyApiInterface {
        //-----------------------------------Login--------------------------------------
        @POST("Usuarios/login")
        Call<String> login(@Query("Email") String email, @Query("Clave") String clave);

        //------------------------------------------------------------------------------

        //-----------------------------------Usuario--------------------------------------

        //Usuario Conectado
        @GET("Usuarios")
        Call<Usuario> leer(@Header("Authorization") String token);
        
        //Alta de usuario
        @POST("Usuarios")
        Call<Usuario> getApiAltaUsuario(@Header("Authorization") String token, @Body Usuario usuario);
        
        //Baja de usuario
        @DELETE("Usuarios/{id}")
        Call<Usuario> getApiBajaUsuario(@Header("Authorization") String token, @Path("id") int id);
        
        //Editar de usuario
        @PUT("Usuarios/{id}")
        Call<Usuario> getApiEditarUsuario(@Header("Authorization") String token, @Path("id") int id, @Body Usuario Usuario);
        //---------------------------------------------------------------------------------
        
        //-----------------------------------Producto--------------------------------------
        //Lista de productos
        @GET("Productos")
        Call<List<Producto>> getApiListarProductos(@Header("Authorization") String token);

        @GET("Productos/{id}")
        Call<Producto> getApiBuscarUnProducto(@Header("Authorization") String token, @Path("id") int Id);

        //-----------------------------------Stock--------------------------------------

        @GET("Stocks/{id}")
        Call<Stock> getApiBuscarStockDeUnProducto(@Header("Authorization") String token, @Path("id") int Id);



        @PUT("Stocks/{id}")
        Call<Stock> getApiEditarStock(@Header("Authorization") String token, @Path("id") int id, @Body Stock Stock);

        //-----------------------------------Carrito--------------------------------------

        @GET("Carritos/{id}")
        Call<List<Carrito>> getApiListarCarrito(@Header("Authorization") String token, @Path("id") int usuarioId);

        @POST("Carritos")
        Call<Carrito> getApiAltaCarrito(@Header("Authorization") String token, @Body Carrito carrito);

        @DELETE("Carritos/{id}")
        Call<Carrito> getApiBajaCarritoItem(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Carritos/{id}")
        Call<Carrito> getApiEditarCarrito(@Header("Authorization") String token, @Path("id") int id, @Body Carrito carrito);

        @DELETE("Carritos/DeleteAll/{id}")
        Call<List<Carrito>> getApiBajaCarritoAllItem(@Header("Authorization") String token, @Path("id") int usuarioId );

        //---------------------------------------------------------------------------------

        //-----------------------------------Detalle--------------------------------------

        @GET("Detalles/{id}")
        Call<List<Detalle>> getApiListarDetalle(@Header("Authorization") String token, @Path("id") int usuarioId);

        @POST("Detalles")
        Call<Detalle> getApiAltaDetalle(@Header("Authorization") String token, @Body Detalle detalle);
        //---------------------------------------------------------------------------------

        //-----------------------------------Venta--------------------------------------

        @GET("Ventas/{id}")
        Call<Venta> getApiBuscarVenta(@Header("Authorization") String token, @Path("id") int usuarioId);

        @POST("Ventas")
        Call<Venta> getApiAltaVenta(@Header("Authorization") String token, @Body Venta venta);

        //---------------------------------------------------------------------------------
    }

}
