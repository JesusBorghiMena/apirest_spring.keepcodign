package com.formacionspring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionspring.entity.Cliente;
import com.formacionspring.service.ClienteService;

//primero decirle a Spring que va a hacer esto
//decirle al sistema que es una api(siempre hay que hacerlo)
//guardamos las url para hacer las accines necesarias de la pagina
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	//para usar el servicio
	@Autowired
	private ClienteService servicio;
	
	//paso la url especifica para mostar el servicio
	//se puede ver en varias url (("/cliente","/client","/"))
	//crear un metodo para conectarse a una ruta
	@GetMapping ("/cliente")
	public List<Cliente> index(){
		//llamar a la variable que retorna el servico
		return servicio.mostrarTodos();
		}
	//() se hacer referencia desde pathVariable Long id para que la url sepa que
	//el id lo voy a cargar yo 
	@GetMapping("/cliente/{id}")
	public Cliente show (@PathVariable Long id) {
		return servicio.mostrarPorId(id);
		}
	//se envian se forma mas segura al hacerlo de esta manera.
	//se puede hacer por get pero es un error de seguridad ya que cualquiera puede tomar esos datos.
	//metodo para crear un nuevo cliente
	//capturamos al cliente con Requestbody
	@PostMapping("/cliente")
	public Cliente create(@RequestBody Cliente cliente) {
		return servicio.guardar(cliente);
		}
	
	//hay que pasar un id que quiero modificar y el registro modificado.
	
	@PutMapping("/cliente/{id}")
	public Cliente update(@RequestBody Cliente cliente
			,@PathVariable Long id) {
		
		Cliente clienteUpdate = servicio.mostrarPorId(id);
		//reemplazo los datos de clienteUpdate por el modelo recibido en RequesBody
		clienteUpdate.setNombre(cliente.getNombre());
		clienteUpdate.setApellido(cliente.getApellido());
		clienteUpdate.setEmail(cliente.getEmail());
		clienteUpdate.setTelefono(cliente.getTelefono());
		clienteUpdate.setCreateAt(cliente.getCreateAt());
		
		//guardo y retorno los datos actualizados.
		return servicio.guardar(clienteUpdate);
	}
	/*@DeleteMapping("/cliente/{id}")
	public void delete(@PathVariable Long id) {
		servicio.borrar(id);
}*/
	@DeleteMapping("/cliente/{id}")
	public Cliente delete(@PathVariable Long id) {
		Cliente clienteBorrado= servicio.mostrarPorId(id);
		servicio.borrar(id);
		return clienteBorrado;
		
	}
	
	}
