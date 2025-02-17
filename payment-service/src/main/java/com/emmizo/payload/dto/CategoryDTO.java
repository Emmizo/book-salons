package com.emmizo.payload.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
}
