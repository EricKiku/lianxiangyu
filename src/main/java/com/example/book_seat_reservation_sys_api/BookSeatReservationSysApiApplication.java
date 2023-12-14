package com.example.book_seat_reservation_sys_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.book_seat_reservation_sys_api.mapper")
public class BookSeatReservationSysApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSeatReservationSysApiApplication.class, args);
    }

}
