package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.service.MathService;

@RestController
public class MathController {

	@Autowired
	private MathService mathService;

	@RequestMapping("/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo ) throws Exception {
		
		return mathService.somar(numberOne, numberTwo);
	}
	
	@RequestMapping("/sub/{numberOne}/{numberTwo}")
	public Double sub(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo ) throws Exception {
	
		return mathService.subtrair(numberOne, numberTwo);
	}
	
	@RequestMapping("/multi/{numberOne}/{numberTwo}")
	public Double multiplicacao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo ) throws Exception {
		
		return mathService.multiplicar(numberOne, numberTwo);
	}
	
	@RequestMapping("/div/{numberOne}/{numberTwo}")
	public Double divisao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo ) throws Exception {
		
		return mathService.dividir(numberOne, numberTwo);
	}
	
	@RequestMapping("/media/{numberOne}/{numberTwo}")
	public Double media(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo ) throws Exception {
		
		return mathService.media(numberOne, numberTwo);
	}
	
	@RequestMapping("/raiz/{number}")
	public Double raiz(@PathVariable("number") String number) throws Exception {
		
		return mathService.raizQuadrada(number);
	}

}
