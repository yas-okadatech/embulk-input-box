package org.embulk.input.box;

import com.box.sdk.*;
import org.embulk.config.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoxClient {
    private final Logger logger = LoggerFactory.getLogger(BoxClient.class);
    private BoxDeveloperEditionAPIConnection client;
    private PluginTask pluginTask;

    public BoxClient(PluginTask pluginTask) {
        try {
            BoxConfig config = BoxConfig.readFrom(new StringReader(pluginTask.getJsonConfig()));
            logger.info(config.getClientId());
            client = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);
            this.pluginTask = pluginTask;
            logger.info("JWT authorization.");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ConfigException(e);
        }
    }

    public List<BoxFile.Info> getFileInfoList() {
        BoxFolder folder = new BoxFolder(client, pluginTask.getFolderId());
        logger.info(String.format("%s %b %b", folder.getID(), folder.iterator().hasNext(), pluginTask.getFilePrefix().isPresent()));

        List<BoxFile.Info> list = new ArrayList<>();

        for (BoxItem.Info itemInfo : folder) {
            if (isTargetFile(itemInfo)) {
                list.add((BoxFile.Info) itemInfo);
                if(list.size() % 1000 == 0) {
                    logger.info(String.format("find files %d", list.size()));
                }
            } else {
                logger.info(String.format("skip item name=%s", itemInfo.getName()));
            }
        }
        logger.info(String.format("total files %d", list.size()));
        return list;
    }

    public InputStream download(BoxFile.Info info) {
        BoxFile boxFile = info.getResource();
        URL url = boxFile.CONTENT_URL_TEMPLATE.build(boxFile.getAPI().getBaseURL(), new Object[]{boxFile.getID()});
        BoxAPIRequest request = new BoxAPIRequest(boxFile.getAPI(), url, "GET");
        BoxAPIResponse response = request.send();
        return response.getBody(null);
    }

    private boolean isTargetFile(BoxItem.Info itemInfo) {
        if (!(itemInfo instanceof BoxFile.Info)) {
            return false;
        }

        if (!pluginTask.getFilePrefix().isPresent()) {
            return true;
        }

        if (!itemInfo.getName().startsWith(pluginTask.getFilePrefix().get())) {
            return false;
        }

        return true;
    }

}
