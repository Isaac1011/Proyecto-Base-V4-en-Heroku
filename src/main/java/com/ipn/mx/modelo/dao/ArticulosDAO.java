/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ArticulosDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticulosDAO {
    
    //Para PostgreSQL
    private static final String SQL_INSERT_Articulo_PostgreSQL = "call spInsertarArticulo( ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_Articulo_PostgreSQL = "call spActualizarArticulo( ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_Articulo_PostgreSQL = "call spEliminarArticulo(?)";
    private static final String SQL_READ_Articulo_PostgreSQL="select * from Articulos where idArticulo = ?";
    private static final String SQL_READall_Articulo_PostgreSQL="select * from Articulos";
    
    //Para MySQL
    private static final String SQL_READ_Articulo = "{call spSeleccionarUno(?)}";
    private static final String SQL_READall_Articulo = "{call spSeleccionarTodo()}";
    
    private Connection conexion;
    
    public Connection obtenerConexion(){
        //obtener conexion con MySQL
        /*
        String usuario = "root";
        String clave = "12admi_777_True/";
        String url = "jdbc:mysql://localhost:3306/ProyectoBase4";
        //String url = "jdbc:mysql://localhost:3306/EscuelaWeb?
        //serverTimeZone=America/Mexico_City&allowPublicKeyRetrieval=true&
        //useSSL=false";

        String driverBD = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driverBD);
            conexion = DriverManager.getConnection(url, usuario, clave);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;*/

        
        //obtener conexion con PostgreSQL
        
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/ProyectoBase4";
        String user = "postgres";
        String password = "root777";
        try{

           Class.forName(driver);
           conexion = DriverManager.getConnection(url, user, password);
           //JOptionPane.showMessageDialog(null, "Se conectó correctamente a la DB");

       }
       catch(Exception e){
           System.out.println("Error al conectar a la base de datos: "+e.toString());
       }

       return conexion;
        
    }
    
    public void create(ArticulosDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_INSERT_Articulo_PostgreSQL);
            cs.setInt(1, dto.getEntidad().getIdCategoria());
            cs.setString(2, dto.getEntidad().getNombreArticulo());
            cs.setString(3, dto.getEntidad().getDescripcionArticulo());
            cs.setInt(4, dto.getEntidad().getExistencias());
            cs.setInt(5, dto.getEntidad().getStockMinimo());
            cs.setInt(6, dto.getEntidad().getStockMaximo());
            cs.setDouble(7, dto.getEntidad().getPrecio());
            cs.executeUpdate();
        } finally {
            if(cs != null){
                cs.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }  
    }
    
    public void update (ArticulosDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try {
            cs = conexion.prepareCall(SQL_UPDATE_Articulo_PostgreSQL);
            cs.setInt(1, dto.getEntidad().getIdArticulo());
            cs.setInt(2, dto.getEntidad().getIdCategoria());
            cs.setString(3, dto.getEntidad().getNombreArticulo());
            cs.setString(4, dto.getEntidad().getDescripcionArticulo());
            cs.setInt(5, dto.getEntidad().getExistencias());
            cs.setInt(6, dto.getEntidad().getStockMinimo());
            cs.setInt(7, dto.getEntidad().getStockMaximo());
            cs.setDouble(8, dto.getEntidad().getPrecio());
            cs.executeUpdate();
        } finally {
            if(cs != null){
                cs.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
        
    }
    
    
    public void delete (ArticulosDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = conexion.prepareCall(SQL_DELETE_Articulo_PostgreSQL);
            cs.setInt(1, dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
        }finally{
            if(cs != null){
                cs.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }
    
    
    public ArticulosDTO read(ArticulosDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try{
            cs = conexion.prepareCall(SQL_READ_Articulo_PostgreSQL);
            cs.setInt(1, dto.getEntidad().getIdArticulo());
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if(resultados.size() > 0){
                return (ArticulosDTO) resultados.get(0);
            }
            else{
                return null;
            }
        }finally{
            if(cs != null){
                cs.close();
            }
            if( conexion != null){
                conexion.close();
            }
            
        }
    }
    
    
    public List readAll() throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try{
            cs = conexion.prepareCall(SQL_READall_Articulo_PostgreSQL);
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if(resultados.size() > 0){
                return resultados;
            }
            else{
                return null;
            }
        }finally{
            if(cs != null){
                cs.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException{
        List resultados = new ArrayList();
        while(rs.next()){
            ArticulosDTO dto = new ArticulosDTO();
            dto.getEntidad().setIdArticulo(rs.getInt("idArticulo"));
            dto.getEntidad().setNombreArticulo(rs.getString("nombreArticulo"));
            dto.getEntidad().setDescripcionArticulo(rs.getString("descripcionArticulo"));
            dto.getEntidad().setExistencias(rs.getInt("existencias"));
            dto.getEntidad().setStockMinimo(rs.getInt("stockMinimo"));
            dto.getEntidad().setStockMaximo(rs.getInt("stockMaximo"));
            dto.getEntidad().setPrecio(rs.getDouble("precio"));
            dto.getEntidad().setIdCategoria(rs.getInt("idCategoria"));
            resultados.add(dto);
        }
        return resultados;
    }
    
    public static void main(String[] args){
        ArticulosDTO dto = new ArticulosDTO();
        ArticulosDAO dao = new ArticulosDAO();
        dto.getEntidad().setIdArticulo(2);
        dto.getEntidad().setIdCategoria(5);
        dto.getEntidad().setNombreArticulo("Cuchara");
        dto.getEntidad().setDescripcionArticulo("Cuchara nueva");
        dto.getEntidad().setExistencias(6);
        dto.getEntidad().setStockMinimo(3);
        dto.getEntidad().setStockMaximo(9);
        dto.getEntidad().setPrecio(2.5);
        
        try{
        //dao.delete(dto);
            System.out.println(dao.read(dto));
            
        }catch(SQLException ex){
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        
                
        
    }
    
    
    
    
}
