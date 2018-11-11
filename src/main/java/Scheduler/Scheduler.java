package Scheduler;

import Scheduler.ListConverter.CSVListConverter;
import Scheduler.ListConverter.ListConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Scheduler {

    public final int PRIORITY_ACCENDING = 0;
    public final int PRIORITY_DECENDING = 1;
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
        private LocalDate startDate;
        private LocalTime startTime;
        private ListConverter listConverter;

        public SchedulerBuilder(String filePath, LocalDate startDate, LocalTime startTime) throws IOException {
            this.priorityOrder = 0;
            this.daysToBeCompleted = 0;
            this.tempMaxWeight = 0;
            this.tempMaxWeightDays =0;
            this.maxSlots = 0;
            this.startDate = startDate;
            this.startTime = startTime;
            this.listConverter = new CSVListConverter(filePath);
        }
    }
}
