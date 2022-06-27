package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;

	public Department() {
	}

	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n{\n");
		sb.append(" \"id\" : ");
		sb.append(id + ",");
		sb.append("\n \"name\": ");
		sb.append(name + ",");
		sb.append("\n}");

		return sb.toString();

	}

	public String toStringFormated() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n\t{\n");
		sb.append("\t \"id\" : ");
		sb.append(id + ",");
		sb.append("\n\t \"name\": ");
		sb.append(name);
		sb.append("\n\t}");

		return sb.toString();

	}

}
