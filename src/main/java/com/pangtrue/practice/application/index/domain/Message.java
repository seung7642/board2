package com.pangtrue.practice.application.index.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * User: SeungHo Kyoung (daygoods@neowiz.com)
 * Date: 2020.01.09
 * Time: 18:19
 */
@Alias("Message") // 이 어노테이션으로 인해 /mappers/sql-index.xml 매핑 파일에서 resultType 값으로 Message 객체를 사용할 수 있는 것.
@Data
public class Message {
	private int seq;
	private String title;
	private String message;
	private Date regDate;
}
