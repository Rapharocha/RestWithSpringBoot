package br.com.erudio.service;

import org.springframework.stereotype.Service;

import br.com.erudio.exception.UnsupportedMathOperationException;

@Service
public class MathService {

	
	public Double somar(String numberOne, String numberTwo) {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Por favor digite um número!");
		}
		
		double sum = isConvertToDouble(numberOne) + isConvertToDouble(numberTwo);
		return sum;
	}
	
	public Double subtrair(String numberOne, String numberTwo) {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Por favor digite um número!");
		}
		
		double sub = isConvertToDouble(numberOne) - isConvertToDouble(numberTwo);
		
		return sub;
		
	}
	
	public Double multiplicar(String numberOne, String numberTwo) {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Por favor digite um número!");
		}
		
		double mult = isConvertToDouble(numberOne) * isConvertToDouble(numberTwo);
		
		return mult;
	}
	
	public Double dividir(String numberOne, String numberTwo) {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Por favor digite um número!");
		}
		double num1 = isConvertToDouble(numberOne);
		double num2 = isConvertToDouble(numberTwo);
		if(num1 == 0 || num2 == 0) {
			throw new UnsupportedMathOperationException("Número não pode ser 0.");
		}
		double div = num1 / num2;
		
		return div;
	}
	
	public Double media(String numberOne, String numberTwo) {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Por favor digite um número!");
		}
		
		double media = (isConvertToDouble(numberOne) + isConvertToDouble(numberTwo))/2;
		
		return media;
	}
	
	public Double raizQuadrada(String number) {
		if(!isNumeric(number)) {
			throw new UnsupportedMathOperationException("Por favor digite um número!");
		}
		
		double raiz = Math.sqrt(isConvertToDouble(number));
		
		return raiz;
	}
	
	
	private double isConvertToDouble(String strNumber) {
		if(strNumber == null) return 0D;
		if(isNumeric(strNumber)) return Double.parseDouble(strNumber);
		return 0D;
	}

	private boolean isNumeric(String strNumber) {
		if(strNumber == null) return false;
		return strNumber.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
