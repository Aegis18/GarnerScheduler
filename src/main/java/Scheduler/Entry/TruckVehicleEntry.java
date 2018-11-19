package Scheduler.Entry;


import Scheduler.WeightConverter.WeightConverter;
import Scheduler.WeightUnits;

public final class TruckVehicleEntry extends VehicleEntry {

    public TruckVehicleEntry(int id, String description, double weight, WeightUnits unit, int priority){
        if(id < 1)
            throw new IllegalArgumentException("ID cannot be 0 or negative");
        if(description == null || description.equals(""))
            throw new IllegalArgumentException("Description cannot be null or empty");
        if(weight == 0 || weight < 0)
            throw new IllegalArgumentException("Must provide valid weight");
        if(priority < 1 || priority > 3)
            throw new IllegalArgumentException("Invalid priority value.");
        this.id = id;
        this.description = description;
        this.weight = weight;
        this.unit = unit;
        this.priority = priority;
    }

//    @Override
//    public static TruckVehicleEntry convert(String descriptor, String delimiter){
//        if(descriptor == null || delimiter == null || delimiter.equals(""))
//            throw new IllegalArgumentException("Invalid descriptor or delimiter");
//        String[] properties = descriptor.split(delimiter);
//        if(properties.length < 5)
//            throw new IllegalArgumentException("Invalid parameters for parsing");
//        int id = Integer.parseInt(properties[0]);
//        String description = properties[1];
//        double weight = Double.parseDouble(properties[2]);
//        WeightUnits unit = null;
//        switch(properties[3]){
//            case "ton":
//                unit = WeightUnits.TON;
//                break;
//            case "lbs":
//                unit = WeightUnits.LBS;
//                break;
//            case "kg" :
//                unit = WeightUnits.KG;
//                break;
//                default:
//                    throw new IllegalArgumentException("Invalid weight");
//        }
//        int priority = Integer.parseInt(properties[4]);
//        return new TruckVehicleEntry(id, description, weight, unit, priority);
//    }

    @Override
    public String toString(){
        return id + ", " + weight + ", " + unit + ", " + priority;
    }


//    private final int id;
//    private final String description;
//    private final double weight;
//    private final WeightUnits unit;
//    private final int priority;
//
//    private TruckVehicleEntry(TruckEntryBuilder truckEntryBuilder){
//        this.id = truckEntryBuilder.id;
//        this.description = truckEntryBuilder.description;
//        this.weight = truckEntryBuilder.weight;
//        this.unit = truckEntryBuilder.unit;
//        this.priority = truckEntryBuilder.priority;
//    }
//
//    public static class TruckEntryBuilder{
//        private int id;
//        private String description;
//        private double weight;
//        private WeightUnits unit;
//        private int priority;
//
//        public TruckEntryBuilder(int id){
//            if(id < 1)
//                throw new IllegalArgumentException("ID cannot be 0 or negative");
//            this.id = id;
//        }
//
//        public TruckEntryBuilder description(String description){
//            if(description == null || description.equals(""))
//                throw new IllegalArgumentException("Description cannot be null or empty");
//            this.description = description;
//            return this;
//        }
//
//        public TruckEntryBuilder weight(double weight){
//            if(weight == 0 || weight < 0)
//                throw new IllegalArgumentException("Must provide valid weight");
//            this.weight = weight;
//            return this;
//        }
//
//        public TruckEntryBuilder unit(WeightUnits weightUnit){
//            this.unit = weightUnit;
//            return this;
//        }
//
//        public TruckEntryBuilder priority(int priority){
//            if(priority < 1 || priority > 3)
//                throw new IllegalArgumentException("Invalid priority value.");
//            this.priority = priority;
//            return this;
//        }
//
//        public TruckVehicleEntry build(){
//
//            return new TruckVehicleEntry(this);
//        }
//    }
//
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

}
