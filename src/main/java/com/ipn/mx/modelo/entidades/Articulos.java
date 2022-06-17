/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Articulos implements Serializable{
    private int idArticulo;
    private String nombreArticulo;
    private String descripcionArticulo;
    private int existencias;
    private int stockMinimo;
    private int stockMaximo;
    private double precio;
    
    private int idCategoria;
    //private Categoria idCategoria;
}
