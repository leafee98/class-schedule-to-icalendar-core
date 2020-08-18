package com.github.leafee98.CSTI.core.ics.model;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;

public class VEvent extends Component {

    public static final String CREATED = "CREATED";
    public static final String LAST_MODIFIED= "LAST-MODIFIED";
    public static final String DTSTAMP = "DTSTAMP";
    public static final String UID = "UID";
    public static final String SUMMARY = "SUMMARY";
    public static final String DTSTART = "DTSTART";
    public static final String DTEND = "DTEND";
    public static final String TRANSP = "TRANSP";
    public static final String LOCATION = "LOCATION";
    public static final String DESCRIPTION = "DESCRIPTION";

    public final Property created = new Property(CREATED);
    public final Property lastModified = new Property(LAST_MODIFIED);
    public final Property dtStamp = new Property(DTSTAMP);
    public final Property uid = new Property(UID);
    public final Property summary = new Property(SUMMARY);
    public final Property dtStart = new Property(DTSTART);
    public final Property dtEnd = new Property(DTEND);
    public final Property transp = new Property(TRANSP);
    public final Property location = new Property(LOCATION);
    public final Property description = new Property(DESCRIPTION);

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

    public Property getTransp() {
        return transp;
    }

    public Property getLocation() {
        return location;
    }

    public Property getDescription() {
        return description;
    }


    public boolean isEmpty() {
        return created.isEmpty()
                && lastModified.isEmpty()
                && dtStamp.isEmpty()
                && uid.isEmpty()
                && summary.isEmpty()
                && dtStart.isEmpty()
                && dtEnd.isEmpty()
                && transp.isEmpty()
                && location.isEmpty()
                && description.isEmpty();
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
        if (! location.isEmpty()) builder.append(location.toString()).append('\n');
        if (! description.isEmpty()) builder.append(description.toString()).append('\n');
        if (! transp.isEmpty()) builder.append(transp.toString()).append('\n');

        builder.append("END:").append(getName());
        return builder.toString();
    }
}
