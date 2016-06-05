@ECHO off
TITLE SETTING UP WORKSPACE
ECHO Downloading Forge
CALL gradlew setupDecompWorkspace --refresh-dependencies
ECHO Setting up for Eclipse Workspace
CALL gradlew eclipse
PAUSE