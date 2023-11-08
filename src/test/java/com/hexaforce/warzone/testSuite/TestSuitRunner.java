package com.hexaforce.warzone.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** Test suit for running all test suites involved in this game project */
@RunWith(Suite.class)
@SuiteClasses({
  GameEngineTestSuit.class,
})
public class TestSuitRunner {}
