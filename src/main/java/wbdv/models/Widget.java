package wbdv.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // an
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
// single table strategy: any field any base any derived class have, will 
// be collapsed into this one table.

// note in the db, we do not have Exam table. This is because we use single table
// strategy, the denormalized version. The exam Id points to the parent widget,
// the base one.
public class Widget {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String text;
	@ManyToOne
	@JsonIgnore // if without jsonignore, topic will come back as an embedded object when retrieving widget.
	// when you render widget, we don't need topic. We already know who your parent is.
	private Topic topic;
	private int size;
//	private String dtype;
	private String name;
	private String widgetType;
	private String src;
	private String items;
	private int listType;
	private int position;
	
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getListType() {
		return listType;
	}
	public void setListType(int listType) {
		this.listType = listType;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
//	public String getDtype() {
//		return dtype;
//	}
//	public void setDtype(String dtype) {
//		this.dtype = dtype;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	
}
