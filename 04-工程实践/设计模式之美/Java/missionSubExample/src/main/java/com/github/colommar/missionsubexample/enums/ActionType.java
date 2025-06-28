package com.github.colommar.missionsubexample.enums;

/**
 * 行为枚举
 */
public enum ActionType {
    START(1, "开始"),
    STOP(2, "暂停"),
    ACHIEVE(3, "完成"),
    EXPIRE(4, "过期");
    
    private final int code;
    private final String message;
    
    ActionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
} 