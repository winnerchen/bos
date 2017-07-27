package cn.itcast;

import cn.itcast.bos.domain.user.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.Set;

/**
 * Created by hasee on 2017/7/22.
 */
public class fastjson {
    public static void main(String[] args) {
        // 对象 --->json 序列化
        User user = new User();
        user.setId(1);
        user.setEmail("eee@163.com");
        user.setPassword("1233");

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class);

        Set<String> excludes = filter.getExcludes();
        Set<String> includes = filter.getIncludes();

        excludes.add("id");
        excludes.add("email");

        // 找对象
        String json = JSON.toJSONString(user,filter);
        System.out.println(json);

        // json---->对象 反序列化

    }

}
