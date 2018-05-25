package com.xuxue.dapp.btc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Main {


    public static void main(String[] args){
        SpringApplication app = new SpringApplication(Main.class);
        app.run();

    }

}
