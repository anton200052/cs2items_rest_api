package me.vasylkov.cs2itemsrestapi.db_updater.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DbUpdateStatus
{
    private boolean updating;
}
