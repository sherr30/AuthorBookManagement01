package com.nagarro.BookManagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorResponseDTO {

    private Long id;
    private String name;
    private String email;
}