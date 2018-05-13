package cn.mageek.datanode.command;

import cn.mageek.common.command.AbstractDataNodeCommand;
import cn.mageek.common.model.DataRequest;
import cn.mageek.common.model.DataResponse;
import cn.mageek.common.model.WebMsgObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static cn.mageek.common.model.LineType.INT_NUM;
import static cn.mageek.datanode.main.DataNode.DATA_POOL;


/**
 * @author Mageek Chiu
 * @date 2018/5/6 0007:13:49
 */
public class CommandDEL extends AbstractDataNodeCommand {

//    private static final Logger logger = LoggerFactory.getLogger(CommandDEL.class);

    @Override
    public DataResponse receive(DataRequest dataRequest) {
        String oldValue = DATA_POOL.remove(dataRequest.getKey());//
        if (oldValue==null) return new DataResponse(INT_NUM,"0");// 不存在
        return new DataResponse(INT_NUM,"1");// 成功删除
    }

    @Override
    public DataResponse send(WebMsgObject webMsgObject) {
        return null;
    }

}
