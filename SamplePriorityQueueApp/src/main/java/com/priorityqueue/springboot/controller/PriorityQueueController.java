package com.priorityqueue.springboot.controller;

import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.priorityqueue.springboot.model.WorkOrder;
import com.priorityqueue.springboot.util.AverageWaitTime;
import com.priorityqueue.springboot.util.CustomErrorType;
import com.priorityqueue.springboot.util.PositionOfWorkOrder;
import com.priorityqueue.springboot.service.PriorityQueueService;

@RestController
@RequestMapping("/api")
public class PriorityQueueController {

	public static final Logger logger = LoggerFactory.getLogger(PriorityQueueController.class);

	@Autowired
	PriorityQueueService pqService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All WorkOrders---------------------------------------------

	@RequestMapping(value = "/workOrder/", method = RequestMethod.GET)
	public ResponseEntity<List<WorkOrder>> listAllWorkOrders() {
		List<WorkOrder> workOrders = pqService.findAllWorkOrders();
		if (workOrders==null) {
                    logger.error("There are no workorders");
                    return new ResponseEntity(new CustomErrorType("There are no workorders!"),HttpStatus.NO_CONTENT);
			//return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<WorkOrder>>(pqService.calculateRankOfWorkOrder(workOrders), HttpStatus.OK);
	}

	

	// -------------------Add workorder-------------------------------------------

	@RequestMapping(value = "/enqueue/", method = RequestMethod.POST)
	public ResponseEntity<?> addWorkOrder(@RequestBody WorkOrder wo, UriComponentsBuilder ucBuilder) {
		logger.info("Adding WorkOrder : {}", wo);
                
                if(wo.getId()<=0 || wo.getId()>Long.MAX_VALUE) {
                    logger.error("{} is not in the range. Please provide valid ID between 0 and 9223372036854775807.", wo.getId());
			return new ResponseEntity(new CustomErrorType(wo.getId() +" is not in the range. Please provide valid ID between 0 and 9223372036854775807."),HttpStatus.BAD_REQUEST);
                }
                
		if (pqService.isWorkOrderExist(wo.getId())) {
			logger.error("Unable to add workorder. A WorkOrder with ID {} already exist", wo.getId());
			return new ResponseEntity(new CustomErrorType("Unable to add workorder. A WorkOrder with ID " + 
			wo.getId() + " already exist."),HttpStatus.CONFLICT);
		}
                String idClass = pqService.getPriorityClassOfID(wo.getId());
                wo.setIdClass(idClass);
		pqService.addWorkOrder(wo);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/enqueue/{index}").buildAndExpand(wo.getIndex()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
        
        // ------------------- Delete a specific WorkOrder -----------------------------------------

	@RequestMapping(value = "/workOrder/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteWorkOrderById(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting WorkOrder with id {}", id);

		WorkOrder wo = pqService.findById(id);
		if (wo == null) {
			logger.error("Unable to delete. WorkOrder with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. WorkOrder with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		pqService.deleteWorkOrderById(id);
		return new ResponseEntity<WorkOrder>(HttpStatus.NO_CONTENT);
	}
        
        // ------------------- Delete the top WorkOrder-----------------------------------------

	@RequestMapping(value = "/dequeue/", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTopWorkOrder() {
		logger.info("Fetching & Deleting Top WorkOrder");

		WorkOrder wo = pqService.findTopWorkOrder();
		if(wo==null) {
			logger.error("Unable to remove top WorkOrder since there are no WorkOrders.");
			return new ResponseEntity(new CustomErrorType("Unable to remove top WorkOrder since there are no WorkOrders."),
					HttpStatus.NOT_FOUND);
		}
		pqService.deleteTopWorkOrder();
		return new ResponseEntity<WorkOrder>(HttpStatus.NO_CONTENT);
	}
	
        // ------------------- Average Wait Time Of WorkOrders-----------------------------------------

	@RequestMapping(value = "/avgWaitTime/", method = RequestMethod.GET)
	public ResponseEntity<?> getAvgWaitTime() {
		logger.info("Fetching Average Wait Time Of WorkOrders");

		List<WorkOrder> wo = pqService.findAllWorkOrders();
		if (wo == null) {
			logger.error("Unable to get Average Wait Time of WorkOrders since there are no WorkOrders.");
			return new ResponseEntity(new CustomErrorType("Unable to get Average Wait Time of WorkOrders since there are no WorkOrders."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AverageWaitTime>(pqService.getAvgWaitTime(new Date()),HttpStatus.OK);
	}
        
        // ------------------- Get Position Of a WorkOrder-----------------------------------------

	@RequestMapping(value = "/pos/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPosOfId(@PathVariable("id") long id) {
		logger.info("Fetching Position of a WorkOrder");

		List<WorkOrder> wo = pqService.findAllWorkOrders();
                PositionOfWorkOrder pos = new PositionOfWorkOrder();
		if (wo == null) {
			logger.error("Unable to get the Position of work order. WorkOrder with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to get the Position of work order. WorkOrder with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}
                pos = pqService.getPosOfId(wo,id);
                if(pos.getId()==0 && pos.getPos()==0)
                {
                 return new ResponseEntity(new CustomErrorType("Unable to get the Position of work order. WorkOrder with id " + id + " not found."),HttpStatus.NOT_FOUND);   
                }
		return new ResponseEntity<PositionOfWorkOrder>(pqService.getPosOfId(wo,id),HttpStatus.OK);
	}

}