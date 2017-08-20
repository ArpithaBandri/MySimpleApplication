package com.priorityqueue.springboot.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.priorityqueue.springboot.model.WorkOrder;
import java.util.Date;



@Service("priorityQueueService")
public class PriorityQueueImpl implements PriorityQueueService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<WorkOrder> workOrders;
	
	static{
		workOrders= populateDummyWorkOrders();
	}
        
        @Override
	public List<WorkOrder> findAllWorkOrders() {
		return workOrders;
	}
        
	@Override
	public void addWorkOrder(WorkOrder wo) {
		wo.setIndex(counter.incrementAndGet());
                wo.setDate(new Date());
		workOrders.add(wo);
	}
        
        @Override
        public WorkOrder findById(long id) {
		for(WorkOrder wo : workOrders){
			if(wo.getId() == id){
				return wo;
			}
		}
		return null;
	}
        
        @Override
        public void deleteWorkOrderById(long id) {
		
		for (Iterator<WorkOrder> iterator = workOrders.iterator(); iterator.hasNext(); ) {
		    WorkOrder wo = iterator.next();
		    if (wo.getId() == id) {
		        iterator.remove();
		    }
		}
	}
        
        @Override
        public WorkOrder findTopWorkOrder() {
            return workOrders.get(0);
        }
        
        public void deleteTopWorkOrder(){
            workOrders.remove(0);
        }
        
        public boolean isWorkOrderExist(long id)
        {
            for(WorkOrder wo : workOrders){
			if(wo.getId() == id){
				return true;
			}
		}
		return false;
        }
        
	private static List<WorkOrder> populateDummyWorkOrders(){
		List<WorkOrder> workOrders = new ArrayList<WorkOrder>();
		workOrders.add(new WorkOrder(counter.incrementAndGet(),5,new Date()));
		workOrders.add(new WorkOrder(counter.incrementAndGet(),10,new Date()));
		workOrders.add(new WorkOrder(counter.incrementAndGet(),12,new Date()));
		workOrders.add(new WorkOrder(counter.incrementAndGet(),15,new Date()));
		return workOrders;
	}

}
