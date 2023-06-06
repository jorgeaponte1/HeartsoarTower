package com.tlg.controller;

import com.tlg.controller.HeartsoarTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class HeartsoarTowerTest {
    private HeartsoarTower game;

    @BeforeEach
    public void setUp() throws IOException {
        game = new HeartsoarTower();
    }

//    @Test
//    public void gameInitialization() {
//        assertNotNull(game);
//    }
//
//    @Test
//    void commandExecution() {
//        String input = "move north";
//        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
//        assertEquals("move north", input);
//    }
}