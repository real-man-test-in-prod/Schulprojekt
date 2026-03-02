package com.rtfm.hammer.repository;


import com.rtfm.hammer.model.SaveGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaveGameRepository extends JpaRepository<SaveGame, Long> {
    Optional<SaveGame> findByCode(String code);
}