package Scheduler;

import Scheduler.Entry.VehicleEntry;
import Scheduler.ListConverter.PlainTextListConverter;
import Scheduler.ListConverter.ListConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Scheduler <S extends VehicleEntry> {

    public static final int PRIORITY_ASCENDING = 0;
    public static final int PRIORITY_DECENDING = 1;
    private final ListConverter listConverter;
    private final int priorityOrder;
    private final int daysToCompleted;
    private final int tempMaxWeight;
    private final int tempMaxWeightDays;
    private int days;
    private int maxSlot;
    private final LocalDateTime startDate;

    private Scheduler(SchedulerBuilder schedulerBuilder) {
        this.listConverter = schedulerBuilder.listConverter;
        this.priorityOrder = schedulerBuilder.priorityOrder;
        this.daysToCompleted = schedulerBuilder.daysToBeCompleted;
        this.tempMaxWeight = schedulerBuilder.tempMaxWeight;
        this.tempMaxWeightDays = schedulerBuilder.tempMaxWeightDays;
        this.days = 0;
        this.startDate = schedulerBuilder.startDateTime;
    }

    public static class SchedulerBuilder<S extends VehicleEntry>{
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
            this.filePath = filePath;
            this.priorityOrder = 0;
            this.daysToBeCompleted = 0;
            this.tempMaxWeight = 0;
            this.tempMaxWeightDays =0;
            this.maxSlots = 0;
            if(Objects.nonNull(localDateTime))
            this.startDateTime = localDateTime;
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
                listConverter = new PlainTextListConverter(filePath);
            if(tempMaxWeight != 0 && tempMaxWeightDays > daysToBeCompleted)
                throw new IllegalArgumentException("Temp Days cannot be less than Days to be completed");
            return new Scheduler(this);
        }
    }

    public List<VehicleEntry> schedule(){
        LinkedList<VehicleEntry> finalList = new LinkedList<VehicleEntry>();
        //Generate the requested list
        List<String> generatedList = generateList();
        //Convert String list to list of Vehicle Entries
        List<VehicleEntry> vehicleList = generateVehicleList(generatedList);
        //Do the sorting based on mine Weight
        sortByMinWeightThenPriority(vehicleList);

//        <------Temp-Days-Condition------>
        Iterator<VehicleEntry> it = vehicleList.iterator();
        LocalDateTime dateCounter = startDate;
        LocalDateTime endTempWeightDay = startDate.plusDays(15);
        endTempWeightDay = endTempWeightDay.minusHours(endTempWeightDay.getHour());
        endTempWeightDay = endTempWeightDay.minusMinutes(endTempWeightDay.getMinute());
        int slot = 1;
        while(it.hasNext()){
            //is during the temp days
            VehicleEntry vehicleEntry = (VehicleEntry) it.next();
            if(dateCounter.isBefore(endTempWeightDay)) {
                if (vehicleEntry.getWeight() > tempMaxWeight) {
                    continue;
                }
                System.out.println("V is less than 15");
                finalList.addLast(vehicleEntry);
                it.remove();
                slot++;
                if(slot == maxSlot){
                    slot = 1;
                    dateCounter = dateCounter.plusHours(1);
                }
            }else
                break;
        }
        return finalList;
    }

    //Generate the List
    public List<String> generateList(){
        return listConverter.convertToList();
    }

    //With Delimiter
    private String getDelimiter(){
        if(listConverter instanceof PlainTextListConverter)
            return ",";
        else return "";
    }

    //Validate List and remove any entries that do not belong
    //Convert all weights to TON

    public List<VehicleEntry> generateVehicleList(List list){
        Iterator<String> it = list.iterator();
        List<VehicleEntry> vehicleEntryList = new ArrayList<VehicleEntry>(list.size());
        int counter = 1;
        while(it.hasNext()){
            String s = (String) it.next();
            try{
                VehicleEntry vehicleEntry = VehicleEntry.convertToTruckEntry(s, getDelimiter());
                vehicleEntry.convertToTon();
                vehicleEntryList.add(vehicleEntry);
            }catch (Exception e){
                it.remove();
                System.out.println("Issue on line: " + counter + ", line will be skipped");
                System.out.println("Line info: " + s);
                System.out.println();
            }finally {
                counter++;
            }
        }
        return vehicleEntryList;
    }

    //sort the list depending on specifications
    private void sortByMinWeightThenPriority(List<VehicleEntry> list){
        list.sort(new VehicleAscendingPriorityComparator().thenComparing(new VehicleMinWeightComparator()));
//        return list;
    }

    private void sortByMinWeight(List<VehicleEntry> list, VehicleMinWeightComparator c){
        list.sort(c);
//        return list;
    }

    private void sortByAscendingPriority(List<VehicleEntry> list, VehicleAscendingPriorityComparator c){
        list.sort(c);
//        return list;
    }



// <-------------------------------------COMPARATORS--------------------------------------------------->

    private class VehicleMinWeightAndAscendingPriorityComparator implements Comparator<VehicleEntry>{

        @Override
        public int compare(VehicleEntry o1, VehicleEntry o2) {
            if(o1.getPriority() == o2.getPriority()){
                if(o1.getWeight() > o2.getWeight())
                    return 1;
                else if (o1.getWeight() < o1.getWeight())
                    return -1;
                else
                    return 0;
            }
            if(o1.getPriority() < o2.getPriority())
            return -1;
            return 0;
        }
    }

    private class VehicleAscendingPriorityComparator implements Comparator<VehicleEntry> {
        @Override
        public int compare(VehicleEntry vehicleEntry, VehicleEntry t1) {
            if(vehicleEntry.getPriority() == t1.getPriority())
                return 0;
            return (vehicleEntry.getPriority() > t1.getPriority()) ? 1 : -1;
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
            return (o1.getWeight() > o2.getWeight()) ? 1 : -1;
        }
    }
}
