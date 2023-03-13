# Box file input plugin for Embulk

TODO: Write short description here and build.gradle file.

## Overview

- **Plugin type**: file input
- **Resume supported**: yes
- **Cleanup supported**: yes

## Configuration

- **auth_method**: name of mechanism to authenticate requests(jwt or oauth. default: jwt)
  - "jwt": JWT Auth. see: https://developer.box.com/guides/authentication/jwt/
    - **json_config**: JSON formatted OAuth 2.0 with JWT app configuration. (string, required)
  - "oauth_2_0": OAuth 2.0 Auth. see: https://developer.box.com/guides/authentication/oauth2/
    - **access_token**: credentials used to represent the authenticated user. (string, required)
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
