package com.github.leafee98.CSTI.core.ics.model;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;

public class Standard extends Component {

    public static final String TZOFFSETFROM = "TZOFFSETFROM";
    public static final String TZOFFSETTO = "TZOFFSETTO";
    public static final String TZNAME = "TZNAME";
    public static final String DTSTART = "DTSTART";
    public static final String RRULE = "RRULE";

    private final Property tzOffsetFrom = new Property(TZOFFSETFROM);
    private final Property tzOffsetTo = new Property(TZOFFSETTO);
    private final Property tzName = new Property(TZNAME);
    private final Property dtStart = new Property(DTSTART);
    private final Property rRule = new Property(RRULE);

    public Standard() {
        super("STANDARD");
    }

    public Property getTzOffsetFrom() {
        return tzOffsetFrom;
    }

    public Property getTzOffsetTo() {
        return tzOffsetTo;
    }

    public Property getTzName() {
        return tzName;
    }

    public Property getDtStart() {
        return dtStart;
    }

    public Property getrRule() {
        return rRule;
    }

    boolean isEmpty() {
        return tzOffsetFrom.isEmpty()
                && tzOffsetTo.isEmpty()
                && tzName.isEmpty()
                && dtStart.isEmpty()
                && rRule.isEmpty();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:").append(getName()).append('\n');

        if (! tzOffsetFrom.isEmpty()) builder.append(tzOffsetFrom.toString()).append('\n');
        if (! tzOffsetTo.isEmpty()) builder.append(tzOffsetTo.toString()).append('\n');
        if (! tzName.isEmpty()) builder.append(tzName.toString()).append('\n');
        if (! dtStart.isEmpty()) builder.append(dtStart.toString()).append('\n');
        if (! rRule.isEmpty()) builder.append(rRule.toString()).append('\n');

        builder.append("END:").append(getName());
        return builder.toString();
    }
}
