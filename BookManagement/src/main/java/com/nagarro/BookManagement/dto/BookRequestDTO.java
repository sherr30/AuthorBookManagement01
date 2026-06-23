package com.nagarro.BookManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Pattern(
        regexp = "^[0-9-]+$",
        message = "ISBN can contain only numbers and hyphens"
    )
    private String isbn;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Author Id is required")
    private Long authorId;
}