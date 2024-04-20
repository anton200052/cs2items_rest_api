package me.vasylkov.cs2itemsrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Cs2itemsrestapiApplication
{
    public static void main(String[] args)    // arr[индекс] = значение  let a = arr[индекс]
    {
        SpringApplication.run(Cs2itemsrestapiApplication.class, args);
    }
}
