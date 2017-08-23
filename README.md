# MySimpleApplication

## Priority Queue Web Service

A webservice which allows user to view the work order queue in the order of their priority and the wait time. A work order consists of

- **Index** : If index is 5 for a particular work order means, it was the 5th work order which got added.*Note:* It is not the position of the work order.
- **Id** : Id is used to identify the requestor. Each requestor has been given an Id.
- **Date** : Time when the work order was added to queue.
- **Id Class** : This defines the class of Ids. Ex: Normal, Priority, VIP, Management Override.
- **Wait Time:** This is the amount of time a work order has been waiting in the queue.
- **Rank:** Based on the class of Id and its&#39;s wait time, we get the rank of each work order in the queue and based on the rank, queue is sorted.

Using the HTTP methods (GET, POST, DELETE), one can operate on the work orders list. Below are the endpoints implemented:

- Enqueue – To add a work order.
- Dequeue – Delete the top work order.
- Delete work order by id – Allows to delete a specific work order based on Id
- List the work orders – Lists the work orders in the order of their priority.
- Get position of id – Gets the position of the work order by using id.
- Average Wait Time – Calculates average mean time of Ids in the queue.

### Pre-requisites:

- JDK 1.8 or above
- NetBeans/Eclipse/STS/IntelliJ etc
- Maven 3 +/Gradle
- Postman(Chrome extension)/Curl

### Build:

Source code is built with maven 3.2.3. Web server used to host the application is Tomcat server and the application is available at port 8080.

### Run:

To run the application, get the jar from target folder. Command to run jar is as follows:

java -jar SamplePriorityQueueApp-1.0.0.jar

### RESTful URLs:

Once the application is up and running, you can browse the below mentioned URLs to see the functionality of it.

- **Get the list of work orders:**

GET [http://localhost:8080/SamplePriorityQueue/api/workOrder/](http://localhost:8080/SamplePriorityQueue/api/workOrder/)

**Curl command:** curl -v -H &quot;Content-Type: application/json&quot; GET [http://localhost:8080/SamplePriorityQueue/api/workOrder/](http://localhost:8080/SamplePriorityQueue/api/workOrder/)

- **Add a work order:**

POST [http://localhost:8080/SamplePriorityQueue/api/enqueue/](http://localhost:8080/SamplePriorityQueue/api/enqueue/)

**Curl command:** curl -v -H &quot;Content-Type: application/json&quot; -X POST -d &#39;{&quot;id&quot;:25}&#39; http://localhost:8080/SamplePriorityQueue/api/enqueue/

- **Delete work order by id:**

DELETE [http://localhost:8080/SamplePriorityQueue/api/workOrder/25](http://localhost:8080/SamplePriorityQueue/api/workOrder/25)

  **Curl command:** curl -X DELETE [http://localhost:8080/SamplePriorityQueue/api/workOrder/25](http://localhost:8080/SamplePriorityQueue/api/workOrder/25)

- **Dequeue:**

DELETE [http://localhost:8080/SamplePriorityQueue/api/dequeue/](http://localhost:8080/SamplePriorityQueue/api/dequeue/)

**Curl command:** curl -XDELETE [http://localhost:8080/SamplePriorityQueue/api/dequeue/](http://localhost:8080/SamplePriorityQueue/api/dequeue/)

- **Get position of work order by Id:**

GET [http://localhost:8080/SamplePriorityQueue/api/pos/25](http://localhost:8080/SamplePriorityQueue/api/pos/25)

**Curl Command:** curl -v -H &quot;Content-Type: application/json&quot; GET [http://localhost:8080/SamplePriorityQueue/api/pos/](http://localhost:8080/SamplePriorityQueue/api/pos/)25

- **Get average wait of Ids:**

GET [http://localhost:8080/SamplePriorityQueue/api/avgWaitTime](http://localhost:8080/SamplePriorityQueue/api/avgWaitTime)

**Curl Command:** curl -v -H &quot;Content-Type: application/json&quot; GET http://localhost:8080/SamplePriorityQueue/api/avgWaitTime

### Contributors:

Arpitha Bandri
