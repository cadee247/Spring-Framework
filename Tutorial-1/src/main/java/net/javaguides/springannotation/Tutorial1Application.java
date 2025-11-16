package net.javaguides.springannotation;

import net.javaguides.springannotation.controller.PizzaController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Tutorial1Application {

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(Tutorial1Application.class, args);
//        PizzaController pizzaController = context.getBean(PizzaController.class);
//        System.out.println(pizzaController.getPizza());
//    }

        ConfigurableApplicationContext context = SpringApplication.run(Tutorial1Application.class, args);
        PizzaController pizzaController = context.getBean(PizzaController.class);
        System.out.println(pizzaController.getPizza());


}}