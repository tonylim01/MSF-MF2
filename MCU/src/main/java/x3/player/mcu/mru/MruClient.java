package x3.player.mcu.mru;

import EDU.oswego.cs.dl.util.concurrent.FutureResult;
import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.*;
import com.rabbitmq.tools.json.JSONReader;
import com.rabbitmq.tools.json.JSONWriter;
import com.uangel.svc.util.TimedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by hwaseob on 2018-03-06.
 */
public class MruClient implements HeartbeatListener {
    final static Logger log = LoggerFactory.getLogger(MruClient.class);
    //offer
    //answer
    //negoDone
    //hangup
    //command
    private ConnectionFactory factory;
    Connection connection;
    Channel channel;
    final String EXCHANGE_NAME = "";//default exchange
    Map<String, FutureResult> futureResultMap = Collections.synchronizedMap(new TimedHashMap<String, FutureResult>(60000/*1 min*/));
    private HeartbeatListener heartbeatListener;

    public MruClient() {
    }

    public ConnectionFactory getConnectionFactory() {
        return factory;
    }

    public void setConnectionFactory(ConnectionFactory factory) {
        this.factory = factory;
    }

//    public void listen(HeartbeatListener listener) {
//    }

    public void connect() throws IOException, TimeoutException {
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare("mcu1_mcud",
                             true,//durable
                             false,//exclusive
                             false,//autoDelete
                             null);
        channel.basicConsume("mcu1_mcud",
                             true,
                             new DefaultConsumer(channel) {
                                 JSONReader r= new JSONReader();
                                 public void handleDelivery(String consumerTag,
                                                            Envelope envelope,
                                                            AMQP.BasicProperties properties,
                                                            byte[] body) throws IOException {
//                                     String message = new String(body, "UTF-8");
//                                     System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                                     log.info("MCU<--MRU "+new String(body));
                                     Map<String,Object> m =(Map<String,Object>)r.read(new String(body));
                                     Map<String, Object> h = (Map<String,Object>)m.get("header");//properties.getHeaders();
                                     String tid = String.valueOf(h.get("transactionId"));
                                     Integer rc = (Integer) h.get("reasonCode");
                                     FutureResult f = futureResultMap.get(tid);
                                     if (f != null)
                                     {
                                         //h.put("body", body);
                                         Map<String, Object> res = new HashMap<String, Object>();
                                         res.put("reasonCode", rc);
//                                         if (m.get("body") != null)
//                                         {
                                         res.put("body", m.get("body"));
//                                         }
                                         if (Integer.valueOf(0).equals(rc))
                                         {
                                             f.set(res);
                                         } else
                                         {
                                             f.setException(new MruClientException(rc));
                                         }
//                                         f.set(new Object[]{properties,
//                                                 body});
                                     }
                                 }
                             });

        channel.queueDeclare("mcu_mcud",
                             true,//durable
                             false,//exclusive
                             true,//autoDelete
                             null);
        setHeartbeatListener(this);
        channel.basicConsume("mcu_mcud",
                             true,
                             new DefaultConsumer(channel) {
                                 JSONReader r= new JSONReader();
                                 @Override
                                 public void handleDelivery(String consumerTag,
                                                            Envelope envelope,
                                                            AMQP.BasicProperties properties,
                                                            byte[] body) throws IOException {
                                     if (heartbeatListener != null)
                                     {
                                         Map<String, Object> h=(Map<String, Object>)r.read(new String(body));
                                         heartbeatListener.beat(h);
                                     }
                                 }
                             });
    }

    public void close() {
        try
        {
            channel.close();
            connection.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (TimeoutException e)
        {
            e.printStackTrace();
        }
    }

    protected FutureResult dispatch(//String exchange,
                                    //String routingKey,
                                    //AMQP.BasicProperties props,
                                    Map<String, Object> header,
//                                    ImmutableMap.Builder<String, Object> header,
                                    Map<String, Object> body) throws IOException {
        FutureResult f = new FutureResult();
        String tid = UUID.randomUUID().toString();
        futureResultMap.put(tid, f);
        header.put("transactionId", tid);

        Map<String, Object> m=new HashMap<>();
        m.put("header", header);
        if (body != null)
        {
            m.put("body", body);
        }
//        System.out.println(new JSONWriter().write(m));
        /*
        try
        {
            channel.basicPublish(EXCHANGE_NAME,//exchange
                                 "mru1_mrud",//routingKey
                                 new AMQP.BasicProperties
                                         .Builder()
//                                         .headers(header)
                                         .build(),
                                 new JSONWriter().write(m).getBytes());
//        } catch (IOException e)
//        {
//            log.error("MCU-->MRU "+dir + "\n"+ header);
//            throw e;
        } catch (NullPointerException e)
        {
            throw e;
        }//*/
        Map<String,Object> res = new HashMap<>();
        res.put("reasonCode", 0);
        f.set(res);
        return f;
    }

//    public FutureResult offer(String dir,
//                              String conference_id,
//                              String callId,
//                              String caller,
//                              String callee) throws IOException {
//        return offer(dir,
//                     conference_id,
//                     callId,
//                     caller,
//                     callee,
//                     null);
//    }

    public FutureResult offer(String dir,
                              String conference_id,
                              String callId,
                              String caller,
                              String callee,
                              String sdp) throws IOException {
//        log.info("MCU-->MRU offer callId = "+callId);
//        FutureResult f = new FutureResult();
//        f.set(null);
//        return f;

//        String tid=UUID.randomUUID().toString();
//        FutureResult f = new FutureResult();
//        futureResultMap.put(tid, f);
//        h.getHeaders().put("type", "A");
//        Map<String, Object> header=
//        ImmutableMap.Builder<String, Object> header =
//                ImmutableMap.<String, Object>builder().
//                        put("type", "msfmp_inbound_set_offer_req").
//                        put("sessionId", callId).
////                put("transactionId", tid).
//        put("msgFrom", "mcu1_mcud");
        //build();
        Map<String,Object> header=new HashMap<>();
        header.put("type", "mfmp_set_offer_req");
        header.put("sessionId", callId);
//                put("transactionId", tid).
        header.put("msgFrom", "mcu1_mcud");

//        String body = (sdp != null) ? new JSONWriter().write(ImmutableMap.<String, String>builder().
//                put("from_no", caller).
//                put("to_no", callee).
//                put("conference_id", conference_id).
//                put("sdp", sdp).
//                build())
//                : new JSONWriter().write(ImmutableMap.<String, String>builder().
//                put("from_no", caller).
//                put("to_no", callee).
////                        put("sdp", sdp).
//        build());
        Map<String,Object> body=new HashMap<>();
        if (sdp != null)
        {
            body.put("from_no", caller);
            body.put("to_no", callee);
            body.put("conference_id", conference_id);
            body.put("sdp", sdp);
        } else
        {
            body.put("from_no", caller);
            body.put("to_no", callee);
            body.put("conference_id", conference_id);
        }

        FutureResult f = dispatch(//EXCHANGE_NAME,//exchange
                                  //"mru1_mrud",//routingKey
                                  header,
                                  body);
        log.info("MCU-->MRU offer "+dir+ "\n" + header + "\n" + body);
        return f;
    }


    public FutureResult answer(String dir,
                               String callId) throws IOException {
//        log.info("MCU-->MRU answer callId = "+callId);
//        FutureResult f = new FutureResult();
//        f.set(null);
//        return f;

//        String tid=UUID.randomUUID().toString();
//        FutureResult f = new FutureResult();
//        futureResultMap.put(tid, f);
//        ImmutableMap.Builder<String, Object> header = ImmutableMap.<String, Object>builder().
//                put("type", "msfmp_inbound_get_answer_req").
//                put("sessionId", callId).
////                put("transactionId", tid).
//        put("msgFrom", "mcu1_mcud");
//                build();
        Map<String,Object> header=new HashMap<>();
        header.put("type", "mfmp_get_answer_req");
        header.put("sessionId", callId);
//                put("transactionId", tid).
        header.put("msgFrom", "mcu1_mcud");
//        String body="";
        FutureResult f = dispatch(//EXCHANGE_NAME,//exchange
                                  //"mru1_mrud",//routingKey
                                  header,
                                  null/*body.getBytes()*/);
        log.info("MCU-->MRU answer "+dir + "\n"+ header/*+"\n"+body*/);
        return f;
    }

//    public FutureResult negoDone(String dir,
//                                 String callId) throws IOException {
//        return negoDone(dir,
//                        callId,
//                        null);
//    }

    public FutureResult negoDone(String dir,
                                 String callId,
                                 String sdp) throws IOException {
//        log.info("MCU-->MRU negoDone callId = "+callId);
//        FutureResult f = new FutureResult();
//        f.set(null);
//        return f;

//        String tid=UUID.randomUUID().toString();
//        FutureResult f = new FutureResult();
//        futureResultMap.put(tid, f);
//        ImmutableMap.Builder<String, Object> header = ImmutableMap.<String, Object>builder().
//                put("type", "msfmp_nego_done_req").
//                put("sessionId", callId).
////                put("transactionId", tid).
//        put("msgFrom", "mcu1_mcud");
//                build();
        Map<String,Object> header=new HashMap<>();
        header.put("type", "mfmp_nego_done_req");
        header.put("sessionId", callId);
//                put("transactionId", tid).
        header.put("msgFrom", "mcu1_mcud");
//        String body = (sdp != null) ? new JSONWriter().write(ImmutableMap.<String, String>builder().
//                put("sdp", sdp).
//                build())
//                : "{}"/*new JSONWriter().write(ImmutableMap.<String, String>builder().
//                build())*/;
        Map<String,Object> body=new HashMap<>();
        if (sdp != null)
        {
            body.put("sdp", sdp);
        }

        FutureResult f = dispatch(//EXCHANGE_NAME,//exchange
//                             "mru1_mrud",//routingKey
                                  header,
                                  body);
        log.info("MCU-->MRU negoDone "+dir+ "\n" + header + "\n" + body);
        return f;
    }

    public FutureResult hangup(String dir, String callId) throws IOException {
//        log.info("MCU-->MRU hangup callId = "+callId);
//        FutureResult f = new FutureResult();
//        f.set(null);
//        return f;

//        String tid=UUID.randomUUID().toString();
//        FutureResult f = new FutureResult();
//        futureResultMap.put(tid, f);
//        ImmutableMap.Builder<String, Object> header = ImmutableMap.<String, Object>builder().
//                put("type", "msfmp_hangup_req").
//                put("sessionId", callId).
////                put("transactionId", tid).
//        put("msgFrom", "mcu1_mcud");
//                build();
        Map<String,Object> header=new HashMap<>();
        header.put("type", "mfmp_hangup_req");
        header.put("sessionId", callId);
//                put("transactionId", tid).
        header.put("msgFrom", "mcu1_mcud");
        FutureResult f = dispatch(//EXCHANGE_NAME,//exchange
//                             "mru1_mrud",//routingKey
                                  header,
                                  null);//body.getBytes());
        log.info("MCU-->MRU hangup "+ dir + "\n"+ header);
        return f;
    }

    public HeartbeatListener getHeartbeatListener() {
        return heartbeatListener;
    }

    public void setHeartbeatListener(HeartbeatListener heartbeatListener) {
        this.heartbeatListener = heartbeatListener;
    }

    @Override
    public void beat(Map<String, Object> h) {
//        log.info("MCU<--MRU heartbeat\n"+h);
    }


    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
        factory.setHost("172.16.0.45");
        factory.setUsername("hwaseob");
        factory.setPassword("uangel");
//        factory.setHost("192.168.2.115");//5672
//        factory.setUsername("mornbr");
//        factory.setPassword("mornbr");

        MruClient client = new MruClient();
        client.setConnectionFactory(factory);
//        client.setHeartbeatListener(new HeartbeatListener() {
//            @Override
//            public void beat(Map<String, Object> h) {
//                System.out.println(h);
//            }
//        });
        client.connect();
//        client.offer("INBOUND",
//                    "1234",
//                     "31ekf",
//                     "7687",
//                     "8233",
//                     "s=kwjef");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish("",//exchange
                             "mcu_mcud",//routingKey
                             new AMQP.BasicProperties
                                     .Builder()
                                     .build(),
                             "{\"session_total\":2000}".getBytes());
    }
}