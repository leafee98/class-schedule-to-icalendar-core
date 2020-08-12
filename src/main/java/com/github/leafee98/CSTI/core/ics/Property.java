package com.github.leafee98.CSTI.core.ics;

import com.github.leafee98.CSTI.core.exceptions.InvalidComponentParams;

import java.util.Map;
import java.util.TreeMap;

public class Property {

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

    public void setValue(Value value) {
        this.value = value;
    }

}
