package org.embulk.input.box;

import java.util.Optional;
import org.embulk.util.config.Config;
import org.embulk.util.config.ConfigDefault;
import org.embulk.util.config.Task;

public interface PluginTask extends Task {
  @Config("auth_method")
  @ConfigDefault("jwt")
  String getAuthMeString();

  @Config("json_config")
  @ConfigDefault("null")
  Optional<String> getJsonConfig();

  @Config("access_token")
  @ConfigDefault("null")
  Optional<String> getAccessToken();

  @Config("folder_id")
  String getFolderId();

  @Config("file_prefix")
  @ConfigDefault("null")
  Optional<String> getFilePrefix();
}
