package com.priorityqueue.springboot.util;

import java.util.Date;

public class DateTimeManipulation {
    private long noOfSeconds;

    public long getNoOfSeconds() {
        return noOfSeconds;
    }

    public void setNoOfSeconds(long noOfSeconds) {
        this.noOfSeconds = noOfSeconds;
    }
    
    public long calculateWaitTimeInSeconds(Date WorkOrderAddedTime)
    {
        Date curDate = new Date();
        noOfSeconds = (curDate.getTime() - WorkOrderAddedTime.getTime())/1000;
        return noOfSeconds;
    }
}
