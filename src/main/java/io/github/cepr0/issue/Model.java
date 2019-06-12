package io.github.cepr0.issue;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "models")
@TypeAlias("model")
public class Model extends BaseEntity {
	private String name;

	@Tolerate
	public Model(String name) {
		this.name = name;
	}
}
