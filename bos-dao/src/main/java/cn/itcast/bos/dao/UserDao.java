package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.itcast.bos.domain.user.User;

public interface UserDao extends JpaRepository<User, Integer> {

	//User findByEmailAndPassword(String email, String password);

	//@Query("from User where email=?1 and password=?2")
	//public User login(String email, String password);

	/*@Query(nativeQuery=true,value="select * from t_user where email=?1 and password=?2")*/
	public User login(String email, String password);

	@Query("from User where email = :email and password = :password")
	public User login3(@Param("email") String email, @Param("password") String password);

	User findByTelephone(String telephone);

	@Modifying
	@Query("update User set password=?2 where telephone=?1")
	void updatePasswordByTelephone(String telephone, String password);
}
