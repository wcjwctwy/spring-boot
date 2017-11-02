package congjun.wang.shiro.service.impl;

import congjun.wang.shiro.dao.RoleDao;
import congjun.wang.shiro.pojo.TRole;
import congjun.wang.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<String> getRoleNamesByUserId(Integer id) {
        Set<String> names = new HashSet<>();
        List<TRole> roles = roleDao.getRolesByUserId(id);
        if(roles!=null&&roles.size()>0) {
            roles.forEach(r -> names.add(r.getName()));
        }
        return names;
    }
}
