package com.jarvismall.sso.service.impl;

import com.jarvismall.mapper.TbUserMapper;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbUser;
import com.jarvismall.pojo.TbUserExample;
import com.jarvismall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    TbUserMapper tbUserMapper;

    @Override
    public TaotaoResult checkUser(String param, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        switch (type) {
            case 1://用户名;
                criteria.andUsernameEqualTo(param);
                break;
            case 2://手机;
                criteria.andPhoneEqualTo(param);
                break;
            case 3://邮箱;
                criteria.andEmailEqualTo(param);
                break;
                default:
                    return TaotaoResult.build(400,"fail");
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if(tbUsers!=null && tbUsers.size()>0){
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult registerUser(TbUser tbUser) {
        //检查数据有效性;
        if(StringUtils.isBlank(tbUser.getUsername())||
                StringUtils.isBlank(tbUser.getEmail())||
                StringUtils.isBlank(tbUser.getPassword())||
                StringUtils.isBlank(tbUser.getPhone())){
            return TaotaoResult.build(400,"error parems");
        }
        TaotaoResult result = checkUser(tbUser.getUsername(), 1);
        TaotaoResult result2 = checkUser(tbUser.getPhone(), 2);
        TaotaoResult result3 = checkUser(tbUser.getEmail(), 3);

        if(!(boolean) result.getData() ||
                !(boolean) result2.getData()||
                !(boolean) result3.getData()){
            return TaotaoResult.build(400,"repeat name");
        }

        //补全;
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        String md5Psd = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Psd);

        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok();
    }
}
