package com.hctxsys.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/test")
public class Test {

	@PersistenceContext
	private EntityManager entityManager;
	@RequestMapping(value = "/agreement", method = RequestMethod.GET)
	@ResponseBody
	public List<?> getAgreement(Model model) {
		String sql = "select a.id,a.type,b.uid from ysk_message a, ysk_message_read b where a.uid=b.uid and a.id=?1";
		Query query = entityManager.createNativeQuery(sql);
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		query.setParameter(1, 4);
		return query.getResultList();
	}
}
