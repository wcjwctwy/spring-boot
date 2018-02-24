package congjun.wang.shiro.service.impl;

import congjun.wang.shiro.dao.ClientDao;
import congjun.wang.shiro.pojo.TClient;
import congjun.wang.shiro.service.ClientService;
import congjun.wang.sql.SqlCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-17
 * <p>Version: 1.0
 */
@Transactional
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;

    @Override
    public TClient createClient(TClient client) {

        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        SqlCondition sqlCondition = new SqlCondition(client);
        return clientDao.createClient(sqlCondition);
    }

    @Override
    public TClient updateClient(TClient client) {
        SqlCondition sqlCondition = new SqlCondition(client);
        sqlCondition.addAnd("id",client.getId().toString(),true);
        return clientDao.updateClient(sqlCondition);
    }

    @Override
    public void deleteClient(Long clientId) {
        TClient client = new TClient();
        client.setId(clientId);
        SqlCondition sqlCondition = new SqlCondition(client);
        clientDao.deleteClient(sqlCondition);
    }

    @Override
    public TClient findOne(Long clientId) {
        TClient client = new TClient();
        client.setId(clientId);
        SqlCondition sqlCondition = new SqlCondition(client);
        return clientDao.getClients(sqlCondition).get(0);
    }

    @Override
    public List<TClient> findAll() {
        SqlCondition sqlCondition = new SqlCondition("t_client");
        return clientDao.getClients(sqlCondition);
    }

    @Override
    public TClient findByClientId(String clientId) {
        TClient client = new TClient();
        client.setClientId(clientId);
        SqlCondition sqlCondition = new SqlCondition(client);
        return clientDao.getClients(sqlCondition).get(0);
    }

    @Override
    public TClient findByClientSecret(String clientSecret) {
        TClient client = new TClient();
        client.setClientSecret(clientSecret);
        SqlCondition sqlCondition = new SqlCondition(client);
        return clientDao.getClients(sqlCondition).get(0);
    }
}
