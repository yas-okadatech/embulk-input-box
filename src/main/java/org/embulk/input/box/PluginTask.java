package org.embulk.input.box;

import com.google.common.base.Optional;
import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.config.ConfigInject;
import org.embulk.config.Task;
import org.embulk.spi.BufferAllocator;

public interface PluginTask
        extends Task
{
    // configuration option 1 (required integer)
    @Config("json_config")
    String getJsonConfig();

    @Config("folder_id")
    String getFolderId();

    @Config("file_prefix")
    @ConfigDefault("null")
    Optional<String> getFilePrefix();

    @ConfigInject
    BufferAllocator getBufferAllocator();
}