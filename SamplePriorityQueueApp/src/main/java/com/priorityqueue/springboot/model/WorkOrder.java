package com.priorityqueue.springboot.model;
	
import java.util.Date;
import java.util.Collections;

public class WorkOrder implements Comparable<WorkOrder>{
        private long index;
	private long id;
        private Date date;
        private String idClass;
        private float rank;
        private long waitTimeInSec;
        //public WorkOrder next;
	
	public WorkOrder(){
		index=0;
	}
	
	public WorkOrder(long index, long id, Date date, String idClass, float rank, long waitTimeInSec){
		this.index = index;
                this.id = id;
                this.date = date;
                this.idClass = idClass;
                this.rank = rank;
                this.waitTimeInSec = waitTimeInSec;	
}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
        public long getIndex() {
            return index;
        }

        public void setIndex(long index) {
            this.index = index;
        }
        
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public float getRank() {
            return rank;
        }

        public void setRank(float rank) {
            this.rank = rank;
        }

        public String getIdClass() {
            return idClass;
        }

        public void setIdClass(String idClass) {
            this.idClass = idClass;
        }

        public long getWaitTimeInSec() {
            return waitTimeInSec;
        }

        public void setWaitTimeInSec(long waitTimeInSec) {
            this.waitTimeInSec = waitTimeInSec;
        }
        
        @Override
	public int compareTo(WorkOrder wo) {
		return (int)(wo.getRank() - this.getRank());
	}
        
	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "index="+ index + "]";
	}*/


}
