package org.embulk.input.box;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.box.sdk.BoxFile;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import org.embulk.config.*;
import org.embulk.exec.ExecutionInterruptedException;
import org.embulk.spi.BufferAllocator;
import org.embulk.spi.Exec;
import org.embulk.spi.FileInputPlugin;
import org.embulk.spi.TransactionalFileInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoxFileInputPlugin
        implements FileInputPlugin
{
    private final Logger logger = LoggerFactory.getLogger(BoxFileInputPlugin.class);

    @Override
    public ConfigDiff transaction(ConfigSource config, FileInputPlugin.Control control)
    {
        PluginTask task = config.loadConfig(PluginTask.class);
        return resume(task.dump(), 1, control);
    }

    @Override
    public ConfigDiff resume(TaskSource taskSource,
            int taskCount,
            FileInputPlugin.Control control)
    {
        control.run(taskSource, taskCount);
        return Exec.newConfigDiff();

    }

    @Override
    public void cleanup(TaskSource taskSource,
            int taskCount,
            List<TaskReport> successTaskReports)
    {
    }

    @Override
    public TransactionalFileInput open(TaskSource taskSource, int taskIndex)
    {
        try {
            final PluginTask task = taskSource.loadTask(PluginTask.class);
            BoxClient client = new BoxClient(task);
            List<BoxFile.Info> fileInfoList = client.getFileInfoList();
            return new FileInput(task,fileInfoList,client);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ExecutionInterruptedException(e);
        }
    }
}
