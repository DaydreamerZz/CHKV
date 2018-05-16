package cn.mageek.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 客户端获得数据
 * @author Mageek Chiu
 * @date 2018/5/6 0006:13:28
 */
public class DataResponse implements Serializable {
    private String lineType ;// +OK,-error msg,:number,$length\r\nstring\r\n
    private String msg;
    private List<String> msgList;

    private String ID;// 要实现请求响应的对应，必须要ID

    public DataResponse(String lineType, String msg) {
        this.lineType = lineType;
        this.msg = msg;
    }

    public DataResponse(String lineType, String msg, String ID) {
        this.lineType = lineType;
        this.msg = msg;
        this.ID = ID;
    }

    public DataResponse(String lineType, List<String> msgList) {
        this.lineType = lineType;
        this.msgList = msgList;
    }

    public DataResponse(String lineType, List<String> msgList, String ID) {
        this.lineType = lineType;
        this.msgList = msgList;
        this.ID = ID;
    }

    public DataResponse(String lineType, String msg, List<String> msgList, String ID) {
        this.lineType = lineType;
        this.msg = msg;
        this.msgList = msgList;
        this.ID = ID;
    }

    public List<String> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<String> msgList) {
        this.msgList = msgList;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "DataResponse -- lineType:"+getLineType()+",msg:"+getMsg()+",msgList:"+getMsgList()+", ID: "+ID;
    }
}
