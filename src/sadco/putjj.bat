@echo off
if not exist i:\Environ\medm\it_lab\javaS\sadco goto error203
  echo Putting PreLoadWETDataFrame files
  cd i:\Environ\medm\it_lab\javaS\sadco
  copy PreLoadWETDataFrame.java  i:
  copy PreLoadWETDataFrame.class i:
  copy PreLoadWETDataFrame.java  mybak\PreLoadWETDataFrame.java.mybak
  i:
  copy PreLoadWETDataFrame.java.ub oldjava\PreLoadWETDataFrame.java
  del PreLoadWETDataFrame.java.ub
  d:
  del PreLoadWETDataFrame.java
  goto cont203
:error203
  echo *** ERROR *** - LAN might not be available
:cont203
