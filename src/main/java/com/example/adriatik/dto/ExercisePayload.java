package com.example.adriatik.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExercisePayload {

    private Integer id;

    private String name;

    private String description;

}
