package edu.up.cs301.game.util;

import android.util.Log;
import android.widget.Toast;

/**
 * Logger is a class used for all logging purposes.
 * It can perform regular logging as well as toast logging.
 *
 * Author: Nicole Kister
 */
public class Logger {
    //Switch for knowing if we are in debug mode or not. We assume by default we are not in debug mode
    private boolean debug = false;
    //Variables for the other types of logging available to students
    public static final int DEBUG = 0;
    public static final int WARN = 1;
    public static final int ERROR = 2;
    public static final int FAILURE = 3;

    /**
     * Constructor for the Logger.
     * The logger must know whether or not the game is in debug mode in order to know to print log
     * messages or not.
     *
     * @param debugMode
     */
    public Logger(boolean debugMode){
        debug = debugMode;
    }
    //Default constructor just in case we are in a class that doesn't have the game
    public Logger(){

    }
    /**
     * This is for regular logging. All that needs to be passed in are the logging tag and the message
     * Example usage: log("In method of recieveMessage", "Got this far!");
     *
     * @param loggingTag
     * @param logMessage
     */
    public void log(String loggingTag, String logMessage){
        Log.i(loggingTag, logMessage);
    }

    /**
     * This is for logging where the user specifies what type of logging they wish to have
     * Options include error, debug (which is different than our debugLog method), and warn
     * If none of these specific words are passed in then the regular Log.i will be used and a
     * message will be supplied saying the default was used because the word they passed in was not
     * one of the valid logging options.
     * The options to pass in are found in the logger object.
     * Example usage: l.log("In method of recieveInfo", "This shouldn't happen!", "l.ERROR");
     *
     * @param loggingTag
     * @param logMessage
     * @param logType
     */
    public void log(String loggingTag, String logMessage, int logType){
        switch (logType){
            //Debug logging
            case 0:
                Log.d(loggingTag, logMessage);
                break;
            //Warning logging
            case 1:
                Log.w(loggingTag, logMessage);
                break;
            //Error logging
            case 2:
                Log.e(loggingTag, logMessage);
                break;
            //Failure logging
            case 3:
                Log.wtf(loggingTag, logMessage);
                break;
            //If something other than the three cases is passed in, use default logging
            default:
                Log.i(loggingTag, logMessage);
                Log.w("LOGGER WARNING:", "The optional logging parameter passed in did not" +
                        "match one of the given logging types. See the Logger Class for more information.");
                break;
        }
    }

    /**
     * The debug logging will use Log.i logging, however it the messages will only appear if the
     * debug mode checkbox is selected.
     * If the logger object is created with no information about if we are in debug mode or not, we
     * assume by default the game is not in debug mode (this is the case where the student doesn't
     * pass in the game.debug to the Logger's constructor).
     *
     * @param loggingTag
     * @param logMessage
     */
    public void debugLog(String loggingTag, String logMessage){
        if (this.debug){
            Log.i(loggingTag, logMessage);
        }

        //If debug mode isn't selected, we won't do anything.
    }

    /**
     * This is for logging with toast notifications. The messages will only appear if the toast mode
     * checkbox is selected.
     */
    public void toast(){
    }
}
