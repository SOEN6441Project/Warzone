package com.hexaforce.warzone.testSuite;

import com.hexaforce.warzone.models.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  MapTest.class,
  PlayerTest.class,
  AdvanceTest.class,
  AirliftTest.class,
  BlockadeTest.class,
  BombTest.class,
  DiplomacyTest.class,
  OrderExecutionPhaseTest.class
})
public class GameEngineTestSuit {}
