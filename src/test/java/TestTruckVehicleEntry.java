import Scheduler.Entry.TruckVehicleEntry;
import Scheduler.Entry.VehicleEntry;
import Scheduler.Scheduler;
import Scheduler.WeightUnits;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;

public class TestTruckVehicleEntry {

    protected int id = 1;
    protected String description = "test";
    protected double weight = 2;
    protected WeightUnits unit = WeightUnits.TON;
    protected int priority = 1;

    @Test
    public void testTruckVehicleEntry(){
        TruckVehicleEntry truckVehicleEntry1 = new TruckVehicleEntry(id, description, weight, unit, priority);
        id = 0;
        try {
            TruckVehicleEntry truckVehicleEntry2 = new TruckVehicleEntry(id, description, weight, unit, priority);
        }catch (IllegalArgumentException e){
            assert true;
        }
    }

//    @Test
//    public void testConvert(){
//
//        String fileName = "C:\\Users\\Avi\\Documents\\AtChat\\munchies\\GarnerScheduler\\csvTest.csv";
//        try {
//            Scheduler scheduler = new Scheduler.SchedulerBuilder(fileName, LocalDateTime.now()).build();
//            List list = scheduler.generateList();
//            List<VehicleEntry> l = scheduler.validateList(list);
//            for (VehicleEntry v: l) {
//                System.out.println(((TruckVehicleEntry)v).toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testSort(){

        String fileName = "C:\\Users\\Avi\\Documents\\AtChat\\munchies\\GarnerScheduler\\csvTest.csv";
        try {
            Scheduler scheduler = new Scheduler.SchedulerBuilder(fileName, LocalDateTime.now()).tempMaxWeight(15)
                                                                                                .tempMaxWeightDays(15)
                    .daysToBeCompleted(60).build();
            List<VehicleEntry> list =scheduler.schedule();
            for(VehicleEntry v : list)
                System.out.println(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
