package jdf.jest.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class MessageBody extends JestEntity {

	private String value;
	private Message parent;

	MessageBody() {}

	public MessageBody(String value, Message parent) {
		this.value = value;
		this.parent = parent;
	}

	@MapsId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@NotNull
	public Message getParent() {
		return parent;
	}

	@Lob
	@Field
	public String getValue() {
		return value;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
