package com.github.leafee98.CSTI.core.ics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Component {

    private final String name;
    private final Map<String, Property> properties = new TreeMap<>();
    private final List<Component> components = new ArrayList<>();

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

        for (Component c : components) {
            builder.append(c);
            builder.append('\n');
        }

        builder.append(String.format("END:%s", name));

        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void addProperty(Property p) {
        properties.put(p.getName(), p);
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public Property getProperty(String name){
        return properties.get(name);
    }

    public List<Component> getComponents() {
        return this.components;
    }

}
