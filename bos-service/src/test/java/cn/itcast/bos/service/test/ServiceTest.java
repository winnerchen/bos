package cn.itcast.bos.service.test;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.impl.FacadeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-domain.xml",
        "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml",
})
public class ServiceTest {

    @Autowired
    private FacadeService facadeService;

    @Test
    public void test01() {
       /* User user = new User();
        user.setEmail("yyyy@163.com");
        user.setPassword("1111");
        user.setTelephone("11111");
        facadeService.getUserService().save(user);*/
    }

    @Test
    public void test02() {

        User existUser = facadeService.getUserService().login("xxx@163.com", "1111");

        System.out.println(existUser);
    }

    @Test
    public void test03() {

        User existUser = facadeService.getUserService().login("xxx@163.com", "1111");

        System.out.println(existUser);
    }

}
