package com.formacionspring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//para crear la carpeta en la tabla
@Entity
//para cambiar el nombre de la tabla
@Table(name="clientes")

public class Cliente implements Serializable{

	
	//ID para marcar la columna principal
	@Id
	//para marcar la auto incrementacion
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//declarar los nombres de las columnas.
	private long id;
	private String nombre;
	private String apellido;
	private String email;
	private int telefono;
	//para cambiar nombre de las columnas
	@Column(name="create_at")
	private Date createAt;
	
	//para poder guardar las imagenes
	private String imagen;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
