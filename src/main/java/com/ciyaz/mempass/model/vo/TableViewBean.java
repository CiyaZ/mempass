package com.ciyaz.mempass.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableViewBean {
	private String key;
	private String value;
	/**
	 * 后加的扩展字段，用于TableView的密码Shadow显示功能
	 */
	private String ext1;
}
