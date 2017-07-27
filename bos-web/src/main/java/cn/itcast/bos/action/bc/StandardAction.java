package cn.itcast.bos.action.bc;
import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.impl.FacadeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import javax.servlet.jsp.tagext.PageData;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class StandardAction extends BaseAction<Standard> {
    @Autowired
    private FacadeService facadeService;

    @Action(value = "standardAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/standard.jsp") })
    public String save() {
        try {
            User existUser = (User) getSessionAttribute("existUser");
            model.setOperator(existUser.getEmail());
            model.setOperatorcompany(existUser.getStation());
            model.setOperationtime(new Time(System.currentTimeMillis()));
            if (model.getDeltag() == null) {
                model.setDeltag(1);
            }
            facadeService.getStandardService().save(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "save";
    }
    @Action(value = "standardAction_pageQuery", results = { @Result(name = "pageQuery", type="fastJson" , params = {"root","obj"}) })
    public String pageQuery() {
        /*Map<String, Object> map = new HashMap<>();
        PageRequest pageRequest = new PageRequest(page - 1, rows);
        Page<Standard> pageData=facadeService.getStandardService().pageQuery(pageRequest);
        map.put("total", pageData.getTotalElements());
        map.put("rows", pageData.getContent());
        push(map);*/
        //System.out.println("---action执行了----");
        Page<Standard> pageData = facadeService.getStandardService().pageQuery(getPageRequest());
        setPageData(pageData);
        return "pageQuery";
    }

    @Action(value = "standardAction_ajaxList", results = { @Result(name = "ajaxList", type="fastJson" , params = {"includeProperties","name"}) })
    public String ajaxList() {
        List<Standard> standards = facadeService.getStandardService().findAll();
        push(standards);
        return "ajaxList";
    }
    @Action(value = "standardAction_delBatch", results = { @Result(name = "delBatch", type="json") })
    public String delBatch() {
        try {
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                facadeService.getStandardService().delBatch(idArr);
                push(true);
            } else {
                push(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "delBatch";
    }
    @Action(value = "standardAction_recoverBatch", results = { @Result(name = "recoverBatch", type="json") })
    public String recoverBatch() {
        try {
            String ids = getParameter("ids");
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                facadeService.getStandardService().recoverBatch(idArr);
                push(true);
            } else {
                push(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            push(false);
        }
        return "recoverBatch";
    }
}
