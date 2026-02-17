package me.vasylkov.cs2itemsrestapi.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.exception.DatabaseUpdateInProgressException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbUpdateChecker
{
    private final DbUpdateStatus updateStatus;

    public void throwIfUpdating()
    {
        if (updateStatus.isUpdating())
        {
            throw new DatabaseUpdateInProgressException("Database update in progress, try again later");
        }
    }
}
