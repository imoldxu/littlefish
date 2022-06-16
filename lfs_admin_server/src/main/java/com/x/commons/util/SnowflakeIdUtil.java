package com.x.commons.util;

/**
 * @author zhouzhixuan
 */
public final class SnowflakeIdUtil {
    /**
     * 起始的时间戳
     */
    private static final long START_TIMESTAMP = 1480166465631L;
    /**
     * 序列号占用的位数
     */
    private static final long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private static final long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数
     */
    private static final long DATACENTER_BIT = 5;
    /**
     * 数据中心最大值
     */
    public static final long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    /**
     * 机器标识最大值 结果是31
     */
    public static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    /**
     * 序列号最大值
     */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 机器标识向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    /**
     * 数据中心向左的位移
     */
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    /**
     * 时间戳向左的位移
     */
    private static final long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    /**
     * 数据中心ID
     */
    private final long dataCenterId;
    /**
     * 机器标识
     */
    private final long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;

    public SnowflakeIdUtil(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATACENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }

        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }

        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return 下一个ID
     */
    public synchronized long nextId() {
        long currentTimestamp = getNewTimestamp();
        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Snowflake error: clock moved backwards, refusing to generate id.");
        }

        if (currentTimestamp == lastTimestamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currentTimestamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return (currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    /**
     * 产生下一个格式化过的ID
     *
     * @param format 格式
     * @return 下一个格式化过的ID
     */
    public synchronized String nextId(String format) {
        return String.format(format, nextId());
    }

    private long getNextMill() {
        long mill = getNewTimestamp();
        while (mill <= lastTimestamp) {
            mill = getNewTimestamp();
        }
        return mill;
    }

    private long getNewTimestamp() {
        return System.currentTimeMillis();
    }
}
