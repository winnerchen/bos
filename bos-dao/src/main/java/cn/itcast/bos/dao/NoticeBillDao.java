package cn.itcast.bos.dao;

import cn.itcast.bos.domain.courier.NoticeBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by hasee on 2017/7/27.
 */
public interface NoticeBillDao extends JpaRepository<NoticeBill,String>, JpaSpecificationExecutor<NoticeBill> {
}
