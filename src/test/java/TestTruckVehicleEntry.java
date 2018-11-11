import Scheduler.Entry.TruckVehicleEntry;
import Scheduler.ListConverter.CSVListConverter;
import Scheduler.WeightUnits;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

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

    @Test
    public void testConvert(){
        try {
            CSVListConverter csvListConverter = new CSVListConverter("C:\\Users\\range\\Documents\\Garner\\GarnerScheduler\\csvTest.csv");
            List<String> list = csvListConverter.convertToList();
            TruckVehicleEntry tve = TruckVehicleEntry.convert(list.get(0), ",");
            System.out.println(tve.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
