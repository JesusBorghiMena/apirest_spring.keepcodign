package com.formacionspring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	@GetMapping ("/clientes")
	public List<Cliente> index(){
		//llamar a la variable que retorna el servico
		return servicio.mostrarTodos();
		}
	//() se hacer referencia desde pathVariable Long id para que la url sepa que
	//el id lo voy a cargar yo 
	@GetMapping("/clientes/{id}")
	public Cliente show (@PathVariable Long id) {
		return servicio.mostrarPorId(id);
		}
	//se envian se forma mas segura al hacerlo de esta manera.
	//se puede hacer por get pero es un error de seguridad ya que cualquiera puede tomar esos datos.
	//metodo para crear un nuevo cliente
	//capturamos al cliente con Requestbody
	//@PostMapping("/cliente")
	//public Cliente create(@RequestBody Cliente cliente) {
		//return servicio.guardar(cliente);
		//}
		
		
	@PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        Cliente clienteNew = null;
        Map<String,Object>  response = new HashMap<>();

        try {

            clienteNew =  servicio.guardar(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido creado con éxito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);


    }
	
	//hay que pasar un id que quiero modificar y el registro modificado.
	
	/*@PutMapping("/cliente/{id}")
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
	}*/
	@PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente
            ,@PathVariable Long id) {

        Cliente clienteUpdate =  servicio.mostrarPorId(id);
        Map<String,Object>  response = new HashMap<>();


        if(clienteUpdate == null) {
            response.put("mensaje","No existe el registro con id:"+id);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {


            clienteUpdate.setNombre(cliente.getNombre());
            clienteUpdate.setApellido(cliente.getApellido());
            clienteUpdate.setEmail(cliente.getEmail());
            clienteUpdate.setTelefono(cliente.getTelefono());
            clienteUpdate.setCreateAt(cliente.getCreateAt());

            //guardo y retorno los datos actualizados
            servicio.guardar(clienteUpdate);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido actualizado con éxito");
        response.put("cliente", clienteUpdate);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    }
	       
	//@DeleteMapping("/cliente/{id}")
	//public void delete(@PathVariable Long id) {
		//servicio.borrar(id);

	/*@DeleteMapping("/clientes/{id}")
	public Cliente delete (@PathVariable Long id) {
		Cliente clienteBorrado= servicio.mostrarPorId(id);
		servicio.borrar(id);
		return clienteBorrado;
		
	}*/
	
	@DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Cliente clienteBorrado = servicio.mostrarPorId(id);
        Map<String,Object>  response = new HashMap<>();

        if(clienteBorrado == null) {
            response.put("mensaje","No existe el registro con id:"+id);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {

            servicio.borrar(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar en base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido eliminado con éxito");
        response.put("cliente", clienteBorrado);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
		@PostMapping("/clientes/uploads")
		    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,
		            @RequestParam("id") Long id){

		        Map<String,Object>  response = new HashMap<>();
		        //buscar el cliente por el id recibido
		        Cliente cliente = servicio.mostrarPorId(id);

		        //preguntamos si el archivo es distinto de vacio
		        if( !archivo.isEmpty() ) {
		            //guardamos el nombre del archivo en esta variable y que se pueda guardar
		        	//la misma imagen en varias personas (id)
		        	String nombreArchivo =  UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "");
		           
		        	//guardamos la ruta completa uploads/nombredelaimagen lo guardamos en
		            //una variable de tipo path que es de java.io

		            Path rutaArchivo = Paths.get("upload").resolve(nombreArchivo).toAbsolutePath();

		            try {
		                //copiamos el archivo fisico a la ruta que definimos en Path
		                Files.copy(archivo.getInputStream(), rutaArchivo );
		            } catch (IOException e) {
		                response.put("mensaje", "Error al subir la imagen del cliente");
		                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
		                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		            }

		            //guardamos el nombre de la imagen
		            cliente.setImagen(nombreArchivo);
		            //registramos en base de datos
		            servicio.guardar(cliente);

		            response.put("cliente", cliente);
		            response.put("mensaje","Imagen subida correctamente :"+nombreArchivo);

		        }


		        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		    }	
	}
	
	
