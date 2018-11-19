package Scheduler.Entry;

import Scheduler.WeightConverter.WeightConverter;
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

    public static VehicleEntry convertToTruckEntry(String descriptor, String delimiter){
        if(descriptor == null || delimiter == null || delimiter.equals(""))
            throw new IllegalArgumentException("Invalid descriptor or delimiter");
        String[] properties = descriptor.split(delimiter);
        if(properties.length < 5)
            throw new IllegalArgumentException("Invalid parameters for parsing");
        int id = Integer.parseInt(properties[0]);
        String description = properties[1];
        double weight = Double.parseDouble(properties[2]);
        WeightUnits unit = null;
        switch(properties[3]){
            case "ton":
                unit = WeightUnits.TON;
                break;
            case "lbs":
                unit = WeightUnits.LBS;
                break;
            case "kg" :
                unit = WeightUnits.KG;
                break;
            default:
                throw new IllegalArgumentException("Invalid weight");
        }
        int priority = Integer.parseInt(properties[4]);
        return new TruckVehicleEntry(id, description, weight, unit, priority);
    }

    public void convertToTon(){
        if(unit == WeightUnits.TON)
            return;
        if(unit == WeightUnits.KG)
            weight = WeightConverter.convertKgToTon(weight);
        else
            weight = WeightConverter.convertLbsToTon(weight);
        unit = WeightUnits.TON;
    }
}
