package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MainController {

    @GetMapping("/multiplication")
    @ResponseBody
    ResponseEntity<String> multiply (@RequestParam Double factor1, @RequestParam Double factor2) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(String.valueOf(factor1 * factor2));
    }


    @GetMapping("/adding")
    @ResponseBody
    ResponseEntity<String> adding (@RequestParam Double factor1, @RequestParam Double factor2) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "+");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(String.valueOf(factor1 + factor2));
    }
}