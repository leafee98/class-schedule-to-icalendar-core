package com.github.leafee98.CSTI.core.ics.model;

import com.github.leafee98.CSTI.core.ics.Component;
import com.github.leafee98.CSTI.core.ics.Property;

public class VAlarm extends Component {

    public static final String ACTION = "ACTION";
    public static final String TRIGGER = "TRIGGER";
    public static final String DESCRIPTION = "DESCRIPTION";

    private final Property action = new Property(ACTION);
    private final Property trigger = new Property(TRIGGER);
    private final Property description = new Property(DESCRIPTION);

    public VAlarm() {
        super("VALARM");
    }

    public Property getAction() {
        return action;
    }

    public Property getTrigger() {
        return trigger;
    }

    public Property getDescription() {
        return description;
    }

    public boolean isEmpty() {
        return action.isEmpty() && trigger.isEmpty() && description.isEmpty();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:").append(getName()).append('\n');

        if (! action.isEmpty()) builder.append(action.toString()).append('\n');
        if (! trigger.isEmpty()) builder.append(trigger.toString()).append('\n');
        if (! description.isEmpty()) builder.append(description.toString()).append('\n');

        builder.append("END:").append(getName());
        return builder.toString();
    }
}
