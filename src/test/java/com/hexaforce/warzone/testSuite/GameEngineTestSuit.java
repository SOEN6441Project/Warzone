package com.hexaforce.warzone.testSuite;

import com.hexaforce.warzone.Utils.CommandTest;
import com.hexaforce.warzone.models.*;
import com.hexaforce.warzone.services.ConquestMapFileReaderTest;
import com.hexaforce.warzone.services.DominationMapFileReaderTest;
import com.hexaforce.warzone.services.MapServiceTest;
import com.hexaforce.warzone.services.PlayerServiceTest;
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
        OrderExecutionPhaseTest.class,
        MapServiceTest.class,
        PlayerServiceTest.class,
        ConquestMapFileReaderTest.class,
        DominationMapFileReaderTest.class,
        AggressivePlayerTest.class,
        TournamentTest.class, 
        CommandTest.class,
        RandomPlayerTest.class
})
public class GameEngineTestSuit {
}
