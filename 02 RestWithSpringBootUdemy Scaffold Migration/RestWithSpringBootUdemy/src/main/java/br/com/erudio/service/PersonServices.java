package br.com.erudio.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {
	
	@Autowired	
	private PersonRepository repository;

	
	public PersonVO findById(Long id) {
		
		Person person = repository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return DozerConverter.parseObject(person, PersonVO.class) ;
	}
	
	public Page<PersonVO> findAll(Pageable pageable){
		
		var page =  repository.findAll(pageable);
		return page.map(this::convertToPersonVO);
	}

	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable){
		
		var page =  repository.findPersonByName(firstName, pageable);
		return page.map(this::convertToPersonVO);
	}
	
	public PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		Person entity = DozerConverter.parseObject(person, Person.class);
		PersonVO vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	
	public PersonVO update(PersonVO p) {
		Person entity = repository.findById(p.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(p.getFirstName());
		entity.setLastName(p.getLastName());
		entity.setAddress(p.getAddress());
		entity.setGender(p.getGender());
		
		PersonVO vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	@Transactional
	public PersonVO disabledPerson(Long id) {
		repository.disablePersons(id);
		Person person = repository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return DozerConverter.parseObject(person, PersonVO.class) ;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
	
}
