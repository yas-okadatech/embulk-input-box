package org.embulk.input.box;

import com.box.sdk.BoxFile;
import org.embulk.config.TaskReport;
import org.embulk.spi.Exec;
import org.embulk.spi.TransactionalFileInput;
import org.embulk.spi.util.InputStreamFileInput;

import java.util.List;

public class FileInput extends InputStreamFileInput implements TransactionalFileInput {
    public FileInput(PluginTask task, List<BoxFile.Info> fileInfoList, BoxClient client) {
        super(task.getBufferAllocator(), new SingleFileProvider(fileInfoList, client));
    }

    @Override
    public void abort() {

    }

    @Override
    public TaskReport commit() {
        return Exec.newTaskReport();
    }
}
