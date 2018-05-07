package cn.mageek.namenode.service;

import cn.mageek.namenode.handler.HeartBeatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 管理所有dataNode
 * @author Mageek Chiu
 * @date 2018/5/7 0007:20:18
 */
public class DataNodeManager implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(DataNodeManager.class);
    private static String dataNodePort;
    private static final Map<String,Channel> channelMap = new ConcurrentHashMap<>();//管理所有datanode

    private CountDownLatch countDownLatch;

    static {
        try( InputStream in = ClassLoader.class.getResourceAsStream("/app.properties")) {
            Properties pop = new Properties();
            pop.load(in);
            dataNodePort = pop.getProperty("namenode.datanode.port");// 对dataNode开放的端口
            logger.debug("config dataNodePort:{}", dataNodePort);
        } catch (IOException e) {
            logger.error("read config error",e);
        }
    }

    public DataNodeManager(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//接收连接
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);//处理连接的I/O事件
//        EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(4);//处理耗时业务逻辑，我实际上为了统一起见把全部业务逻辑都放这里面了
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//新建一个channel
                    .option(ChannelOption.SO_BACKLOG, 64)//最大等待连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("ReadTimeoutHandler",new ReadTimeoutHandler(40));// in // 多少秒超时
                            ch.pipeline().addLast(new ObjectDecoder(2048, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));// in 进制缓存类加载器
                            ch.pipeline().addLast(new ObjectEncoder());// out
                            ch.pipeline().addLast(new HeartBeatHandler(channelMap));// in
                        }
                    });

            // Start the server. 采用同步等待的方式
            ChannelFuture f = b.bind(Integer.parseInt(dataNodePort)).sync();
            logger.info("ClientManager is up now and listens on {}", f.channel().localAddress());
            countDownLatch.countDown();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            logger.info("ClientManager is down");

        } catch (InterruptedException e) {
            logger.error("ClientManager start error: ", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
//            businessGroup.shutdownGracefully();
        }

    }




}




