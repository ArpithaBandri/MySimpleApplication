package com.priorityqueue.springboot.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.priorityqueue.springboot.model.WorkOrder;
import com.priorityqueue.springboot.util.*;
import java.util.List;
import java.util.Date;
import java.util.Collections;



@Service("priorityQueueService")
public class PriorityQueueImpl implements PriorityQueueService, Comparator<WorkOrder>{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private List<WorkOrder> workOrders = new ArrayList<WorkOrder>();
        
        private DateTimeManipulation dt = new DateTimeManipulation();
        
        private static int mgmtIdCount=0;
        
        private static long totalNoOfSec =0;
	
        public int compare(WorkOrder wo1, WorkOrder wo2) {
        return (int)(wo2.getRank() - wo1.getRank());
    }
        
        @Override
	public List<WorkOrder> findAllWorkOrders() {
		
            if(workOrders!=null)
            {
                Collections.sort(workOrders);
                return workOrders;
            }
            return null;
	}
        
	@Override
	public void addWorkOrder(WorkOrder wo) {
                
		wo.setIndex(counter.incrementAndGet());
                wo.setDate(new Date());
                if(wo.getIdClass().equalsIgnoreCase("Management_Override"))
                {
                    wo.setRank(Float.MAX_VALUE-mgmtIdCount);
                    WorkOrder mgmt = new WorkOrder(wo.getIndex(),wo.getId(),wo.getDate(),wo.getIdClass(),wo.getRank(),wo.getWaitTimeInSec());
                    //workOrders.add(mgmtIdCount, mgmt);
                    workOrders.add(mgmt);
                    mgmtIdCount++;
                }
                else{
                   workOrders.add(new WorkOrder(wo.getIndex(),wo.getId(),wo.getDate(),wo.getIdClass(),wo.getRank(),wo.getWaitTimeInSec()));         
                }
                
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
            if(workOrders!=null){
            for(WorkOrder wo : workOrders){
			if(wo.getId() == id){
				return true;
			}
		}
            }
		return false;
        }
        
        public String getPriorityClassOfID(long id){
            
            if(id%3==0&&id%5==0)
            {
                return "Management_Override";
            }
            else if(id%3==0)
            {
                return "Priority";
            }
            else if(id%5==0)
            {
                return "VIP";
            }
            else
            {
                return "Normal";
            }
        }
        
        public List<WorkOrder> calculateRankOfWorkOrder(List<WorkOrder> wOrders)
        {
            for (Iterator<WorkOrder> iterator = wOrders.iterator(); iterator.hasNext(); ) 
            {
                    long n = 0;
		    WorkOrder wo = iterator.next();
                    switch(wo.getIdClass())
                    {
                        case "Normal"   : n=dt.calculateWaitTimeInSeconds(wo.getDate());
                                          wo.setRank((float)n);
                                          wo.setWaitTimeInSec(n);
                                          break;
                                        
                        case "Priority" : n=dt.calculateWaitTimeInSeconds(wo.getDate());
                                          wo.setRank((float)Math.max(3.0f, n*(Math.log10(n))));
                                          wo.setWaitTimeInSec(n);
                                          break;
                                          
                        case "VIP"      : n=dt.calculateWaitTimeInSeconds(wo.getDate());
                                          wo.setRank((float)Math.max(4.0f, 2*n*(Math.log10(n))));
                                          wo.setWaitTimeInSec(n);
                                          break;
                                          
                        //Ids which fall under Management_Override Class, we are assigning rank when adding work order to the List                  
                        case "Management_Override" : n=dt.calculateWaitTimeInSeconds(wo.getDate());
                                                     wo.setWaitTimeInSec(n);
                                                     break;
                                                   
                        case "default" : break;
		    }
                    
		} return wOrders;
        }
        
        public AverageWaitTime getAvgWaitTime(Date curDate){
            AverageWaitTime avgWT = new AverageWaitTime();
            if(!workOrders.isEmpty())
            {
                long noOfSec;
                for(int i=0; i<workOrders.size();i++)
                {
                    noOfSec = (curDate.getTime() - workOrders.get(i).getDate().getTime())/2000;
                    totalNoOfSec += noOfSec;
                }
                 avgWT.setAvgWaitTime(totalNoOfSec/workOrders.size());
                 avgWT.setNoOfWorkOrders(workOrders.size());
                 
                return avgWT;
            }
            return null;
        }
        
        public PositionOfWorkOrder getPosOfId(List<WorkOrder> wOrders, long id)
        {
            PositionOfWorkOrder pos = new PositionOfWorkOrder();
            if(wOrders!=null)
            {
                for(int i=0; i<wOrders.size();i++)
                {
                    if(wOrders.get(i).getId()==id)
                        pos.setPos(i+1);
                        pos.setId(id);
                }
            }
            return pos;
        }
        

}
