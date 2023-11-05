package com.hexaforce.warzone.models;
import java.util.Observable;
import com.hexaforce.warzone.utils.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 * The Logging Class that record every action takes place while game play.
 */
@Getter
@Setter
public class LogEntryBuffer extends Observable {
    /**
     * Log message to be stored.
     */
    String d_logMessage;

    /**
     * Constructor for initializing the class by adding Log writer as an observer
     * object.
     */
    public LogEntryBuffer() {
        // d_logMessages = new ArrayList<String>();
        Logger l_logger = new Logger();
        this.addObserver(l_logger);
    }
}
