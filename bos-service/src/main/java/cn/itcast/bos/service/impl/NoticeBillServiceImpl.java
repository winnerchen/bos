package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.DecidedZoneDao;
import cn.itcast.bos.dao.NoticeBillDao;
import cn.itcast.bos.dao.RegionDao;
import cn.itcast.bos.dao.WorkBillDao;
import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.domain.courier.NoticeBill;
import cn.itcast.bos.domain.courier.WorkBill;
import cn.itcast.bos.service.NoticeBillService;
import cn.itcast.bos.service.base.BaseInterface;
import cn.itcast.crm.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.Set;

/**
 * Created by hasee on 2017/7/27.
 */
@Service
@Transactional
public class NoticeBillServiceImpl implements NoticeBillService {
    @Autowired
    private DecidedZoneDao decidedZoneDao;
    @Autowired
    private WorkBillDao workBillDao;
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private NoticeBillDao noticeBillDao;


    @Override
    public Customer findCustomerByTelephone(String telephone) {
        // CRM
        String url = BaseInterface.CRM_BASE_URL + "/findcustomerbytelephone/" + telephone;
        Customer c = WebClient.create(url).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        return c;

    }

    @Override
    public void save(final NoticeBill model, String province, String city, String district) {
        // 业务通知单录入
        noticeBillDao.saveAndFlush(model);// saveAndFlush model瞬时 --->持久态 model OID
        System.out.println(model.getId() + "=================" + String.valueOf(model.getCustomerId()));
        // 1 : 客户是否是一个新客户 crm录入 返回 customerId
        if (!("null".equalsIgnoreCase(String.valueOf(model.getCustomerId())))) {
            // 老客户 更新crm地址
            String url = BaseInterface.CRM_BASE_URL + "/updateadressbyid/" + model.getCustomerId() + "/" + model.getPickaddress();
            WebClient.create(url).put(null);
        } else {
            // 新客户 插入 返回Cusotmer id
            String url = BaseInterface.CRM_BASE_URL + "/save";
            Customer c = new Customer();
            c.setName(model.getCustomerName());
            c.setAddress(model.getPickaddress());
            c.setDecidedzoneId(null);
            c.setStation("传智播客");
            c.setTelephone(model.getTelephone());
            Response post = WebClient.create(url).accept(MediaType.APPLICATION_JSON).post(c);
            // 响应体 获取实体模型
            Customer entity = post.readEntity(Customer.class);// 主键从crm系统获取
            model.setCustomerId(entity.getId());// noticebill --->customerId
        }
        // 2: 取派员 自动分单 成功 分单类型 自动! 如果自动分单失败 staff null 分单类型人工
        // 地址库完全匹配
        String url = BaseInterface.CRM_BASE_URL + "/findcustomerbyaddress/" + model.getPickaddress();
        Customer c = WebClient.create(url).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (c != null) {
            // 根据地址查询客户信息
            String decidedzoneId = c.getDecidedzoneId();// 断点...
            System.out.println(decidedzoneId + "----------------------");
            if (StringUtils.isNotBlank(decidedzoneId)) {
                // 客户已经被关联
                DecidedZone decidedZone = decidedZoneDao.findOne(decidedzoneId);
                final Staff staff = decidedZone.getStaff();
                model.setStaff(staff);
                model.setOrdertype("自动");
                // 自动分单已经成功 生成一张工单
                WorkBill bill = new WorkBill();
                bill.setAttachbilltimes(0);
                bill.setBuildtime(new Date(System.currentTimeMillis()));
                bill.setNoticeBill(model);
                bill.setType("新");
                bill.setStaff(staff);
                bill.setRemark(model.getRemark());
                bill.setPickstate("新单");
                workBillDao.save(bill);
                // 发送短信mq
                jmsTemplate.send("bos_staff", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        MapMessage mapMessage = session.createMapMessage();
                        mapMessage.setString("telephone", staff.getTelephone());
                        mapMessage.setString("msg", "你好,客户名称=" + model.getCustomerName() + "---送达地址=" + model.getArrivecity());
                        return mapMessage;
                    }
                });
                return;
            }
        }
        // 自动分单 规则2 管理分区匹配法
        // 省市区 查询Region---subareas----> 关键字模糊匹配 --->Subarea --->Decidedzone--->Staff
        Region region = regionDao.findRegionByProvinceAndCityAndDistrict(province, city, district);
        Set<Subarea> subareas = region.getSubareas();
        if (subareas != null && subareas.size() != 0) {

            for (Subarea sub : subareas) {
                if (model.getPickaddress().contains(sub.getAddresskey())) {
                    DecidedZone zone = sub.getDecidedZone();
                    if (zone != null) {
                        Staff staff = zone.getStaff();
                        model.setStaff(staff);
                        model.setOrdertype("自动");
                        // 自动分单已经成功 生成一张工单
                        WorkBill bill = new WorkBill();
                        bill.setAttachbilltimes(0);
                        bill.setBuildtime(new Date(System.currentTimeMillis()));
                        bill.setNoticeBill(model);
                        bill.setType("新");
                        bill.setStaff(staff);
                        bill.setRemark(model.getRemark());
                        bill.setPickstate("新单");
                        workBillDao.save(bill);
                        return;
                    }
                }
            }

        }
        // 自动分单失败
        model.setOrdertype("人工");

    }

}
