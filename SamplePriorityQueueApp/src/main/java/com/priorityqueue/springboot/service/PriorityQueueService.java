package com.priorityqueue.springboot.service;


import java.util.List;

import com.priorityqueue.springboot.model.WorkOrder;

public interface PriorityQueueService {
	
	void addWorkOrder(WorkOrder wo);
        
        List<WorkOrder> findAllWorkOrders();
        
        WorkOrder findById(long id);
        
        void deleteWorkOrderById(long id);
        
        WorkOrder findTopWorkOrder();
        
        void deleteTopWorkOrder();
        
	boolean isWorkOrderExist(long id);
        
	/*void updateWorkOrder(WorkOrder wo);
	
	void deleteWorkOrderById(long id);

	boolean isWorkOrderExist(WorkOrder wo);
	*/
}
