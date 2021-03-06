package com.readlist.service.web.controllers;

import com.readlist.service.core.model.Article;
import com.readlist.service.core.interfaces.IArticleService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("articles")
class ArticlesController {
    @Autowired
    private IArticleService articleService;

    private final Integer mockUserId = 0;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Flux<Article> getAll() {
        return articleService.getAllArticles(mockUserId);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Article>> getById(@PathVariable("id") Integer id) {
        return  articleService.findById(id)
                .map(article -> ResponseEntity.ok(article))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"title\":\"Test article\", \"url\":\"http://localhost/test\"}")
    }))
    Mono<ResponseEntity<URI>> create(@RequestBody Article article) {
        return articleService.createArticle(article, mockUserId)
                .map(created -> ResponseEntity.created(URI.create(("/" + created.getId()))).build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Article>> updateById(@PathVariable Integer id, @RequestBody Article article) {
        return articleService.updateArticle(id, article)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
        return articleService.deleteArticle(id)
                .map(deleted -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}