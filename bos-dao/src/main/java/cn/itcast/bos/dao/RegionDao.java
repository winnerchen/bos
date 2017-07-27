package cn.itcast.bos.dao;

import cn.itcast.bos.domain.bc.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/21.
 */
public interface RegionDao extends JpaRepository<Region,String>, JpaSpecificationExecutor<Region>{

    Region findByPostcode(String postcode);
    @Query("from Region where province like ?1 or city like ?1 or district like ?1")
    List<Region> findByCriterion(String params);
}
