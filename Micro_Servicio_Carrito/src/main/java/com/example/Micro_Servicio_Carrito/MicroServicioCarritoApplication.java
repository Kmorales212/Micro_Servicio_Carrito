package com.example.Micro_Servicio_Carrito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.carrito")
@EnableJpaRepositories(basePackages = "com.example.carrito.repository")
public class MicroServicioCarritoApplication { 

    public static void main(String[] args) {
        SpringApplication.run(MicroServicioCarritoApplication.class, args);
    }
}