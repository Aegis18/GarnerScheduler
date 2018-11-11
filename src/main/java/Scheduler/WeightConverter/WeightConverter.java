package Scheduler.WeightConverter;

import Scheduler.WeightUnits;

public class WeightConverter {


    public static double convertKgToTon(double weight){
        return weight / 907.185;
    }

    public static double convertLbsToTon(double weight){
        return weight / 2000;
    }

    public static double convertTonToKg(double weight){
        return weight * 907.185;
    }

    public static double convertTonTolbs(double weight){
        return weight * 2000;
    }

    public static double convertKgToLbs(double weight){
        return weight*2.205;
    }

    public static double convertLbsToKg(double weight){
        return weight/2.205;
    }
}
