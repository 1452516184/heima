import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密测试
 * @author wangxin
 * @version 1.0
 */
public class BcrytTest {

    @Test
    public void testBcryt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //加密
        //String password = encoder.encode("123456");
        //System.out.println("加密后的秘钥" + password);
        //$2a$10$dF.n/Jr5lB/YqYbVE/IoeefqzLiyMF.8seH4yziWF.DdaWV.uL/BW
        //$2a$10$je3wNPm9A.iVN3.sBQYBI.aby8hkiWZ3Q8sVT14SORSjyKCt3wEzq

        //匹配密码是否正确（解密）
        boolean flag = encoder.matches("11116", "$2a$10$dF.n/Jr5lB/YqYbVE/IoeefqzLiyMF.8seH4yziWF.DdaWV.uL/BW");

        System.out.println(flag);
    }
}
