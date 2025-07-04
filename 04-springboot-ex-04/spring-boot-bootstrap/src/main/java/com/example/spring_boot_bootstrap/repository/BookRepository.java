package com.example.spring_boot_bootstrap.repository;

import com.example.spring_boot_bootstrap.model.Book;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
