package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import org.apache.log4j.Logger;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;

@JSComponent(name="redis_fix")
public class RedisOps {

    private static Logger log = Logger.getLogger(RedisOps.class.getName());

    private static Connection conn = new Connection();
    private static Jedis jedis = new Jedis();

    static {
        try {
            conn.setHost("127.0.0.1");
            conn.setPort(6379);
            conn.connect();
            jedis.connect();
            log.debug("\nJedis connected: " + jedis.isConnected() + "\n\tConnected to port: " + conn.getPort() + "\n\tTo host: " + conn.getHost() + "\n");
        } catch (RuntimeException e) {
            log.debug("Could not connect to Redis Server \n\tPort: " + conn.getPort() + "\n\tHost: " + conn.getHost());
        }
    }

    public static Jedis getJedis() {return jedis;}

    @JSRunnable
    public void setHost(String host) {
        conn.setHost(host);
    }

    @JSRunnable
    public void setPort(int port) {
        conn.setPort(port);
    }

    @JSRunnable
    public void connect() {
        conn.connect();
        jedis.connect();
        log.debug("\nJedis connected: " + jedis.isConnected() + "\n\tConnected to port: " + conn.getPort() + "\n\tTo host: " + conn.getHost() + "\n");
    }

//    @JSRunnable
//    public static String expire(String s, int i) {return jedis.expire(s,i).toString();}
//    @JSRunnable
//    public static String expire_byte(byte[] s, int i) {return jedis.expire(s,i).toString();}
//    @JSRunnable
//    public static String ttl(String s) {return jedis.ttl(s).toString();}
//    @JSRunnable
//    public static String append(String s, String i) {return jedis.append(s,i).toString();}
//    @JSRunnable
//    public static String clusterCountKeysInSlot(int i) {return jedis.clusterCountKeysInSlot(i).toString();}
//    @JSRunnable
//    public static String clusterKeySlot(String s) {return jedis.clusterKeySlot(s).toString();}
//    @JSRunnable
//    public static String decr(String s) {return jedis.decr(s).toString();}
//    @JSRunnable
//    public static String decr_byte(byte[] s) {return jedis.decr(s).toString();}
//    @JSRunnable
//    public static String decrBy(String s, int i) {return jedis.decrBy(s,i).toString();}
//    @JSRunnable
//    public static String decrBy_byte(byte[] s, int i) {return jedis.decrBy(s,i).toString();}
//    @JSRunnable
//    public static String eval(String s) {return jedis.eval(s).toString();}
//    @JSRunnable
//    public static String evalsha(String s) {return jedis.evalsha(s).toString();}
}
