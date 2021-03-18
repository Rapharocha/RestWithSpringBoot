package br.com.erudio.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.service.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// CORS habilitado no controller em especifico
// @CrossOrigin(origins = {"http://localhost:8080", "http://www.erudio.com.br"})
@Api(description = "Description for Person", tags = {"Person Endpoint"})
@RestController
@RequestMapping("api/person/v1")
public class PersonController {	

	@Autowired
	private PersonServices personServices;

	@ApiOperation(value = "FindById people recorded")
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml","application/x-yaml"})
	public PersonVO findById(@PathVariable("id") Long id) {
		PersonVO personVo = personServices.findById(id);
		personVo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVo;
	}
	
	@ApiOperation(value = "Delete people recorded")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		personServices.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Find all people recorded")
	@GetMapping(produces = {"application/json", "application/xml","application/x-yaml"})
	public List<PersonVO> findAll(String id) {
		List<PersonVO> persons = personServices.findAll();
		persons.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}
	
	@ApiOperation(value = "Create people recorded")
	@PostMapping(produces = {"application/json", "application/xml","application/x-yaml"}, 
			consumes = {"application/json", "application/xml","application/x-yaml"})
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVo =  personServices.create(person);
		personVo.add(linkTo(methodOn(PersonController.class).findById(personVo.getKey())).withSelfRel());
		return personVo;
	}
	
	@ApiOperation(value = "Update people recorded")
	@PutMapping(produces = {"application/json", "application/xml","application/x-yaml"}, 
			consumes = {"application/json", "application/xml","application/x-yaml"})
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVo =  personServices.update(person);
		personVo.add(linkTo(methodOn(PersonController.class).findById(personVo.getKey())).withSelfRel());
		return personVo;
	}
	

}
