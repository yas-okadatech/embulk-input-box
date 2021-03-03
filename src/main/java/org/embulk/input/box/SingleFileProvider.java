package org.embulk.input.box;

import com.box.sdk.BoxFile;
import org.embulk.spi.util.InputStreamFileInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class SingleFileProvider implements InputStreamFileInput.Provider {
    private final Logger logger = LoggerFactory.getLogger(SingleFileProvider.class);

    private Iterator<BoxFile.Info> iterator;
    private BoxClient client;

    public SingleFileProvider(List<BoxFile.Info> fileInfoList, BoxClient client) {
        this.iterator = fileInfoList.iterator();
        this.client = client;

        logger.info("download files");
    }


    @Override
    public InputStream openNext() {
        if (!iterator.hasNext()) {
            return null;
        }
        BoxFile.Info info = iterator.next();
        return client.download(info);
    }

    @Override
    public void close() {

    }
}
