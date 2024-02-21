package com.example.adriatik.services;

import com.example.adriatik.entities.SetReservation;
import com.example.adriatik.repositories.SetReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetReservationServiceImpl implements SetReservationService {

    private final SetReservationRepository setReservationRepository;

    @Autowired
    public SetReservationServiceImpl(SetReservationRepository setReservationRepository) {
        this.setReservationRepository = setReservationRepository;
    }

    @Override
    public SetReservation addSetReservation(SetReservation setReservation) {
        return setReservationRepository.save(setReservation);
    }

    @Override
    public boolean deleteSetReservationById(Integer id) {
        if(setReservationRepository.existsById(id)) {
            setReservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public SetReservation editSetReservation(Integer id, SetReservation setReservation) {
        if(setReservationRepository.existsById(id)) {
            setReservation.setId(id);
            return setReservationRepository.save(setReservation);
        }
        return null;
    }

    @Override
    public SetReservation findById(Integer id) {
        return setReservationRepository.findById(id).orElse(null);
    }
}