package com.github.leafee98.CSTI.core.ics.model;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;

import java.util.ArrayList;
import java.util.List;

public class VEvent extends Component {

    public static final String CREATED = "CREATED";
    public static final String LAST_MODIFIED= "LAST-MODIFIED";
    public static final String DTSTAMP = "DTSTAMP";
    public static final String UID = "UID";
    public static final String SUMMARY = "SUMMARY";
    public static final String DTSTART = "DTSTART";
    public static final String DTEND = "DTEND";
    public static final String RRULE = "RRULE";
    public static final String TRANSP = "TRANSP";
    public static final String LOCATION = "LOCATION";
    public static final String DESCRIPTION = "DESCRIPTION";

    private final Property created = new Property(CREATED);
    private final Property lastModified = new Property(LAST_MODIFIED);
    private final Property dtStamp = new Property(DTSTAMP);
    private final Property uid = new Property(UID);
    private final Property summary = new Property(SUMMARY);
    private final Property dtStart = new Property(DTSTART);
    private final Property dtEnd = new Property(DTEND);
    private final Property rRule = new Property(RRULE);
    private final Property transp = new Property(TRANSP);
    private final Property location = new Property(LOCATION);
    private final Property description = new Property(DESCRIPTION);

    private final List<VAlarm> vAlarms = new ArrayList<>();

    public VEvent() {
        super("VEVENT");
    }

    public Property getCreated() {
        return created;
    }

    public Property getLastModified() {
        return lastModified;
    }

    public Property getDtStamp() {
        return dtStamp;
    }

    public Property getUid() {
        return uid;
    }

    public Property getSummary() {
        return summary;
    }

    public Property getDtStart() {
        return dtStart;
    }

    public Property getDtEnd() {
        return dtEnd;
    }

    public Property getRRule() {
        return rRule;
    }

    public Property getTransp() {
        return transp;
    }

    public Property getLocation() {
        return location;
    }

    public Property getDescription() {
        return description;
    }

    public List<VAlarm> getVAlarms() {
        return vAlarms;
    }

    public boolean isEmpty() {
        return created.isEmpty()
                && lastModified.isEmpty()
                && dtStamp.isEmpty()
                && uid.isEmpty()
                && summary.isEmpty()
                && dtStart.isEmpty()
                && dtEnd.isEmpty()
                && rRule.isEmpty()
                && transp.isEmpty()
                && location.isEmpty()
                && description.isEmpty()
                && vAlarms.isEmpty();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:").append(getName()).append('\n');

        if (! created.isEmpty()) builder.append(created.toString()).append('\n');
        if (! lastModified.isEmpty()) builder.append(lastModified.toString()).append('\n');
        if (! dtStamp.isEmpty()) builder.append(dtStamp.toString()).append('\n');
        if (! uid.isEmpty()) builder.append(uid.toString()).append('\n');
        if (! summary.isEmpty()) builder.append(summary.toString()).append('\n');
        if (! dtStart.isEmpty()) builder.append(dtStart.toString()).append('\n');
        if (! dtEnd.isEmpty()) builder.append(dtEnd.toString()).append('\n');
        if (! rRule.isEmpty()) builder.append(rRule.toString()).append('\n');
        if (! location.isEmpty()) builder.append(location.toString()).append('\n');
        if (! description.isEmpty()) builder.append(description.toString()).append('\n');
        if (! transp.isEmpty()) builder.append(transp.toString()).append('\n');

        for (VAlarm alarm : vAlarms) {
            builder.append(alarm).append('\n');
        }

        builder.append("END:").append(getName());
        return builder.toString();
    }
}
