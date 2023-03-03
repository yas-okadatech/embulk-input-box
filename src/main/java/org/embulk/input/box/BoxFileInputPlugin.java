package org.embulk.input.box;

import com.box.sdk.BoxFile;
import java.util.List;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigSource;
import org.embulk.config.TaskReport;
import org.embulk.config.TaskSource;
import org.embulk.spi.FileInputPlugin;
import org.embulk.spi.TransactionalFileInput;
import org.embulk.util.config.ConfigMapper;
import org.embulk.util.config.ConfigMapperFactory;
import org.embulk.util.config.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoxFileInputPlugin implements FileInputPlugin {
  private final Logger logger = LoggerFactory.getLogger(BoxFileInputPlugin.class);
  private static final ConfigMapperFactory CONFIG_MAPPER_FACTORY =
      ConfigMapperFactory.builder().addDefaultModules().build();

  @Override
  public ConfigDiff transaction(ConfigSource config, FileInputPlugin.Control control) {
    final ConfigMapper configMapper = CONFIG_MAPPER_FACTORY.createConfigMapper();
    PluginTask task = configMapper.map(config, PluginTask.class);
    return resume(task.toTaskSource(), 1, control);
  }

  @Override
  public ConfigDiff resume(TaskSource taskSource, int taskCount, FileInputPlugin.Control control) {
    control.run(taskSource, taskCount);
    return CONFIG_MAPPER_FACTORY.newConfigDiff();
  }

  @Override
  public void cleanup(TaskSource taskSource, int taskCount, List<TaskReport> successTaskReports) {}

  @Override
  public TransactionalFileInput open(TaskSource taskSource, int taskIndex) {
    try {
      final TaskMapper taskMapper = CONFIG_MAPPER_FACTORY.createTaskMapper();
      final PluginTask task = taskMapper.map(taskSource, PluginTask.class);
      BoxClient client = new BoxClient(task);
      List<BoxFile.Info> fileInfoList = client.getFileInfoList();
      return new FileInput(task, fileInfoList, client);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
