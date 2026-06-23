package com.nagarro.BookManagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {

    private Long id;
    private String title;
    private String isbn;
    private Double price;

    private Long authorId;
    private String authorName;
}