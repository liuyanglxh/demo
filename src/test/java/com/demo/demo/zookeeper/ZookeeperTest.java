package com.demo.demo.zookeeper;

import com.demo.demo.common.config.BootstrapConfig;
import com.demo.demo.common.config.CommonConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by liuyang on 2018/11/8
 */
public class ZookeeperTest {

    @Test
    public void test() throws Exception {
        //创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String address = BootstrapConfig.get("server.ip") + ":" + BootstrapConfig.get("zookeeper.port");
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();
        //创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, "/curator");
        mutex.acquire();
        //获得了锁, 进行业务流程
        System.out.println("Enter mutex");
        //完成业务流程, 释放锁
        mutex.release();
        //关闭客户端
        client.close();
    }

    @Test
    public void testZk() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(BootstrapConfig.get("server.ip") + ":2181", 5000, null);

        String path = "/";
        if (zooKeeper != null) {
            List<String> children = zooKeeper.getChildren(path, false);
            for (String child : children) {
                System.err.println(child);
            }
        }
    }
}
