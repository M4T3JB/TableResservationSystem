package com.example.adriatik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestPayload {
    private Integer id;

    private Integer client;

    private Integer employee;

    private String description;
}
