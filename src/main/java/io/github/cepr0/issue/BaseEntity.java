package io.github.cepr0.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@Data
public abstract class BaseEntity {
	@Id
	private String id;

	@Version
	@JsonIgnore
	private Integer version;
}
