package com.itheima.mysecurity;

import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义认证和授权类
 * @author wangxin
 * @version 1.0
 */
@Component //controller service dao
public class MyUserService implements UserDetailsService {

    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static Map<String,User> map = new HashMap<>();

    static {
        ///多条用户对象记录（从数据库查询出来的）
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(encoder.encode("admin"));

        User user2 = new User();
        user2.setUsername("zhangsan");
        user2.setPassword(encoder.encode("123"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }

    /**
     * 根据用户名加载用户数据
     *
     *  用户再页面输入的用户名：loadUserByUsername(String username)
     *  根据用户名查询数据用户信息
     *  校验密码的逻辑并不是在此类中进行的，交给springSecurity框架完成
     *  将数据库查询的密码返回给框架，跟用户输入的密码进行对比
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将用户名对应的密码 查询交给spirngSecurtiy框架即可
        //1.根据username用户名获取用户信息（模拟从数据库查询）
        System.out.println("用户页面输入了用户名：：：："+username);
        User user = map.get(username);
        //2.如果不存在 return null;
        if(user == null){
            return null;
        }
        //3.获取数据库中查询的密码
        //String dbPassword = "{noop}"+user.getPassword();
        String dbPassword = user.getPassword();

        //4.授权 授予ROLE_ADMIN权限（写死 后续从数据库中查询当前用户的权限列表 ）
        List<GrantedAuthority> list = new ArrayList<>();
        //添加一个权限ROLE_ADMIN
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        list.add(new SimpleGrantedAuthority("add"));

        //5.new User(密码);用户名：String username, String password,
       // Collection<? extends GrantedAuthority> authorities
        return new org.springframework.security.core.userdetails.User(username,dbPassword,list);
    }
}
