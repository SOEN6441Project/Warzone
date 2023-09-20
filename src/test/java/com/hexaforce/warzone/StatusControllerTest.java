package com.hexaforce.warzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class StatusControllerTest {

    @Autowired
    private StatusController controller;
    @Test
    void showStatus() {
        assertThat(controller).isNotNull();
    }
}