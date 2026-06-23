package com.nagarro.BookManagement.csv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.BookManagement.entity.Book;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.web.multipart.MultipartFile;
import com.nagarro.BookManagement.entity.Author;


@Service
public class CsvService {

    public ByteArrayInputStream booksToCsv(List<Book> books) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PrintWriter writer = new PrintWriter(out);

        writer.println("Id,Title,ISBN,Price");

        for (Book book : books) {

            writer.println(
                    book.getId() + "," +
                    book.getTitle() + "," +
                    book.getIsbn() + "," +
                    book.getPrice());
        }

        writer.flush();

        return new ByteArrayInputStream(out.toByteArray());
    }
    public List<Book> csvToBooks(MultipartFile file)
            throws Exception {

        List<Book> books = new java.util.ArrayList<>();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                file.getInputStream()));

        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            Book book = new Book();

            book.setTitle(data[1]);
            book.setIsbn(data[2]);
            book.setPrice(Double.parseDouble(data[3]));

            books.add(book);
        }

        return books;
    }
    public ByteArrayInputStream authorsToCsv(List<Author> authors) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PrintWriter writer = new PrintWriter(out);

        writer.println("Id,Name,Email");

        for (Author author : authors) {

            writer.println(
                    author.getId() + "," +
                    author.getName() + "," +
                    author.getEmail());
        }

        writer.flush();

        return new ByteArrayInputStream(out.toByteArray());
    }
    public List<Author> csvToAuthors(MultipartFile file)
            throws Exception {

        List<Author> authors = new java.util.ArrayList<>();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                file.getInputStream()));

        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            Author author = new Author();

            author.setName(data[1]);
            author.setEmail(data[2]);

            authors.add(author);
        }

        return authors;
    }
}