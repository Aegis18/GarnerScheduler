package Scheduler;

import Scheduler.Entry.VehicleEntry;
import Scheduler.ListConverter.CSVListConverter;
import Scheduler.ListConverter.ListConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Scheduler {

    public static final int PRIORITY_ASCENDING = 0;
    public static final int PRIORITY_DECENDING = 1;
    private final ListConverter listConverter;
    private final int priorityOrder;

    private Scheduler(SchedulerBuilder schedulerBuilder){
        this.listConverter = schedulerBuilder.listConverter;
        this.priorityOrder = schedulerBuilder.priorityOrder;
    }

    public static class SchedulerBuilder {
        private int daysToBeCompleted;
        private int tempMaxWeight;
        private int tempMaxWeightDays;
        private int priorityOrder;
        private int maxSlots;
        private String filePath;
        private String directoryOutput;
        private ListConverter listConverter;
        private LocalDateTime startDateTime;

        public SchedulerBuilder(String filePath, LocalDateTime localDateTime) throws IOException {
            this.priorityOrder = 0;
            this.daysToBeCompleted = 0;
            this.tempMaxWeight = 0;
            this.tempMaxWeightDays =0;
            this.maxSlots = 0;
        }

        public SchedulerBuilder priorityOrder(int priorityOrder){
            if(priorityOrder < 0 || priorityOrder > 1)
                throw new IllegalArgumentException("Priority Order must be either: PRIORITY_ASCENDING OR PRIORITY DESCENDING");
            this.priorityOrder = priorityOrder;
            return this;
        }

        public SchedulerBuilder daysToBeCompleted(int days){
            if(days < 0)
                throw new IllegalArgumentException("Cannot have negative days");
            daysToBeCompleted = days;
            return this;
        }

        public SchedulerBuilder tempMaxWeight(int tempMaxWeight){
            if(tempMaxWeight < 0)
                throw new IllegalArgumentException("Cannot have negative tempMaxWeight");
            this.tempMaxWeight = tempMaxWeight;
            return this;
        }

        public SchedulerBuilder tempMaxWeightDays(int days){
            if(days < 0)
                throw new IllegalArgumentException("Cannot have negative tempMaxWeightDays");
            tempMaxWeightDays = days;
            return this;
        }

        public SchedulerBuilder maxSlots(int maxSlots){
            if(maxSlots < 0)
                throw new IllegalArgumentException("Cannot have negative max slots");
            this.maxSlots = maxSlots;
            return this;
        }

        public Scheduler build() throws IOException {
            if(listConverter == null)
                listConverter = new CSVListConverter(filePath);
            return new Scheduler(this);
        }
    }

    private List generateList(){
        return listConverter.convertToList();
    }

    private List validateList(List list){
        Iterator<VehicleEntry> it = list.iterator();
        
        return null;
    }

    private List sort(){
        return null;
    }

    private PriorityQueue sortByMinWeight(List list){
        PriorityQueue<VehicleEntry> queue = new PriorityQueue(list.size(), new VehicleMinWeightComparator());
        queue.addAll(list);
        return queue;
    }

    private PriorityQueue sortByAscendingPriority(List list){

        return null;
    }

    private class VehicleAscendingPriorityComparator implements Comparator<VehicleEntry> {
        @Override
        public int compare(VehicleEntry vehicleEntry, VehicleEntry t1) {
            if(vehicleEntry.getPriority() == t1.getPriority())
                return 0;
            return (vehicleEntry.getPriority() < t1.getPriority()) ? 1 : -1;
        }
    }

    private class VehicleDescendingPriorityComparator implements Comparator<VehicleEntry>{
        @Override
        public int compare(VehicleEntry vehicleEntry, VehicleEntry t1) {
            if(vehicleEntry.getPriority() == t1.getPriority())
                return 0;
            return (vehicleEntry.getPriority() > t1.getPriority()) ? 1 : -1;
        }
    }

    private class VehicleMinWeightComparator implements Comparator<VehicleEntry>{
        @Override
        public int compare(VehicleEntry o1, VehicleEntry o2) {
            if(o1.getWeight() == o2.getWeight())
                return 0;
            return (o1.getWeight() < o2.getWeight()) ? 1 : -1;
        }
    }
}
