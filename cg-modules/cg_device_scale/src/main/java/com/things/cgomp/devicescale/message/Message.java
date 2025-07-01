package com.things.cgomp.devicescale.message;

import com.things.cgomp.devicescale.annotation.Property;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class Message<T extends AbstractBody> extends AbstractMessage<T> {


    protected int seq;

    private String ts;
    //命令字
    protected int cmd;


    protected Integer version;

    //数据是否加密
    protected boolean encryptionType;


    @Property(index = 0, type = DataType.UNSIGNED_SHORT_LE, desc = "序列号")
    public int getSeq() {
        return seq;
    }

    @Property(index = 2, type = DataType.CP56Time2a, desc = "发送时间")
    public String getTs() {
        return ts;
    }


    @Property(index = 9, type = DataType.BYTE, desc = "加密标志")
    public boolean getEncryptionType() {
        return encryptionType;
    }


    @Property(index = 10, type = DataType.BYTE, desc = "CMD")
    public int getCmd() {
        return cmd;
    }


    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEncryptionType(boolean encryptionType) {
        this.encryptionType = encryptionType;
    }


    public void setTs(String ts) {
        this.ts = ts;
    }


    public String getVersion() {
        return "2.0";
    }


    /**
     * 去掉开始符，CRC
     *
     * @return
     */
    @Override
    public Integer getHeaderLength() {
        return 11;
    }

}
