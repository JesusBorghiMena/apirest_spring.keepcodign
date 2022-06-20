package com.formacionspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.entity.Cliente;

//debo indicarle que vamos a hacer por cada clase a Spring.
@Repository

//para CRUD (Create, delateByid, reset, 
//nombre de la clase (T) y ID es el tipo del ID.
public interface ClienteDao extends CrudRepository<Cliente, Long > {

	
}
