package com.request.api.exception;

import java.util.ArrayList;
import java.util.List;

import com.request.api.dto.error.ErrorValidationDTO;
import com.request.api.dto.error.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorValidationHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorValidationDTO> handle(MethodArgumentNotValidException exception) {
		List<ErrorValidationDTO> errors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorValidationDTO error = new ErrorValidationDTO(e.getField(), message);
			errors.add(error);
		});
		
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminExistsException.class)
	public ExceptionResponse handle(AdminExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProductExistsException.class)
	public ExceptionResponse handle(ProductExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProductNotExistsException.class)
	public ExceptionResponse handle(ProductNotExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PurchaseNotExistsException.class)
	public ExceptionResponse handle(PurchaseNotExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(GenericException.class)
	public ExceptionResponse handle(GenericException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
}
