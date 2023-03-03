package org.embulk.input.box;

import com.box.sdk.BoxFile;
import java.util.List;
import org.embulk.config.TaskReport;
import org.embulk.spi.Exec;
import org.embulk.spi.TransactionalFileInput;
import org.embulk.util.config.ConfigMapperFactory;
import org.embulk.util.file.InputStreamFileInput;

public class FileInput extends InputStreamFileInput implements TransactionalFileInput {

  private static final ConfigMapperFactory CONFIG_MAPPER_FACTORY =
      ConfigMapperFactory.builder().addDefaultModules().build();

  public FileInput(PluginTask task, List<BoxFile.Info> fileInfoList, BoxClient client) {
    super(Exec.getBufferAllocator(), new SingleFileProvider(fileInfoList, client));
  }

  @Override
  public void abort() {}

  @Override
  public TaskReport commit() {
    return CONFIG_MAPPER_FACTORY.newTaskReport();
  }
}
