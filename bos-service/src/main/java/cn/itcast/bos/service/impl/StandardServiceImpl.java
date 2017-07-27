package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hasee on 2017/7/17.
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardDao standardDao;

    @Override
    public void save(Standard model) {
        standardDao.save(model);
    }

    @Override
    public Page<Standard> pageQuery(PageRequest pageRequest) {
        return standardDao.findAll(pageRequest);
    }

    @Override
    public void delBatch(String[] idArr) {
        for (String s : idArr) {
            standardDao.delBatch(Integer.parseInt(s));
        }
    }

    @Override
    public List<Standard> findAll() {
        return standardDao.findAllInUse();
    }

    @Override
    public void recoverBatch(String[] idArr) {
        for (String s : idArr) {
            standardDao.recoverBatch(Integer.parseInt(s));
        }
    }
}
