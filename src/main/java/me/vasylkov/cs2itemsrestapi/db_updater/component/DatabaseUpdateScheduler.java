package me.vasylkov.cs2itemsrestapi.db_updater.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.db_updater.service.DatabaseTablesUpdater;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseUpdateScheduler
{
    private final DatabaseTablesUpdater tablesUpdater;

    @Scheduled(initialDelay = 5, fixedDelay = 7200000)  // 7200000 ms = 2 часа
    public void updateData()
    {
        tablesUpdater.updateTables();
    }
}
