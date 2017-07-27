package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.UserDao;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.UserService;


@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	/*@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		//return userDao.findByEmailAndPassword(email, password);
		return userDao.login(email, password);
		//return userDao.login1(email,password);
	}

	@Override
	public User findUserByTelpehone(String telephone) {
		return userDao.findByTelephone(telephone);

	}

	@Override
	public void updatePasswordByTelephone(String telephone, String password) {
		userDao.updatePasswordByTelephone( telephone,  password);
	}

}
