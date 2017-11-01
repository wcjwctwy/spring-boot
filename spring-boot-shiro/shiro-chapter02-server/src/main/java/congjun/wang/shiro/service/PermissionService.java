package congjun.wang.shiro.service;

import congjun.wang.shiro.pojo.TPermission;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    Set<String> getPermissionNamesByUserId(Integer id);

}
