package com.hctxsys.util;

import com.hctxsys.entity.YskUserCheckinfoEntity;
import com.hctxsys.entity.YskUserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityManagerUtils {
    @PersistenceContext
    private EntityManager em;
    public boolean checkWhere(String sql) {
        if(sql.indexOf("where")!=-1) return true;
        else return false;
    }

    /**
     * 用户认证主页数据
     * @param result
     * @return
     */
    public List<Object[]> indexTable(StringBuilder sql) {
        Query query = em.createQuery(sql.toString());
        List<Object[]> resultList = query.getResultList();
        return resultList;
    }
}
