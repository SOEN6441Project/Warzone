package com.hexaforce.warzone.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import java.util.Observer;

import com.hexaforce.warzone.models.LogEntryBuffer;

/**
 * This class writes the logs to the file.
 * Class.
 */
public class Logger implements Observer {

    /**
     * Current LogEntryBuffer observable instance.
     */
    LogEntryBuffer d_logEntryBuffer;

    /**
     * Writes the updated LogEntryBuffer instance into Log file.
     *
     * @param p_observable LogEntryBuffer Object.
     * @param p_object     Object
     */
    @Override
    public void update(Observable p_observable, Object p_object) {
        d_logEntryBuffer = (LogEntryBuffer) p_observable;
        File l_logfile = new File("Logs.txt");
        String l_logMessage = d_logEntryBuffer.getD_logMessage();
        try {
            if (l_logMessage.equals("Game is Starting..." + System.lineSeparator() + System.lineSeparator())) {
                Files.newBufferedWriter(Paths.get("Logs.txt"), StandardOpenOption.TRUNCATE_EXISTING).write(" ");
            }
            Files.write(Paths.get("Logs.txt"), l_logMessage.getBytes(StandardCharsets.US_ASCII),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception l_e) {
            l_e.printStackTrace();
        }
    }
}
