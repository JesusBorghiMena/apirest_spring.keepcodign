package com.formacionspring.service;

import java.util.List;

import com.formacionspring.entity.Cliente;

public interface ClienteService {

	//son funciones abstractas. 
	public List<Cliente> mostrarTodos();
		
	//metodo para mostar un cliente por Id
	public Cliente mostrarPorId(Long Id);
	
	//Metodo para guardar un cliente
	public Cliente guardar (Cliente cliente);
	
	//metodo para borrar un cliente por Id
	public void borrar(Long Id);

	
	
	
	 
}
