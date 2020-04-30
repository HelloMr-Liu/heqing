package com.huijianzhu.heqing.test;

import com.huijianzhu.heqing.HeQingMain;
import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.mapper.TdUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ================================================================
 * 说明：当前类说说明
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  10:27            创建
 * =================================================================
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes= HeQingMain.class)
public class UserMapperTest {

    @Autowired
    private TdUserMapper tdUserMapper;


    @Test
    public void testList(){
        List<TdUser> tdUsers = tdUserMapper.selectUserAll();
        tdUsers.forEach(
            e->{
                System.out.println(e.toString());
            }
        );

    }
}
