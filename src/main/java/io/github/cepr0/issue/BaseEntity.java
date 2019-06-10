package io.github.cepr0.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity {
	@Id
	private String id;

	@Version
	@JsonIgnore
	private Integer version;
}
