/**
 * Aug 10, 2012
 */
package org.zlex.json.domain;

import java.io.Serializable;

/**
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6121501427655885029L;
	private int id;
	private String name;
	private boolean status;

	/**
	 * 
	 */
	public Person() {
		// do nothing
	}

	/**
	 * @param id
	 * @param name
	 * @param status
	 */
	public Person(int id, String name, boolean status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
