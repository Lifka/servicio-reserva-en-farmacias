package com.red.lifka.sisfarmaapp.DB;


import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.red.lifka.sisfarmaapp.Cliente.Departamentos;
import com.red.lifka.sisfarmaapp.Cliente.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JSONParser {

    private Activity activity;
    private DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    private DBQuerys db_querys;
    JSONManager json_manager = new JSONManager();
    public static final String URL_PRODUCTOS = "http://10.0.2.2:8080/Farmacia/api/productos";

    public JSONParser(Activity a){
        activity = a;
        db_querys = new DBQuerys(a);
    }

    public void readAndParseJSONProductos() throws JSONException {

                try{
                    String json_productos = json_manager.getJSON(URL_PRODUCTOS);
                    /***/Toast.makeText(activity, "JSON --> " + json_productos.toString(), Toast.LENGTH_LONG).show();

                    JSONArray json_array = null;
                    try {
                        json_array = new JSONArray(json_productos);
                    } catch (JSONException e) {
                        Log.e("JSONobject", "Malformed JSON " + e.getMessage());
                    }

                    if(json_array != null) {
                        parseJSONProductos(json_array);
                    } else {
                        Log.e("Failed to get productos","There aren't productos");;
                    }
                } catch(Exception e){
                    Log.e("Failed to get productos",e.getMessage());;
                }

    }


    private void parseJSONProductos(JSONArray productos_json) throws JSONException, ParseException {
        ArrayList<Producto> productos = new ArrayList();

        for(int i = 0; i < productos_json.length(); i++){


            int id = productos_json.getJSONObject(i).getInt("id");
            String nombre = productos_json.getJSONObject(i).getString("nombre");
            String descripcion = productos_json.getJSONObject(i).getString("descripcion");
            float precio = (float)productos_json.getJSONObject(i).getDouble("precio");
            String f_creacion = productos_json.getJSONObject(i).getString("f_creacion");
            String f_caducidad = productos_json.getJSONObject(i).getString("f_caducidad");
            Departamentos departamento = Departamentos.SIN_CLASIFICAR;//Departamentos.valueOf(productos_json.getJSONObject(i).getString("departamento"));
            float porcentaje_iva = 2.3f;//(float)productos_json.getJSONObject(i).getDouble("porcentaje_iva");

            Date f_creacion_date = new Date();
            Date f_caducidad_date = new Date();

            try {
                f_creacion_date = format.parse(f_creacion);
                f_caducidad_date = format.parse(f_caducidad);
            } catch (Exception e){
                /***/Log.d("Date error", e.getMessage());

            }


            /***/Log.d("Uno más --> ",Integer.toString(i) + " " + nombre);
            /***/Toast.makeText(activity, "Uno más --> " + Integer.toString(i) + " " + nombre, Toast.LENGTH_LONG).show();


            Producto producto = new Producto(id, nombre, descripcion, precio, f_creacion_date, f_caducidad_date,
                    departamento, porcentaje_iva);
            productos.add(producto);

        }
        /***/Toast.makeText(activity, "total leídos --> " + Integer.toString(productos.size()), Toast.LENGTH_LONG).show();
        /***/Log.d("total leídos --> ",Integer.toString(productos.size()));;

        db_querys.putProductos(productos);

    }


}