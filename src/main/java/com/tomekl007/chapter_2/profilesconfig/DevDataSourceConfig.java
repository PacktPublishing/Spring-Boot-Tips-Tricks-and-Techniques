package com.tomekl007.chapter_2.profilesconfig;

import com.tomekl007.chapter_2.profilesconfig.DataSourceConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataSourceConfig implements DataSourceConfig {
    @Override
    public void setup() {
        System.out.println("Setting up dataSource for DEV environment. ");
    }
}
