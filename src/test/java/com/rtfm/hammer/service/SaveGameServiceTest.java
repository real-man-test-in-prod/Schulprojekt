package com.rtfm.hammer.service;

import com.rtfm.hammer.model.SaveGame;
import com.rtfm.hammer.repository.SaveGameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveGameServiceTest {

    @Mock
    private SaveGameRepository saveGameRepository;

    @Mock
    private SaveCodeGenerator saveCodeGenerator;

    @InjectMocks
    private SaveGameService saveGameService;

    @Test
    void shouldSaveGameWithUniqueCode() {
        String code = "ABCD-EFGH-IJKL";
        when(saveCodeGenerator.generate()).thenReturn(code);
        when(saveGameRepository.findByCode(code)).thenReturn(Optional.empty());

        String result = saveGameService.save("Player", "state", LocalDate.now());

        assertEquals(code, result);
        verify(saveGameRepository).save(any(SaveGame.class));
    }

    @Test
    void shouldRegenerateCodeWhenDuplicate() {
        String code1 = "ABCD-EFGH-IJKL";
        String code2 = "MNOP-QRST-UVWX";
        when(saveCodeGenerator.generate()).thenReturn(code1, code2);
        when(saveGameRepository.findByCode(code1)).thenReturn(Optional.of(new SaveGame()));
        when(saveGameRepository.findByCode(code2)).thenReturn(Optional.empty());

        String result = saveGameService.save("Player", "state", LocalDate.now());

        assertEquals(code2, result);
    }

    @Test
    void shouldLoadGameByCode() {
        SaveGame saveGame = new SaveGame();
        saveGame.setCode("ABCD-EFGH-IJKL");
        when(saveGameRepository.findByCode("ABCD-EFGH-IJKL")).thenReturn(Optional.of(saveGame));

        SaveGame result = saveGameService.loadByCode("abcd-efgh-ijkl");

        assertEquals(saveGame, result);
    }

    @Test
    void shouldThrowWhenCodeNotFound() {
        when(saveGameRepository.findByCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> saveGameService.loadByCode("invalid"));
    }

    @Test
    void shouldUpdateGameState() {
        SaveGame saveGame = new SaveGame();
        saveGame.setCode("ABCD-EFGH-IJKL");
        when(saveGameRepository.findByCode("ABCD-EFGH-IJKL")).thenReturn(Optional.of(saveGame));

        String result = saveGameService.update(saveGame.getCode(), "new state", LocalDate.now());

        assertEquals("ABCD-EFGH-IJKL", result);
        verify(saveGameRepository).save(saveGame);
        assertEquals("new state", saveGame.getSaveGameState());
    }
}