package com.training.tutorials.controller.api;


import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExampleRestController {

    @GetMapping("/count")
    public ResponseEntity<Long> countWordLength(@RequestParam String word) {
        Long length = (long) word.length();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(length);
    }


}
