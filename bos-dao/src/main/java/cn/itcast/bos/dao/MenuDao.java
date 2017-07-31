package cn.itcast.bos.dao;

import cn.itcast.bos.domain.auth.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/7/30.
 */
public interface MenuDao extends JpaRepository<Menu,String>, JpaSpecificationExecutor<Menu> {
    @Query("from Menu where generatemenu = 1 order by zindex desc")
    List<Menu> ajaxListHasSonMenus();

    @Query("from Menu order by zindex desc")
    List<Menu> ajaxList();
}
