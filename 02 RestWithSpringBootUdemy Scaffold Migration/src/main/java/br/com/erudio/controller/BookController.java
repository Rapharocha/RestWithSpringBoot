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

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Description for Books", tags = "BookEndPoint")
@RestController
@RequestMapping("/api/books/v1")
public class BookController {

	@Autowired
	private BookService service;
	
	@ApiOperation(value = "FindById books recorded")
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml","application/x-yaml"})
	public BookVO findById(@PathVariable("id") Long id) {
		BookVO bookVO = service.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}
	
	@ApiOperation(value = "Delete books recorded")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Find all books recorded")
	@GetMapping(produces = {"application/json", "application/xml","application/x-yaml"})
	public List<BookVO> findAll(String id) {
		List<BookVO> books = service.findAll();
		books.stream()
			.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		return books;
	}
	
	@ApiOperation(value = "Create books recorded")
	@PostMapping(produces = {"application/json", "application/xml","application/x-yaml"}, 
			consumes = {"application/json", "application/xml","application/x-yaml"})
	public BookVO create(@RequestBody BookVO person) {
		BookVO bookVO =  service.create(person);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}
	
	@ApiOperation(value = "Update books recorded")
	@PutMapping(produces = {"application/json", "application/xml","application/x-yaml"}, 
			consumes = {"application/json", "application/xml","application/x-yaml"})
	public BookVO update(@RequestBody BookVO book) {
		BookVO bookVO =  service.update(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}
}
