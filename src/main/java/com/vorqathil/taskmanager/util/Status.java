package com.vorqathil.taskmanager.util;

import lombok.Getter;

@Getter
public enum Status {
    OPEN("Открыта"),
    IN_PROGRESS("В процессе"),
    DONE("Завершена");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }
}
