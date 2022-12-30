package com.waiyanhtet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String loginId;

	private String password;

	@OneToMany(mappedBy = "member", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Contact> contacts;

	@Enumerated(EnumType.STRING)
	private Role role;

	enum Role {
		Admin, Member
	}

	public Member() {
		this.contacts = new ArrayList<>();
	}

	public Member(String name, String loginId, String password) {
		this();
		this.name = name;
		this.loginId = loginId;
		this.password = password;
		this.role = Role.Member;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void addContacts(Contact contact) {
		contact.setMember(this);
		this.contacts.add(contact);
	}
}
