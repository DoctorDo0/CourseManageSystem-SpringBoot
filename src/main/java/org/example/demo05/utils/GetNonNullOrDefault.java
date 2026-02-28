package org.example.demo05.utils;

import java.util.Arrays;
import java.util.Objects;

public class GetNonNullOrDefault {
    // 输入多个参数，获取并返回第一个不是null的值，使用时默认末尾为default
    @SafeVarargs
    public static <T> T getNonNull(T... ts) {
        return Arrays.stream(ts).filter(Objects::nonNull).findFirst().orElse(null);
    }
}
