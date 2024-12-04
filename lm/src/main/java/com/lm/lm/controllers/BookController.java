package com.lm.lm.controllers;

import com.lm.lm.models.Book;
import com.lm.lm.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


@PostMapping
public ResponseEntity<Book> addBook(@RequestBody Book book) {
    if (book.getTitle() == null || book.getPrice() == null || book.getCategory() == null || book.getPublisher() == null) {
        return ResponseEntity.badRequest().build(); 
    }
    return ResponseEntity.ok(bookRepository.save(book));
}

@PutMapping("/{id}")
public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody Book book) {
    return bookRepository.findById(id)
        .map(existingBook -> {
            existingBook.setTitle(book.getTitle());
            existingBook.setDescription(book.getDescription());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setCategory(book.getCategory());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setPrice(book.getPrice());
            return ResponseEntity.ok(bookRepository.save(existingBook));
        })
        .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

