package com.priorityqueue.springboot.service;

import com.priorityqueue.springboot.model.WorkOrder;
import com.priorityqueue.springboot.util.*;
import java.util.Date;
import java.util.List;

public interface PriorityQueueService {
	
	void addWorkOrder(WorkOrder wo);
        
        List<WorkOrder> findAllWorkOrders();
        
        WorkOrder findById(long id);
        
        void deleteWorkOrderById(long id);
        
        WorkOrder findTopWorkOrder();
        
        void deleteTopWorkOrder();
        
	boolean isWorkOrderExist(long id);
        
        String getPriorityClassOfID(long id);
        
        List<WorkOrder> calculateRankOfWorkOrder(List<WorkOrder> workOrder);
        
        PositionOfWorkOrder getPosOfId(List<WorkOrder> wo, long id);
        
        AverageWaitTime getAvgWaitTime(Date curDate);
}
