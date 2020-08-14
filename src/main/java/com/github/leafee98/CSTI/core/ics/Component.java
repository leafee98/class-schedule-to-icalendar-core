package com.github.leafee98.CSTI.core.ics;

import java.util.List;
import java.util.Map;

public abstract class Component {

    private final String name;

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String toString();

}
