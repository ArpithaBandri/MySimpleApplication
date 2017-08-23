package com.priorityqueue.springboot;

import com.priorityqueue.springboot.model.WorkOrder;
import com.priorityqueue.springboot.util.AverageWaitTime;
import com.priorityqueue.springboot.util.PositionOfWorkOrder;
import java.net.URI;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class SamplePriorityQueueTestClient
{
    public static final String REST_SERVICE_URI = "http://localhost:8080/SamplePriorityQueue/api";
    
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllWorkOrders(){
        System.out.println("Testing listAllWorkOrders API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> workOrdersMap = restTemplate.getForObject(REST_SERVICE_URI+"/workOrder/", List.class);
         
        if(workOrdersMap!=null){
            for(LinkedHashMap<String, Object> map : workOrdersMap){
                System.out.println("Work Order : index="+map.get("index")+",id="+map.get("id")+", Date="+map.get("date")+", Id Class="+map.get("idClass")+", Rank="+map.get("rank")+", Wait Time = "+map.get("waitTimeInSec"));
            }
        }else{
            System.out.println("No work order exist----------");
        }
    }
    
    /*Post*/
    private static void addWorkOrder() {
        System.out.println("Testing addWorkOrder API----------");
        RestTemplate restTemplate = new RestTemplate();
        WorkOrder wo;
        wo = new WorkOrder(1,20,new Date(),"VIP", (float)Math.max(4.0f, 2*15*(Math.log10(15))),15);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/enqueue/", wo, WorkOrder.class);
        System.out.println("Location : "+uri);
    }
    
    /* DELETE */
    private static void deleteWorkOrderById() {
        System.out.println("Testing deleteWorkOrderById API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/workOrder/20");
    }
    
    /* DELETE */
    private static void deleteTopWorkOrder(){
        System.out.println("Testing deleteTopWorkOrder API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/dequeue/");
    }
    
    /* GET */
    private static void getPosOfId(){
        System.out.println("Testing getPosOfId API----------");
        RestTemplate restTemplate = new RestTemplate();
        PositionOfWorkOrder pos = restTemplate.getForObject(REST_SERVICE_URI+"/pos/20", PositionOfWorkOrder.class);
        System.out.println("Position: "+pos.getPos());
        System.out.println("Work Order Id: "+pos.getId());
    }
    
    /* GET */
    private static void getAvgWaitTime(){
        System.out.println("Testing getAvgWaitTime API----------");
        AverageWaitTime avgWT = new AverageWaitTime();
        RestTemplate restTemplate = new RestTemplate();
        avgWT = restTemplate.getForObject(REST_SERVICE_URI+"/avgWaitTime/", AverageWaitTime.class);
        System.out.println("Average Wait Time: "+avgWT.getAvgWaitTime());
        System.out.println("No Of Work Orders: "+avgWT.getNoOfWorkOrders());
    }
    
    public static void main(String args[]){
        listAllWorkOrders();
        addWorkOrder();
        listAllWorkOrders();
        deleteWorkOrderById();
        listAllWorkOrders();
        addWorkOrder();
        deleteTopWorkOrder();
        listAllWorkOrders();
        addWorkOrder();
        getPosOfId();
        listAllWorkOrders();
        getAvgWaitTime();
    }
}
