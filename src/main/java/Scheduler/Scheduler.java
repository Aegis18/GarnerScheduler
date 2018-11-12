package Scheduler;

import Scheduler.ListConverter.CSVListConverter;
import Scheduler.ListConverter.ListConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Scheduler {

    public static final int PRIORITY_ASCENDING = 0;
    public static final int PRIORITY_DECENDING = 1;
//    private final ListConverter listConverter;

    private Scheduler(SchedulerBuilder schedulerBuilder){
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

        private List generateList(){
            List<String> list = listConverter.convertToList();
            return list;
        }
    }
}
