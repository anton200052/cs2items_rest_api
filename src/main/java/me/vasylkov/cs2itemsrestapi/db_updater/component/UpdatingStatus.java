package me.vasylkov.cs2itemsrestapi.db_updater.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UpdatingStatus
{
    private boolean updating;
}
