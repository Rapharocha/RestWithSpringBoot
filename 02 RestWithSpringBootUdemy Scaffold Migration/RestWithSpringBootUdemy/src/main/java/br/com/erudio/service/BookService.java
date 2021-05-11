package br.com.erudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Book;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;
	
	public BookVO findById(Long id) {
		
		Book book = repository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return DozerConverter.parseObject(book, BookVO.class) ;
	}
	
	public Page<BookVO> findAll(Pageable pageable){
		
		var page =  repository.findAll(pageable);
		return page.map(this::convertToBookVO);
	}
	
	public BookVO convertToBookVO(Book book) {
		return DozerConverter.parseObject(book, BookVO.class);
	}
	
	public BookVO create(BookVO book) {
		Book entity = DozerConverter.parseObject(book, Book.class);
		BookVO vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	
	public BookVO update(BookVO b) {
		Book entity = repository.findById(b.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setAuthor(b.getAuthor());
		entity.setLaunchDate(b.getLaunchDate());
		entity.setPrice(b.getPrice());
		entity.setTitle(b.getTitle());
		
		BookVO vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
}
