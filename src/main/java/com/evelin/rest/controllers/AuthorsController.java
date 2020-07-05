package com.evelin.rest.controllers;

import com.evelin.rest.model.Author;
import com.evelin.rest.repository.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorsController implements AuthorsNamespace{

    private final AuthorRepository authorRepository;

    public AuthorsController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long authorId){
       Optional<Author> author =  authorRepository.findById(authorId);
       return  author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Author> create(
            UriComponentsBuilder uriComponentsBuilder,
            @RequestBody Author author
    ){
        Author author1 = authorRepository.save(author);
        return  ResponseEntity.
                created(uriComponentsBuilder.path("/authors/{authorId}").buildAndExpand(author1.getId()).toUri()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete (@PathVariable Long id){
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
