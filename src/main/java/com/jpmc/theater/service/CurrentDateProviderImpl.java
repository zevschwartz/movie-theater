package com.jpmc.theater.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDate;

public class CurrentDateProviderImpl implements Serializable, CurrentDateProvider {
    private static @Nullable
    volatile CurrentDateProviderImpl instance = null;

    private CurrentDateProviderImpl() {
        if (instance != null) {
            throw new IllegalStateException("instance cannot be instantiated with null instance");
        }
    }

    /**
     * @return make sure to return singleton instance
     */
    public static @NotNull CurrentDateProviderImpl getInstance() {
        if (instance == null) {
            synchronized (CurrentDateProviderImpl.class) {
                if (instance == null) {
                    instance = new CurrentDateProviderImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public @NotNull LocalDate currentDate() {
        return LocalDate.now();
    }

    protected @NotNull CurrentDateProviderImpl readResolve() {
        return getInstance();
    }


}
