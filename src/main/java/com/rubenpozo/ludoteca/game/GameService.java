package com.rubenpozo.ludoteca.game;

import java.util.List;

import com.rubenpozo.ludoteca.game.model.Game;
import com.rubenpozo.ludoteca.game.model.GameDto;

public interface GameService {
    List<Game> find(String title, Long idCategory);

    void save(Long id, GameDto dto);
}
