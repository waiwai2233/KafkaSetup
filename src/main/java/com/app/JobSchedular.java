package com.app;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;

public class JobSchedular {

    public static void main(String[] args) {
        try {
            // Create the Scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            
            ArrayList<String> topicNames = new ArrayList<String>();
            topicNames.add("TEST_TOPIC_4");
            topicNames.add("TEST_TOPIC_5");
            topicNames.add("TEST_TOPIC_6");

            ArrayList<Date> startTimes = new ArrayList<Date>();
            startTimes.add(someStartTime(10));
            startTimes.add(someStartTime(5));
            startTimes.add(someStartTime(15));
            
            for (int i = 0; i < topicNames.size(); i++) {
                // Create JobDataMap to pass parameters
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("topicName", topicNames.get(i));

                // Define the job and tie it to our EventNotificationJob class
                JobDetail job = JobBuilder.newJob(EventNotificationJob.class)
                        .withIdentity("eventNotificationJob" + i, "group1")
                        .requestRecovery(true)  // Recover the running Jobs in case of crash
                        .usingJobData("topicName", topicNames.get(i)) 
                        .build();

                // Trigger the job to run at a specific time (e.g., 10 seconds from now)
                Trigger trigger = newTrigger()
                        .withIdentity("TEST_TRIGGER_NAME" + i, "group1")
                        .startAt(startTimes.get(i))  // Set the start time of the event here
                        .build();

                // Schedule the job using the trigger
                scheduler.scheduleJob(job, trigger);
            }

            // // Create JobDataMap to pass parameters
            // JobDataMap jobDataMap = new JobDataMap();
            // jobDataMap.put("topicName", "TEST_TOPIC_2");

            // // Define the job and tie it to our EventNotificationJob class
            // JobDetail job = JobBuilder.newJob(EventNotificationJob.class)
            //         .withIdentity("eventNotificationJob", "group1")
            //         .usingJobData("topicName", "TEST_TOPIC_2") 
            //         .build();

            // // Trigger the job to run at a specific time (e.g., 10 seconds from now)
            // Trigger trigger = newTrigger()
            //         .withIdentity("TEST_TRIGGER_NAME", "group1")
            //         .startAt(someStartTime(10))  // Set the start time of the event here
            //         .build();

            // // Schedule the job using the trigger
            // scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static Date someStartTime(int offset) {
        // Set the specific event start time here
        // offset is the number of seconds from now
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, offset);
        return cal.getTime();
    }
}