package cn.mageek.datanode.handler;

import cn.mageek.common.model.DataRequest;
import cn.mageek.common.util.Decoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * server收到的buffer转换为消息对象
 * @author Mageek Chiu
 * @date 2018/5/5 0005:14:32
 */
public class RcvMsgHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RcvMsgHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf buf  = (ByteBuf) msg;
        try {
            List<DataRequest> dataRequestList = Decoder.bytesToDataRequests(buf);
            dataRequestList.forEach(ctx::fireChannelRead);// 多个命令多次次传输给下一个handler
        }catch (Exception e){
            logger.error("parse data :{} , from: {} , error: ", ByteBufUtil.hexDump(buf),ctx.channel().remoteAddress(),e);
        }finally {
            buf.release();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }
}
