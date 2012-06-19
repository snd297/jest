package jdf.jest.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class JestEntity {

	private Long id;

	private Integer version;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	@Version
	@Column(name = "optlock")
	public Integer getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
