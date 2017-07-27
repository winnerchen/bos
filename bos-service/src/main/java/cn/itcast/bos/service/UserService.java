package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.user.User;

public interface UserService {
	public void save(User user);

	public void delete(User user);

	public User findUserById(Integer id);

	public List<User> findAll();

	// 业务 登录
	/*public User findUserByUsernameAndPassword(String username, String password);*/
	public User login(String email, String password);


	User findUserByTelpehone(String telephone);

	void updatePasswordByTelephone(String telephone, String password);
}
