package jdf.jest.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

@Entity
public class Messages extends JestEntity {
	private String subject;
	private List<Message> messages = newArrayList();

	Messages() {}

	public Messages(String subject) {
		this.subject = subject;
	}

	public void addMessage(Message message) {
		messages.add(message);
		message.setParent(this);
	}

	@OneToMany(
			mappedBy = "parent",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@OrderBy("createTime")
	public List<Message> getMessages() {
		return messages;
	}

	@NotNull
	public String getSubject() {
		return subject;
	}

	@SuppressWarnings("unused")
	private void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
