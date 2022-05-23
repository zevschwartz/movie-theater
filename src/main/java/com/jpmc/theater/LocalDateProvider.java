package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDate;

public class LocalDateProvider implements Serializable {
    private static @Nullable volatile LocalDateProvider instance = null;

    private LocalDateProvider() {
        if (instance != null) {
            throw new IllegalStateException("instance cannot be instantiated with null instance");
        }
    }

    /**
     * @return make sure to return singleton instance
     */
    public static @NotNull LocalDateProvider getInstance() {
        if (instance == null) {
            synchronized (LocalDateProvider.class) {
                if (instance == null) {
                    instance = new LocalDateProvider();
                }
            }
        }
        return instance;
    }

    public @NotNull LocalDate currentDate() {
        return LocalDate.now();
    }

    protected @NotNull LocalDateProvider readResolve() {
        return getInstance();
    }


}
