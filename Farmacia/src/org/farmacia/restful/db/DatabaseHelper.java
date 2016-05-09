package org.farmacia.restful.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.farmacia.restful.modelo.Departamento;
import org.farmacia.restful.modelo.Direccion;
import org.farmacia.restful.modelo.FORMA_PAGO;
import org.farmacia.restful.modelo.Farmacia;
import org.farmacia.restful.modelo.Producto;
import org.farmacia.restful.modelo.Usuario;
import org.farmacia.restful.modelo.Pedido;

public class DatabaseHelper{
	private final static DatabaseHelper db = new DatabaseHelper();
	static Connection con;
	static Statement stat;
	static ResultSet res;
	
	private List<Producto> productos = new ArrayList<Producto>();
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private List<Farmacia> farmacias = new ArrayList<Farmacia>();
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	private DatabaseHelper(){
		
		// PRODUCTOS
		Usuario us1 = new Usuario("jcgallardomorales@gmail.com","mipass","Juan Carlos Gallardo",
				new Direccion("calle",1,'a',"GRANADA",10000,"GRANADA","ESPAÑA"),
				"22222222A",FORMA_PAGO.CONTRARREMBOLSO);
		
		Usuario us2 = new Usuario("javierizquierdovera@gmail.com","mipass","Javier Izquierdo Vera",
				new Direccion("calle",2,'d',"GRANADA",10000,"GRANADA","ESPAÑA"),
				"55555555H",FORMA_PAGO.CONTRARREMBOLSO);
		
		usuarios.add(us1);
		usuarios.add(us2);
		
		Producto p1 = new Producto(1,"Producto 1", "Descripcion p 1",1.0f, new GregorianCalendar(), new GregorianCalendar(),Departamento.HOMEOPATIA, 1.21f);
		Producto p2 = new Producto(2,"Producto 2", "Descripcion p 2",2.0f, new GregorianCalendar(), new GregorianCalendar(),Departamento.MEDICAMENTOS, 1.16f);
		Producto p3 = new Producto(3,"Producto 3", "Descripcion p 3",3.0f, new GregorianCalendar(), new GregorianCalendar(),Departamento.MEDICAMENTOS, 1.21f);
		Producto p4 = new Producto(4,"Producto 4", "Descripcion p 4",4.0f, new GregorianCalendar(), new GregorianCalendar(),Departamento.PERFUMERIA, 1.21f);
		
		productos.add(p1);
		productos.add(p2);
		productos.add(p3);
		productos.add(p4);
		
		
		// Stocks de farmacias
		
		Farmacia f1 = new Farmacia("1111111","Farmacia1", null, null, null, -4.12f, 37.26f);
		Farmacia f2 = new Farmacia("2222222", "Farmacia2", null, null, null, -5.70f, 38.35f);
		f1.addStockProducto(p1.getId(), 20);
		f1.addStockProducto(p2.getId(), 100);
		f1.addStockProducto(p3.getId(), 200);
		f1.addStockProducto(p4.getId(), 50);
		
		f2.addStockProducto(p1.getId(), 10);
		f2.addStockProducto(p2.getId(), 30);
		f2.addStockProducto(p3.getId(), 15);
		f2.addStockProducto(p4.getId(), 40);

		farmacias.add(f1);
		farmacias.add(f2);
		
		farmacias.add(f1);
		farmacias.add(f2);
		
		// PEDIDOS
		Pedido pedido1 = new Pedido(f1);
		pedido1.createLineaPedido(p1, 3);
		pedido1.createLineaPedido(p4, 2);
				
		Pedido pedido2 = new Pedido(f1);
		pedido2.createLineaPedido(p1, 10);
		pedido2.createLineaPedido(p2, 5);
				
		Pedido pedido3 = new Pedido(f2);
		pedido3.createLineaPedido(p4, 3);
				
		pedidos.add(us1.addPedido(pedido1));
		pedidos.add(us2.addPedido(pedido2));
		pedidos.add(us1.addPedido(pedido3));
		
	}
	
	public static DatabaseHelper getInstance(){
		return db;
	}
	
	public List<Producto> getProductos(){
		return productos;
	}
	
	public List<Pedido> getPedidos(){
		return pedidos;
	}
	
	public List<Farmacia> getFarmacias(){
		getFarmaciasDB();
		return farmacias;
	}
	
	public List<Usuario> getUsuarios(){
		return usuarios;
	}
	
	private void getFarmaciasDB(){
		try {
			Conexion conexion = Conexion.getConexion();
		    ResultSet res = conexion.query("select * from farmacia");
		      
		    while(res.next()){
		    	System.out.println(res.getString(1));
		    }
		    
		 }catch ( Exception e ) {
		      System.err.println("**************************" + e.getClass().getName() + ": " + e.getMessage() + e.getLocalizedMessage() );
		      //System.exit(0);
		 }
		 
	}
}
