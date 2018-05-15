package cn.mageek.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mageek Chiu
 * @date 2018/5/10 0010:20:51
 */
public class ConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionTest.class);


    public static void main(String... arg) throws Exception {
//        Connection connection = new Connection();
//        connection.connect();

        Client client = new Client();
        client.connect("192.168.0.136","10102");

        logger.debug(client.set("192.168.0.136:10099","123456")+"");
        logger.debug(client.get("192.168.0.136:10099")+"");
        logger.debug(client.set("112","23")+"");
        logger.debug(client.del("1321")+"");
        logger.debug(client.del("112")+"");

//        client.close();



//        try(Client client = new Client("192.168.0.136","10102")){
//            logger.debug(client.set("192.168.0.136:10099","123456")+"");
//            logger.debug(client.get("192.168.0.136:10099")+"");
//            logger.debug(client.set("112","23")+"");
//            logger.debug(client.del("1321")+"");
//            logger.debug(client.del("112")+"");
//        }

    }

    @Test
    public void SplitTest(){
        // 结果都是2 所以末尾有没有分隔符都一样
        String a = "aa\r\nbb";
        logger.debug("{}",a.split("\r\n").length);//2
        a = "aa\r\nbb\r\n";
        logger.debug("{}",a.split("\r\n").length);//2


        a = "aa";
        logger.debug("{}",a.split("\r\n").length);//1

    }
}