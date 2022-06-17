/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.CategoriaDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class CategoriaDAO {
    private static final String SQL_INSERT="{call spInsertar( ?, ?)}";
    private static final String SQL_UPDATE="{call spActualizar (?, ? ,?)}";
    private static final String SQL_DELETE="{call spEliminar(?)}";
    private static final String SQL_READ="{call spSeleccionarUno(?)}";
    private static final String SQL_READ_ALL="{call spSeleccionarTodo()}";
    
    private static final String SQL_INSERT_postgreSQL="call spInsertar( ?, ?)";
    private static final String SQL_UPDATE_postgreSQL="call spActualizar (?, ? ,?)";
    private static final String SQL_DELETE_postgreSQL="call spEliminar(?)";
    private static final String SQL_READ_postgreSQL="select * from Categoria where idCategoria = ?";
    private static final String SQL_READ_ALL_postgreSQL="select * from Categoria";
    
 
    private Connection conexion;
    
     public Connection obtenerConexion() {
        //obtener conexion con MySQL
        /*String usuario = "root";
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

    
   /*  
     String usuario = "postgres";
     String clave = "root777";
     String db = "ProyectoBase4";
     String ip = "localhost";
     String puerto = "5432";
     
     String url  = "org.postgresql.Driver://"+ip+":"+puerto+"/"+db;
     */
     
 
 
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

   public void create(CategoriaDTO dto) throws SQLException{
       obtenerConexion();
       CallableStatement cs = null;
       try{
           cs = conexion.prepareCall(SQL_INSERT_postgreSQL);
           cs.setString(1, dto.getEntidad().getNombreCategoria());
           cs.setString(2, dto.getEntidad().getDescripcionCategoria());
           cs.executeUpdate();
       }finally{
           if (cs != null) cs.close();
           if (conexion != null) conexion.close();
       }
   }
   public void update(CategoriaDTO dto) throws SQLException{
       obtenerConexion();
       CallableStatement cs = null;
       try{
           cs = conexion.prepareCall(SQL_UPDATE_postgreSQL);
           /*El orden de los argumentos de el stored procedure 
           de MySQL y el de PostgreSQL son distintos, si se va a cambiar
           de base de datos tomar eso en cuenta*/
           cs.setString(1, dto.getEntidad().getNombreCategoria());
           cs.setString(2, dto.getEntidad().getDescripcionCategoria());
           cs.setInt(3, dto.getEntidad().getIdCategoria());
           cs.executeUpdate();
       }finally{
           if (cs != null) cs.close();
           if (conexion != null) conexion.close();
       }
   }
   public void delete(CategoriaDTO dto) throws SQLException{
       obtenerConexion();
       CallableStatement cs = null;
       try{
           cs = conexion.prepareCall(SQL_DELETE_postgreSQL);
           cs.setInt(1, dto.getEntidad().getIdCategoria());
           cs.executeUpdate();
       }finally{
           if (cs != null) cs.close();
           if (conexion != null) conexion.close();
       }
   }
   public CategoriaDTO read(CategoriaDTO dto) throws SQLException{
       obtenerConexion();
       CallableStatement cs = null;
       ResultSet rs = null;
       try{
           cs = conexion.prepareCall(SQL_READ_postgreSQL);
           cs.setInt(1, dto.getEntidad().getIdCategoria());
           rs = cs.executeQuery();
           List resultados = obtenerResultados(rs);
           if (resultados.size() > 0){
               return (CategoriaDTO) resultados.get(0);
           }else{
               return null;
           }
       }finally{
           if (cs != null) cs.close();
           if (conexion != null) conexion.close();
       }
   }
   public List readAll() throws SQLException{
       obtenerConexion();
       CallableStatement cs = null;
       ResultSet rs = null;
       try{
           cs = conexion.prepareCall(SQL_READ_ALL_postgreSQL);
           rs = cs.executeQuery();
           List resultados = obtenerResultados(rs);
           if (resultados.size() > 0){
               return  resultados;
           }else{
               return null;
           }
       }finally{
           if (cs != null) cs.close();
           if (conexion != null) conexion.close();
       }
   }

    private List obtenerResultados(ResultSet rs) throws SQLException{
        List resultados = new ArrayList();
        while (rs.next()) {     
            CategoriaDTO dto = new CategoriaDTO();
            dto.getEntidad().setIdCategoria(rs.getInt("idCategoria"));
            dto.getEntidad().setNombreCategoria(rs.getString("nombreCategoria"));
            dto.getEntidad().setDescripcionCategoria(rs.getString("descripcionCategoria"));
            resultados.add(dto);
        }
        return resultados;
    } 
    
    
    public static void main(String[] args) {
   /*     CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        //dto.getEntidad().setIdCategoria(8);
        //dto.getEntidad().setNombreCategoria("JAJA");
        //dto.getEntidad().setDescripcionCategoria("JAAAAAAAAAAAA");

        try {
            //dao.create(dto);
            //dao.delete(dto);
            //System.out.println(dao.readAll());
            //System.out.println(dao.read(dto));
            //dao.delete(dto);
            //System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
