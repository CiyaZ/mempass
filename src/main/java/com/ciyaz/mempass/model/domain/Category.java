package com.ciyaz.mempass.model.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long cateoryId;
	private String categoryName;
}
