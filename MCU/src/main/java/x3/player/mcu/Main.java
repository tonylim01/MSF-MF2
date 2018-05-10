package x3.player.mcu;

import com.rabbitmq.client.ConnectionFactory;
import com.uangel.svc.util.FileWatchdog;
import com.uangel.svc.util.LocalIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import x3.player.mcu.mru.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Thread.sleep(1000);
        Properties config=new Properties();
        FileInputStream in=new FileInputStream("config/mcu.properties");
        try
        {
            config.load(in);
        } finally
        {
            in.close();
        }
        int port=Integer.parseInt(config.getProperty("port", "5070"));
        log.info("port = "+port);
        String redis_host = config.getProperty("redis.host", "localhost");
        int redis_port=Integer.parseInt(config.getProperty("redis.port", "6379"));
        log.info("redis = "+redis_host+":"+redis_port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig,
                                       redis_host,
                                       redis_port,
                                       1000/*timeout*/);

        SipSignal sip = new SipSignal();

        Client client = new Client() {
            @Override
            protected void onServiceStarted(String callId, int aiif_id) {
                Session s=sip.getSessionMap().get(callId);
                if (s == null)
                {
                    log.warn("session is not found");
                    return;
                }
                if (s.isClosed())
                {
                    log.warn("session is closed");
                    return;
                }
//                s.setAiifId(aiif_id);
                String mdn=s.getCaller()/*"01012341234"*/;
                try
                {
                    aiif_allocated(callId,
                                   mdn,
                                   aiif_id);
                } catch (IOException e)
                {
                    log.error(e.toString(), e);
                }
            }
        };
        String mq_host = config.getProperty("rabbitmq.host", "localhost");
        String user=config.getProperty("rabbitmq.user");
        String passwd=config.getProperty("rabbitmq.passwd");
        log.info("rabbitmq = "+mq_host+", "+user+":"+passwd);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(mq_host);
        if (user != null)
        {
            factory.setUsername(user);
        }
        if (passwd != null)
        {
            factory.setPassword(passwd);
        }
        client.setConnectionFactory(factory);
        client.connect();


        String localIP = LocalIP.getLocalAddress().toString().substring(1);
        log.info("Local IP = "+localIP);
        sip.setHost(localIP);
        sip.setPort(port);
        sip.setPool(pool);
        sip.setClient(client);
        sip.init();
        log.info("MCU start,.");

        //https://stackoverflow.com/questions/16251273/can-i-watch-for-single-file-change-with-watchservice-not-the-whole-directory
        FileWatchdog wd=new FileWatchdog("lib/MCU-1.0.jar") {
            @Override
            protected void doOnChange() {
                //https://stackoverflow.com/questions/3015030/how-to-programmatically-restart-a-jar
                log.info("restart");
                Runtime runtime = Runtime.getRuntime();
                try
                {
                    Thread.sleep(1000);
                    String cmd="java -jar lib/MCU-1.0.jar";
                    log.info(cmd);
                    Process proc = runtime.exec(cmd);
                } catch (Exception e)
                {
                    //e.printStackTrace();
                    log.info(e.toString(),e);
                }
                log.info("exit");
                System.exit(0);
            }
        };//
        wd.setDelay(5000L);
        wd.start();
    }
}
