package com.daagng.test.db.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{
	@NotNull
	private String name;

	@OneToMany(mappedBy = "user")
	private List<Account> accountList;

	public void addAccount(Account account) {
		if (this.accountList == null)
			accountList = new LinkedList<>();
		accountList.add(account);
		account.setUser(this);
	}
}
