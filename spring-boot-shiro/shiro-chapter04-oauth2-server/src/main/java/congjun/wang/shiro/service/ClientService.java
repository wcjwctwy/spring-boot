package congjun.wang.shiro.service;

import congjun.wang.shiro.pojo.TClient;

import java.util.List;

public interface ClientService {
    public TClient createClient(TClient client);// 创建客户端
    public TClient updateClient(TClient client);// 更新客户端
    public void deleteClient(Long clientId);// 删除客户端  
    TClient findOne(Long clientId);// 根据id查找客户端
    List<TClient> findAll();// 查找所有
    TClient findByClientId(String clientId);// 根据客户端id查找客户端
    TClient findByClientSecret(String clientSecret);//根据客户端安全KEY查找客户端
}  