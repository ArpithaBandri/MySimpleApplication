package com.priorityqueue.springboot.controller;

import java.util.List;

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
import com.priorityqueue.springboot.util.CustomErrorType;
import com.priorityqueue.springboot.service.PriorityQueueService;

@RestController
@RequestMapping("/api")
public class PriorityQueueController {

	public static final Logger logger = LoggerFactory.getLogger(PriorityQueueController.class);

	@Autowired
	PriorityQueueService pqService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All WorkOrders---------------------------------------------

	@RequestMapping(value = "/workOrder/", method = RequestMethod.GET)
	public ResponseEntity<List<WorkOrder>> listAllUsers() {
		List<WorkOrder> workOrders = pqService.findAllWorkOrders();
		if (workOrders.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<WorkOrder>>(workOrders, HttpStatus.OK);
	}

	

	// -------------------Add workorder-------------------------------------------

	@RequestMapping(value = "/enqueue/", method = RequestMethod.POST)
	public ResponseEntity<?> addWorkOrder(@RequestBody WorkOrder wo, UriComponentsBuilder ucBuilder) {
		logger.info("Adding WorkOrder : {}", wo);

		if (pqService.isWorkOrderExist(wo.getId())) {
			logger.error("Unable to add workorder. A WorkOrder with ID {} already exist", wo.getId());
			return new ResponseEntity(new CustomErrorType("Unable to add workorder. A WorkOrder with ID " + 
			wo.getId() + " already exist."),HttpStatus.CONFLICT);
		}
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
		if (wo == null) {
			logger.error("Unable to remove top WorkOrder since there are no WorkOrders.");
			return new ResponseEntity(new CustomErrorType("Unable to remove top WorkOrder since there are no WorkOrders."),
					HttpStatus.NOT_FOUND);
		}
		pqService.deleteTopWorkOrder();
		return new ResponseEntity<WorkOrder>(HttpStatus.NO_CONTENT);
	}
	

}