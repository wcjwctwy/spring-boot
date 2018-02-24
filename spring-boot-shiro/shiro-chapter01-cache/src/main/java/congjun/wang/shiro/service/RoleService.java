package congjun.wang.shiro.service;

import congjun.wang.shiro.pojo.TRole;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<String> getRoleNamesByUserId(Integer id);
}
