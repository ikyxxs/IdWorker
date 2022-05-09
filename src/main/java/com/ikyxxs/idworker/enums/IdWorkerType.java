package com.ikyxxs.idworker.enums;

/**
 * 发号器业务类型
 */
public enum IdWorkerType {

    ORDER_ID("ORDER_ID", "订单号"),
    ;

    private final String type;
    private final String  desc;

    IdWorkerType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
