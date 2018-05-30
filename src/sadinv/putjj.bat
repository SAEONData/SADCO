@echo off
if not exist i:\Environ\medm\it_lab\javaS\sadinv goto error33
  echo Putting SendEmail files
  cd i:\Environ\medm\it_lab\javaS\sadinv
  copy SendEmail.java  i:
  copy SendEmail.class i:
  copy SendEmail.java  mybak\SendEmail.java.mybak
  i:
  copy SendEmail.java.ub oldjava\SendEmail.java
  del SendEmail.java.ub
  d:
  del SendEmail.java
  goto cont33
:error33
  echo *** ERROR *** - LAN might not be available
:cont33
