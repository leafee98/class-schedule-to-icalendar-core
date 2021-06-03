package com.github.leafee98.CSTI.core.ics;

import com.github.leafee98.CSTI.core.exceptions.InvalidComponentParams;

import java.util.Map;
import java.util.TreeMap;

public class Property {
    public static final String SUMMARY = "SUMMARY";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LOCATION = "LOCATION";
    public static final String ACTION = "ACTION";
    public static final String TRANSP = "TRANSP";
    public static final String PRODID = "PRODID";
    public static final String TZID = "TZID";
    public static final String TZNAME = "TZNAME";
    public static final String UID = "UID";

    private final String name;
    private final Map<String, String> parameters = new TreeMap<>();
    private Value value;

    public Property(String name, Value value) {
        this.name = name;
        this.value = value;
    }

    public Property(String name) {
        this(name, new Value(""));
    }

    public Property(String name, Value value, String... params) {
        this(name, value);
        if ((params.length & 1) != 1) {
            for (int i = 0; i < params.length; i += 2)
                this.putParameter(params[i], params[i + 1]);
        } else {
            throw new InvalidComponentParams("params must be \"Key1, Value1, Key2, Value2\" " +
                    "and its length must be even.");
        }
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public String toString() {



        StringBuilder builder = new StringBuilder(name);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.append(';');
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
        }

        builder.append(':');

        // escape value or not
        if (this.name.equals(SUMMARY)
                || this.name.equals(DESCRIPTION)
                || this.name.equals(LOCATION)
                || this.name.equals(ACTION)
                || this.name.equals(TRANSP)
                || this.name.equals(PRODID)
                || this.name.equals(TZID)
                || this.name.equals(TZNAME)
                || this.name.equals(UID))
            builder.append(value.toStringEscape());
        else
            builder.append(value);

        return builder.toString();
    }

    public void putParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(String value) {
        this.setValue(new Value(value));
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
