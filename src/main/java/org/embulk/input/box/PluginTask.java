package org.embulk.input.box;

import java.util.Optional;
import org.embulk.util.config.Config;
import org.embulk.util.config.ConfigDefault;
import org.embulk.util.config.Task;

public interface PluginTask extends Task {
  @Config("json_config")
  String getJsonConfig();

  @Config("folder_id")
  String getFolderId();

  @Config("file_prefix")
  @ConfigDefault("null")
  Optional<String> getFilePrefix();
}
