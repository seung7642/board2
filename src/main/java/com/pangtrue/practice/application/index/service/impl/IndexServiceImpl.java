package com.pangtrue.practice.application.index.service.impl;

import com.pangtrue.practice.application.index.dao.IndexMapper;
import com.pangtrue.practice.application.index.domain.Message;
import com.pangtrue.practice.application.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: SeungHo Kyoung (daygoods@neowiz.com)
 * Date: 2020.01.09
 * Time: 18:00
 */
@Service
public class IndexServiceImpl implements IndexService {

	private final IndexMapper indexMapper;

	@Autowired
	public IndexServiceImpl(IndexMapper indexMapper) {
		this.indexMapper = indexMapper;
	}

	@Override
	public Message getMessage() {
		return indexMapper.get();
	}

}
