package br.com.erudio.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;

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
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		Page<PersonVO> persons = personServices.findAll(pageable);
		persons.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find all people recorded")
	@GetMapping(value = {"/findPersonByName/{firstName}"} , produces = {"application/json", "application/xml","application/x-yaml"})
	public ResponseEntity<?> findPersonByName(
			@PathVariable("firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		Page<PersonVO> persons = personServices.findPersonByName(firstName, pageable);
		persons.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
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
	
	@ApiOperation(value = "Disable enabled people")
	@PatchMapping(value = "/{id}", produces = {"application/json", "application/xml","application/x-yaml"})
	public PersonVO disablePerson(@PathVariable("id") Long id) {
		PersonVO personVo = personServices.disabledPerson(id);
		personVo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVo;
	}
	

}
