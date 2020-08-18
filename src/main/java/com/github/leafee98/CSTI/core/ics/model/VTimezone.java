package com.github.leafee98.CSTI.core.ics.model;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;

public class VTimezone extends Component {

    public static final String TZID = "TZID";
    public static final String STANDARD = "STANDARD";
    public static final String DAYLIGHT = "DAYLIGHT";

    private final Property tzid = new Property(TZID);
    private Standard standard = new Standard();
    private Daylight daylight = new Daylight();

    public VTimezone() {
        super("VTIMEZONE");
    }

    public Property getTzid() {
        return tzid;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Daylight getDaylight() {
        return daylight;
    }

    public void setDaylight(Daylight daylight) {
        this.daylight = daylight;
    }

    public boolean isEmpty() {
        return tzid.isEmpty()
                && standard.isEmpty()
                && daylight.isEmpty();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:").append(getName()).append('\n');

        if (! tzid.isEmpty()) builder.append(tzid.toString()).append('\n');
        if (! standard.isEmpty()) builder.append(standard.toString()).append('\n');
        if (! daylight.isEmpty()) builder.append(daylight.toString()).append('\n');

        builder.append("END:").append(getName());
        return builder.toString();
    }
}
