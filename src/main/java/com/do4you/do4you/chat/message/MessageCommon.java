package com.do4you.do4you.chat.message;

import java.time.format.DateTimeFormatter;

public interface MessageCommon {
    public static DateTimeFormatter messageDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
}
