package com.yupi.yuzan.model.enums;

import lombok.Getter;

@Getter
public enum UserEnum {
    ADMIN("管理员", "admin"),
    USER("普通用户", "user");

    private final String text;
    private final String value;

    UserEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static UserEnum getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (UserEnum anEnum : UserEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
