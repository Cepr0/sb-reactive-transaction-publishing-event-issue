package io.github.cepr0.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "models")
@TypeAlias("model")
public class Model extends BaseEntity {
	private String name;
}
