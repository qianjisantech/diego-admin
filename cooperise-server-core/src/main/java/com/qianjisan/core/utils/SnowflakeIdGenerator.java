package com.qianjisan.core.utils;

/**
 * 雪花ID生成�?
 * 基于Twitter的Snowflake算法生成分布式唯一ID
 *
 * 雪花ID结构�?
 * - 1位符号位（始终为0�?
 * - 41位时间戳（毫秒级，从2024-01-01开始）
 * - 5位数据中心ID
 * - 5位工作机器ID
 * - 12位序列号
 *
 * 生成的ID�?4位long类型
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class SnowflakeIdGenerator {

    // 起始时间�?(2024-01-01 00:00:00)
    private static final long START_TIMESTAMP = 1704067200000L;

    // 各部分位�?
    private static final long SEQUENCE_BITS = 12;
    private static final long WORKER_ID_BITS = 5;
    private static final long DATACENTER_ID_BITS = 5;

    // 最大�?
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    // 位移偏移�?
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    // 数据中心ID和工作机器ID
    private final long datacenterId;
    private final long workerId;

    // 序列号和上次时间�?
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * 构造函�?
     *
     * @param datacenterId 数据中心ID (0-31)
     * @param workerId 工作机器ID (0-31)
     */
    public SnowflakeIdGenerator(long datacenterId, long workerId) {
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException("数据中心ID必须�?-" + MAX_DATACENTER_ID + "之间");
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("工作机器ID必须�?-" + MAX_WORKER_ID + "之间");
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    /**
     * 生成下一个ID
     *
     * @return 雪花ID
     */
    public synchronized long nextId() {
        long timestamp = getCurrentTimestamp();

        // 时钟回拨处理
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨，无法生成ID");
        }

        // 同一毫秒内的序列号递增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号溢出，等待下一毫秒
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 生成ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT) |
               (datacenterId << DATACENTER_ID_SHIFT) |
               (workerId << WORKER_ID_SHIFT) |
               sequence;
    }

    /**
     * 等待下一毫秒
     */
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }

    /**
     * 获取当前时间�?
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 解析雪花ID
     *
     * @param id 雪花ID
     * @return 解析结果
     */
    public static SnowflakeIdInfo parseId(long id) {
        long timestamp = (id >> TIMESTAMP_LEFT_SHIFT) + START_TIMESTAMP;
        long datacenterId = (id >> DATACENTER_ID_SHIFT) & MAX_DATACENTER_ID;
        long workerId = (id >> WORKER_ID_SHIFT) & MAX_WORKER_ID;
        long sequence = id & MAX_SEQUENCE;

        return new SnowflakeIdInfo(timestamp, datacenterId, workerId, sequence);
    }

    /**
     * 雪花ID信息
     */
    public static class SnowflakeIdInfo {
        private final long timestamp;
        private final long datacenterId;
        private final long workerId;
        private final long sequence;

        public SnowflakeIdInfo(long timestamp, long datacenterId, long workerId, long sequence) {
            this.timestamp = timestamp;
            this.datacenterId = datacenterId;
            this.workerId = workerId;
            this.sequence = sequence;
        }

        public long getTimestamp() { return timestamp; }
        public long getDatacenterId() { return datacenterId; }
        public long getWorkerId() { return workerId; }
        public long getSequence() { return sequence; }

        @Override
        public String toString() {
            return String.format("SnowflakeIdInfo{timestamp=%d, datacenterId=%d, workerId=%d, sequence=%d}",
                               timestamp, datacenterId, workerId, sequence);
        }
    }

    // 单例实例
    private static volatile SnowflakeIdGenerator instance;

    /**
     * 获取单例实例（默认配置）
     */
    public static SnowflakeIdGenerator getInstance() {
        if (instance == null) {
            synchronized (SnowflakeIdGenerator.class) {
                if (instance == null) {
                    // 使用默认的datacenterId=1, workerId=1
                    instance = new SnowflakeIdGenerator(1, 1);
                }
            }
        }
        return instance;
    }

    /**
     * 生成雪花ID的便捷方�?
     */
    public static long generateId() {
        return getInstance().nextId();
    }
}
