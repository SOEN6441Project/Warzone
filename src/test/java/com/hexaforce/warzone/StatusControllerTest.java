package com.hexaforce.warzone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatusControllerTest {

  @Autowired private StatusController controller;

  @Test
  void showStatus() {
    assertThat(controller).isNotNull();
  }
}
