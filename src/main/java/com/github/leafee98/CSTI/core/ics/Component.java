package com.github.leafee98.CSTI.core.ics;

import java.util.Map;
import java.util.TreeMap;

public class Component {

    private final String name;
    private final Map<String, Property> properties = new TreeMap<>();
    private final Map<String, Component> components = new TreeMap<>();

    public Component(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("BEGIN:%s\n", name));

        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            builder.append(entry.getValue());
            builder.append('\n');
        }

        for (Map.Entry<String, Component> entry : components.entrySet()) {
            builder.append(entry.getValue());
            builder.append('\n');
        }

        builder.append(String.format("END:%s", name));

        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void add(Property p) {
        properties.put(p.getName(), p);
    }

    public void add(Component c) {
        components.put(c.getName(), c);
    }

    public Property getProperty(String name){
        return properties.get(name);
    }

    public Component getComponent(String name) {
        return components.get(name);
    }

}
