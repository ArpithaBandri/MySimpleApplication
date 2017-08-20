package com.priorityqueue.springboot.model;
	
import java.util.Date;

public class WorkOrder {
        private long index;
	private long id;
        private Date date;
	
	public WorkOrder(){
		index=0;
	}
	
	public WorkOrder(long index, long id, Date date){
		this.index = index;
                this.id = id;
                this.date = date;
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

	@Override
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
	}


}
