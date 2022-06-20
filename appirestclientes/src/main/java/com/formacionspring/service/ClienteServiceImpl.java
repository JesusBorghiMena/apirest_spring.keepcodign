package com.formacionspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionspring.entity.Cliente;
import com.formacionspring.repository.ClienteDao;

//hay que decirle a Spring que es esto
@Service

//marca en rojo ClienteServiceImpl para agregar un metodo si o si.
public class ClienteServiceImpl implements ClienteService {
	
	
	//para implemntar la variable
	@Autowired
	//hay que inyectar clienteDao

	private ClienteDao clienteDao;
	//mejor rendimiento a la hora de consulta

	@Override
	@Transactional(readOnly= true)
	public List<Cliente> mostrarTodos() {
		//hay que instar mis clientes de mi Dao
		//casquear (reafirmar) que va a devolver un estado de tipo cliente
		//llamar a la funcion findAll
		return (List<Cliente>) clienteDao.findAll();

	}

	@Override
	@Transactional
	public Cliente mostrarPorId(Long Id) {
		return clienteDao.findById(Id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente guardar(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void borrar(Long Id) {
		clienteDao.deleteById(Id);
	}
	
}
