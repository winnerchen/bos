package cn.itcast.bos.service;

import cn.itcast.bos.domain.auth.Menu;
import cn.itcast.bos.domain.auth.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by hasee on 2017/7/31.
 */
public interface RoleService {
    void save(Role model, String menuIds, String[] functionIds);

    Page<Role> pageQuery(PageRequest pageRequest);
}
