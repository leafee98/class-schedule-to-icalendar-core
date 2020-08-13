package com.github.leafee98.CSTI.core.ics;

import com.github.leafee98.CSTI.core.exceptions.InvalidComponentParams;

import java.util.Map;
import java.util.TreeMap;

public class Value {

    private String value;
    private final Map<String, String> parameters = new TreeMap<>();

    public Value() {
        this("");
    }

    public Value(String value) {
        this.value =  value;
    }

    public Value(String value, String... params) {
        this.value = value;
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
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append(';');
        }
        if (value == null || value.length() == 0) {
            return builder.substring(0, builder.length() - 1);
        } else {
            builder.append(value);
            return builder.toString();
        }
    }

    public void putParameter(String param, String value) {
        parameters.put(param, value);
    }

    public String getParameter(String param) {
        return parameters.get(param);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}