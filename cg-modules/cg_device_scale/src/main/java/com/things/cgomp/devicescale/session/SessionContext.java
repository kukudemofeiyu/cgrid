package com.things.cgomp.devicescale.session;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson2.annotation.JSONField;
import com.things.cgomp.devicescale.call.ServiceCallBack;
import com.things.cgomp.devicescale.message.resp.M0AResp;
import com.things.cgomp.devicescale.utils.ByteUtil;
import lombok.Getter;
import org.smartboot.socket.transport.AioSession;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Getter
public class SessionContext {

    private String sn;
    @JSONField(serialize = false)
    private AioSession session;
    private Boolean authPass;
    private String aesKey;
    private M0AResp priceMode;


    public M0AResp getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(M0AResp priceMode) {
        this.priceMode = priceMode;
    }

    private AtomicInteger seq = new AtomicInteger(0);

    private Map<String /** 枪 **/, State> cgEfSessions = new ConcurrentHashMap<>();

    private Map<String /** Id **/, Pair<String, Long /**待发送到云的 hex，创建时间 **/>> syncHex = new ConcurrentHashMap<>();


    public SessionContext(String sn) {
        this.sn = sn;
    }

    public AioSession getSession() {
        return session;
    }

    public void setSession(AioSession session) {
        this.session = session;
    }


    public byte[] nextSeq() {
        int rSeq = seq.incrementAndGet();
        return ByteUtil.intToByte2L(rSeq);
    }


    public Boolean getAuthPass() {
        return authPass;
    }

    public void setAuthPass(Boolean authPass) {
        this.authPass = authPass;
    }

    public void setAESSecret(String aesKey) {
        this.aesKey = aesKey;
    }

    public void setSeq(AtomicInteger seq) {
        this.seq = seq;
    }

    public void setCgEfSessions(Map<String, State> cgEfSessions) {
        this.cgEfSessions = cgEfSessions;
    }

    public void setSyncHex(Map<String, Pair<String, Long>> syncHex) {
        this.syncHex = syncHex;
    }

    public void addSyncQueue(String key, Pair<String, Long> pair) {
        if (syncHex.size() < 50) {
            syncHex.put(key, pair);
        }

    }

    public void delSyncQueueData(String key) {
        syncHex.remove(key);
    }

    private Timer timer;

    public synchronized void checkRealDataChange(String aliSn, ServiceCallBack serviceCallBack) {
//        if (null == timer) {
//            timer = new Timer();
//            TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
//                    System.out.println("Task executed at " + System.currentTimeMillis());
//                }
//            };
//            timer.schedule(task, 1000, 10000); // 延迟1秒,每10秒上报
//        }
        //如果有变化,马上执行
        State state = cgEfSessions.get(aliSn);
        if (null != state) {
            String orderNo = state.getOrderNo();
            //有变化
            if (orderNo != null && !orderNo.equals(state.getLastOrderNo())) {
                serviceCallBack.call(1, aliSn, state);

            }
        }

    }

    public void close() {
        session.close();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
