package cn.itcast.bos.action.auth;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.auth.Menu;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.service.impl.FacadeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by hasee on 2017/7/30.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class RoleAction extends BaseAction<Role> {
    @Autowired
    private FacadeService facadeService;

    @Action(value = "roleAction_save", results = {@Result(name = "save", location =
            "/WEB-INF/pages/admin/role.jsp")})
    public String save() {
        String menuIds = getRequest().getParameter("menuIds");
        String[] functionIds = getRequest().getParameterValues("functionIds");

        facadeService.getRoleService().save(model, menuIds, functionIds);
        return "save";
    }


    @Action(value = "roleAction_pageQuery")
    public String pageQuery() {
        super.setPage(Integer.parseInt(getParameter("page")));
        Page<Role> pageData = facadeService.getRoleService().pageQuery(getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }
/*
    @Action(value = "menuAction_ajaxListHasSonMenus", results = {@Result(name =
            "ajaxListHasSonMenus" , type = "fastJson")})
    public String ajaxListHasSonMenus() {
        List<Menu> menus = facadeService.getMenuService().ajaxListHasSonMenus();
        push(menus);
        return "ajaxListHasSonMenus";

    }
    @Action(value = "menuAction_ajaxList", results = {@Result(name =
            "ajaxList" , type = "fastJson", params = {"includeProperties","name,id,pId"})})
    public String ajaxList() {
        List<Menu> menus = facadeService.getMenuService().ajaxList();
        push(menus);
        return "ajaxList";

    }*/

}
