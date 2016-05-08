package com.red.lifka.sisfarmaapp.Cliente;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;


public class Farmacia {
    private String CIF;
    private String nombre;
    private Location localizacion;
    HashMap<Integer, ProductoFarmacia> productos_farmacia;

    public Farmacia(String CIF, String nombre, Location localizacion){
        this.CIF = CIF;
        this.nombre = nombre;
        this.localizacion = localizacion;
        productos_farmacia = new HashMap();
    }

    public String getNombre(){
        return nombre;
    }

    public String getCIF() {
        return CIF;
    }

    public Location getLocation(){
        return localizacion;
    }

    public void setProducto(Producto pro, int cantidad){
        productos_farmacia.put(pro.getId(), new ProductoFarmacia(pro.getId(),cantidad));
    }

    public void setProducto(int pro, int cantidad){
        productos_farmacia.put(pro, new ProductoFarmacia(pro,cantidad));
    }

    public void setProducto(ProductoFarmacia producto_farmacia){
        productos_farmacia.put(producto_farmacia.getProductID(), producto_farmacia);
    }

    public void setProducto(ArrayList<ProductoFarmacia> producto_farmacia_array){
        for(int i = 0; i < producto_farmacia_array.size(); i++)
            setProducto(producto_farmacia_array.get(i));
    }

    public int getStockOf(int id){
        return productos_farmacia.get(id).getStock();
    }

    public void buy(int id, int cantidad){
        productos_farmacia.get(id).subtract(cantidad);
    }
}
