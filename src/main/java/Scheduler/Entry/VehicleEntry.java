package Scheduler.Entry;

import Scheduler.WeightUnits;

public abstract class VehicleEntry {
    protected int id;
    protected String description;
    protected double weight;
    protected WeightUnits unit;
    protected int priority;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public WeightUnits getUnit() {
        return unit;
    }

    public int getPriority() {
        return priority;
    }

//    public abstract VehicleEntry convert(String descriptor, String delimiter);

}
