package com.huge.ihos.bm.budgetModel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.model.BaseObject;

@Entity
@Table(name="bm_model_assist")
public class BmModelAssist  extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033042848750343600L;
	private String id;
	private AssistType assistCode;
	private BudgetModel model;
	private String item;
	private String itemName;
	
	@Id
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assistCode")
	public AssistType getAssistCode() {
		return assistCode;
	}
	public void setAssistCode(AssistType assistCode) {
		this.assistCode = assistCode;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modelId")
	public BudgetModel getModel() {
		return model;
	}
	public void setModel(BudgetModel model) {
		this.model = model;
	}
	
	@Column(name="itemId")
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	@Column
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BmModelAssist other = (BmModelAssist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BmModelAssist [id=" + id + ", item=" + item + ", itemName="
				+ itemName + "]";
	}

	
	
}
