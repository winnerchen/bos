package cn.itcast.bos.service;

import cn.itcast.bos.domain.courier.NoticeBill;
import cn.itcast.crm.domain.Customer;

/**
 * Created by hasee on 2017/7/27.
 */
public interface NoticeBillService {
    Customer findCustomerByTelephone(String telephone);

    void save(NoticeBill model, String province, String city, String district);
}
