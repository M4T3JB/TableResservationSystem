package com.example.adriatik.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramPayload {
    private Integer id;

    private Integer employeeId;

    private Integer clientId;

    private List<Integer> setExerciseId;
}
