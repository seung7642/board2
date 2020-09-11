package com.neowiz.practice.application.index.dao;

import com.neowiz.practice.application.index.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * User: SeungHo Kyoung (daygoods@neowiz.com)
 * Date: 2020.01.09
 * Time: 18:18
 */
@Repository
@Mapper
public interface IndexMapper {

	/**
	 * 메세지를 가져온다.
	 *
	 * @return Message
	 */
	Message get(); //  /mappers/sql-index.xml 파일에서 해당 클래스와 연결시켜놓았기 때문에 <select id="get" ...> 을 사용할 수 있는 것.

}
