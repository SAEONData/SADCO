@echo off
if not exist i:\Environ\medm\it_lab\javaS\common goto error2
  echo Putting edmCommon files
  cd i:\Environ\medm\it_lab\javaS\common
  copy edmCommon.java  i:
  copy edmCommon.class i:
  copy edmCommon.java  mybak\edmCommon.java.mybak
  i:
  copy edmCommon.java.ub oldjava\edmCommon.java
  del edmCommon.java.ub
  d:
  del edmCommon.java
  goto cont2
:error2
  echo *** ERROR *** - LAN might not be available
:cont2
if not exist i:\Environ\medm\it_lab\javaS\common goto error4
  echo Putting edmCommonPC files
  cd i:\Environ\medm\it_lab\javaS\common
  copy edmCommonPC.java  i:
  copy edmCommonPC.class i:
  copy edmCommonPC.java  mybak\edmCommonPC.java.mybak
  i:
  copy edmCommonPC.java.ub oldjava\edmCommonPC.java
  del edmCommonPC.java.ub
  d:
  del edmCommonPC.java
  goto cont4
:error4
  echo *** ERROR *** - LAN might not be available
:cont4
