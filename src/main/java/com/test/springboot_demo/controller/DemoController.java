package com.test.springboot_demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.springboot_demo.error.BookNotFoundException;
import com.test.springboot_demo.model.Book;
import com.test.springboot_demo.model.ErrorMessage;
import com.test.springboot_demo.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "DemoController", description = "Rest API related to Book Management")
public class DemoController {

	@Autowired
	BookService bookservice;

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> list = bookservice.getAllBooks();

		return new ResponseEntity<List<Book>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation(value = "Search a Book with an ID ", response = Book.class)
	@RequestMapping("/books/{bookid}")
	public ResponseEntity<Book> getbookid(@PathVariable("bookid") int bookid) throws BookNotFoundException {
		Book book = null;

		book = bookservice.getBookById(bookid);
		if (book.getBookid() == 0) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setErrorCode(404);
			errorMessage.setErrorMessage("No BookID record exist for given bookid");
			return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Book>(book, new HttpHeaders(), HttpStatus.OK);
		}

	}

	@ApiOperation(value = "Add book")
	@PostMapping("/books/{bookid}")
	public ResponseEntity<Book> createorUpdateBook(@RequestBody Book book) throws BookNotFoundException {
		Book updated = bookservice.createorUpdateBook(book);
		return new ResponseEntity<Book>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation(value = "delete a book")
	@DeleteMapping("/books/{bookid}")
	public HttpStatus deleteBookId(@PathVariable("bookid") int bookid) throws BookNotFoundException {
		bookservice.deleteBookId(bookid);
		return HttpStatus.FORBIDDEN;
	}

}
