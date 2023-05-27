package com.freshtyre.application.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestResource {

    @GetMapping
    fun test(): String {
        return "Hello, it's TEST"
    }

}