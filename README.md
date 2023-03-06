# Box file input plugin for Embulk

TODO: Write short description here and build.gradle file.

## Overview

- **Plugin type**: file input
- **Resume supported**: yes
- **Cleanup supported**: yes

## Configuration

- **json_config**: JSON formatted OAuth 2.0 with JWT app configuration. (string, required)
- **folder_id**: target folder id (string, required)
- **path_prefix**: prefix of target files (string, required)

## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```

## Run

```
$ embulk run -I build/gemContents/lib/ config.yml
```
