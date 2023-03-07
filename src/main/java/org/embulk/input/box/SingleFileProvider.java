package org.embulk.input.box;

import com.box.sdk.BoxFile;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.embulk.util.file.InputStreamFileInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public void close() {}
}
