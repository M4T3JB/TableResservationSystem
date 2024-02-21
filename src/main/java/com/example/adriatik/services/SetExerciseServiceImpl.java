package com.example.adriatik.services;

import com.example.adriatik.entities.SetExercise;
import com.example.adriatik.repositories.SetExerciseRepository;
import com.example.adriatik.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class SetExerciseServiceImpl implements SetExerciseService{

    private final UserRepository userRepository;

    private final SetExerciseRepository setExerciseRepository;

    @Autowired
    public SetExerciseServiceImpl(UserRepository userRepository, SetExerciseRepository setExerciseRepository) {
        this.userRepository = userRepository;
        this.setExerciseRepository = setExerciseRepository;
    }

    @Override
    public SetExercise addSetExercise(SetExercise setExercise) {
        setExerciseRepository.save(setExercise);
        return setExercise;
    }
}
