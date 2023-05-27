package com.freshtyre

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FreshtyreAPIApplication

fun main(args: Array<String>) {
    SpringApplication.run(FreshtyreAPIApplication::class.java, *args)
}