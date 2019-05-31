package com.ciyaz.mempass.model.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long accountId;
	private String itemName;
	private String username;
	private String password;
	private String description;
	private String note;
	private Date createTime;
	private Date lastModifiedTime;
	private Integer availableStatus;
}
