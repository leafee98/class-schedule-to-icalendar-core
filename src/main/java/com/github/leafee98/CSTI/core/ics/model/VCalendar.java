package com.github.leafee98.CSTI.core.ics.model;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;
import com.github.leafee98.CSTI.core.ics.Value;

import java.util.ArrayList;
import java.util.List;

public class VCalendar extends Component {

    public static final String VCALENDAR = "VCALENDAR";
    public static final String VTIMEZONE = "VTIMEZONE";
    public static final String VEVENT = "VEVENT";
    public static final String PRODID = "PRODID";
    public static final String VERSION = "VERSION";

    private final Property prodId = new Property(PRODID);
    private final Property version = new Property(VERSION);
    private VTimeZone timeZone = new VTimeZone();
    private final List<VEvent> vEvents = new ArrayList<>();

    public VCalendar() {
        super(VCALENDAR);
        this.getVersion().setValue(new Value("2.0"));
        this.getProdId().setValue(new Value("class-schedule-to-icalendar"));
    }

    public Property getProdId() {
        return prodId;
    }

    public Property getVersion() {
        return version;
    }

    public VTimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(VTimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public List<VEvent> getVEvents() {
        return vEvents;
    }

    public boolean isEmpty() {
        return prodId.isEmpty()
                && version.isEmpty()
                && timeZone.isEmpty()
                && vEvents.isEmpty();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:").append(getName()).append('\n');

        if (! prodId.isEmpty()) builder.append(prodId.toString()).append('\n');
        if (! version.isEmpty()) builder.append(version.toString()).append('\n');
        if (! timeZone.isEmpty()) builder.append(timeZone.toString()).append('\n');

        for (VEvent event : vEvents)
            builder.append(event).append('\n');

        builder.append("END:").append(getName());
        return builder.toString();
    }
}
