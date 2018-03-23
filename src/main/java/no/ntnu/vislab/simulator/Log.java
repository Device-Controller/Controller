package no.ntnu.vislab.simulator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private final Date time;
    private final String tag;
    private final String log;
    private final String threadId;
    public static final String SYSTEM = "SYSTEM";
    public static final String CONNECTION = "CONNECTION";
    public static final String SENT_NORMAL = "SENT";
    public static final String SENT_INCORRECT = "SENT INCORRECT";
    public static final String SENT_DELAYED = "SENT DELAYED";
    public static final String SENT_NO_RESPONSE = "DIDN'T SEND";
    public static final String SENT_CORRUPTED = "SENT CORRUPT";
    public static final String SENT = "SENT";
    public static final String RECEIVED = "RECEIVED";
    public static final String GENERATED = "GENERATED";
    public static final String ERROR = "ERROR";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS, d MMM");


    public Log(Date time, String id, String tag, String log) {
        this.time = time;
        this.tag = tag;
        this.log = log;
        this.threadId = id;
    }

    @Override
    public String toString() {
        return dateFormat.format(time) + "  " + threadId + " " + tag + ": " + log;
    }

    public boolean hasTag(String tag) {
        return this.tag.equals(tag) || hasId(tag);
    }

    public boolean hasId(String id) {
        return threadId.equals(id);
    }

    public boolean logOlderThan(Date date) {
        return time.after(date);
    }

    public boolean logNewerThan(Date date) {
        return time.before(date);
    }

    public String getTag() {
        return tag;
    }

    public String getThreadId() {
        return threadId;
    }
}
