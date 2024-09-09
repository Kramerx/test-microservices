package com.project.transactional.util;

import java.util.UUID;

public final class Utils {
    public static Long generateUniqueId() {
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String lastTenDigits = uuid.length() > 10 ? uuid.substring(uuid.length() - 10) : uuid;
        return Long.valueOf(lastTenDigits);
    }
}
