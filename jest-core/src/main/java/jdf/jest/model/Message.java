package jdf.jest.model;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Message extends JestEntity {
	private Date createTime = new Date();

	private Set<MessageBody> body = newHashSet();

	private User author;
	private Messages parent;

	Message() {}

	public Message(User author, String body, Messages parent) {
		this.author = author;
		setBodyPublic(body);
		this.parent = parent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public User getAuthor() {
		return author;
	}

	@SuppressWarnings("unused")
	@OneToMany(
			mappedBy = "parent",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true)
	private Set<MessageBody> getBody() {
		return body;
	}

	@Transient
	public MessageBody getBodyPublic() {
		if (body.isEmpty()) {
			return null;
		}
		return getOnlyElement(body);
	}

	@NotNull
	public Date getCreateTime() {
		return createTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Messages getParent() {
		return parent;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@SuppressWarnings("unused")
	private void setBody(Set<MessageBody> body) {
		this.body = body;
	}

	public void setBodyPublic(@Nullable String clob) {
		if (clob == null) {
			body.clear();
		} else {
			if (body.isEmpty()) {
				body.add(new MessageBody());
			} else {
				getOnlyElement(body).setValue(clob);
			}
		}
	}

	@SuppressWarnings("unused")
	private void setCreateTime(Date createTimestamp) {
		this.createTime = createTimestamp;
	}

	public void setParent(Messages parent) {
		this.parent = parent;
	}

}
