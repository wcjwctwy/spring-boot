package congjun.wang.shiro.service.impl;

import congjun.wang.shiro.dao.PermissionDao;
import congjun.wang.shiro.pojo.TPermission;
import congjun.wang.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Set<String> getPermissionNamesByUserId(Integer id) {
        Set<String> names = new HashSet<>();
        List<TPermission> permissions = permissionDao.getPermissionsByUserId(id);
        if(permissions!=null&&permissions.size()>0) {
            permissions.forEach(p -> names.add(p.getName()));
        }
        return names;
    }
}
