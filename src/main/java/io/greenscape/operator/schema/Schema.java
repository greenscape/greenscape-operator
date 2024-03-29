package io.greenscape.operator.schema;

import java.io.Serializable;
import java.util.Objects;

public class Schema implements Serializable {

	private static final long serialVersionUID = 343321980105481010L;
	private final String name;
	private final String characterSet;

	public Schema(String name, String characterSet) {
		this.name = name;
		this.characterSet = characterSet;
	}

	public String getName() {
		return name;
	}

	public String getCharacterSet() {
		return characterSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Schema schema = (Schema) o;
		return Objects.equals(name, schema.name) && Objects.equals(characterSet, schema.characterSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, characterSet);
	}
}