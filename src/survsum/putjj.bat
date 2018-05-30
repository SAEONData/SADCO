@echo off
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error1
  echo Putting AddNewScientist files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy AddNewScientist.java  i:
  copy AddNewScientist.class i:
  copy AddNewScientist.java  mybak\AddNewScientist.java.mybak
  i:
  copy AddNewScientist.java.ub oldjava\AddNewScientist.java
  del AddNewScientist.java.ub
  d:
  del AddNewScientist.java
  goto cont1
:error1
  echo *** ERROR *** - LAN might not be available
:cont1
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error3
  echo Putting DisplaySurvsum files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy DisplaySurvsum.java  i:
  copy DisplaySurvsum.class i:
  copy DisplaySurvsum.java  mybak\DisplaySurvsum.java.mybak
  i:
  copy DisplaySurvsum.java.ub oldjava\DisplaySurvsum.java
  del DisplaySurvsum.java.ub
  d:
  del DisplaySurvsum.java
  goto cont3
:error3
  echo *** ERROR *** - LAN might not be available
:cont3
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error5
  echo Putting DisplaySurvsumList files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy DisplaySurvsumList.java  i:
  copy DisplaySurvsumList.class i:
  copy DisplaySurvsumList.java  mybak\DisplaySurvsumList.java.mybak
  i:
  copy DisplaySurvsumList.java.ub oldjava\DisplaySurvsumList.java
  del DisplaySurvsumList.java.ub
  d:
  del DisplaySurvsumList.java
  goto cont5
:error5
  echo *** ERROR *** - LAN might not be available
:cont5
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error8
  echo Putting PrintSurvsum files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy PrintSurvsum.java  i:
  copy PrintSurvsum.class i:
  copy PrintSurvsum.java  mybak\PrintSurvsum.java.mybak
  i:
  copy PrintSurvsum.java.ub oldjava\PrintSurvsum.java
  del PrintSurvsum.java.ub
  d:
  del PrintSurvsum.java
  goto cont8
:error8
  echo *** ERROR *** - LAN might not be available
:cont8
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error11
  echo Putting SadCommon files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy SadCommon.java  i:
  copy SadCommon.class i:
  copy SadCommon.java  mybak\SadCommon.java.mybak
  i:
  copy SadCommon.java.ub oldjava\SadCommon.java
  del SadCommon.java.ub
  d:
  del SadCommon.java
  goto cont11
:error11
  echo *** ERROR *** - LAN might not be available
:cont11
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error15
  echo Putting SurvsumCommon files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy SurvsumCommon.java  i:
  copy SurvsumCommon.class i:
  copy SurvsumCommon.java  mybak\SurvsumCommon.java.mybak
  i:
  copy SurvsumCommon.java.ub oldjava\SurvsumCommon.java
  del SurvsumCommon.java.ub
  d:
  del SurvsumCommon.java
  goto cont15
:error15
  echo *** ERROR *** - LAN might not be available
:cont15
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error17
  echo Putting UpdateChiefScBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateChiefScBlock.java  i:
  copy UpdateChiefScBlock.class i:
  copy UpdateChiefScBlock.java  mybak\UpdateChiefScBlock.java.mybak
  i:
  copy UpdateChiefScBlock.java.ub oldjava\UpdateChiefScBlock.java
  del UpdateChiefScBlock.java.ub
  d:
  del UpdateChiefScBlock.java
  goto cont17
:error17
  echo *** ERROR *** - LAN might not be available
:cont17
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error19
  echo Putting UpdateDataBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateDataBlock.java  i:
  copy UpdateDataBlock.class i:
  copy UpdateDataBlock.java  mybak\UpdateDataBlock.java.mybak
  i:
  copy UpdateDataBlock.java.ub oldjava\UpdateDataBlock.java
  del UpdateDataBlock.java.ub
  d:
  del UpdateDataBlock.java
  goto cont19
:error19
  echo *** ERROR *** - LAN might not be available
:cont19
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error21
  echo Putting UpdateMooringBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateMooringBlock.java  i:
  copy UpdateMooringBlock.class i:
  copy UpdateMooringBlock.java  mybak\UpdateMooringBlock.java.mybak
  i:
  copy UpdateMooringBlock.java.ub oldjava\UpdateMooringBlock.java
  del UpdateMooringBlock.java.ub
  d:
  del UpdateMooringBlock.java
  goto cont21
:error21
  echo *** ERROR *** - LAN might not be available
:cont21
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error23
  echo Putting UpdateObjectsBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateObjectsBlock.java  i:
  copy UpdateObjectsBlock.class i:
  copy UpdateObjectsBlock.java  mybak\UpdateObjectsBlock.java.mybak
  i:
  copy UpdateObjectsBlock.java.ub oldjava\UpdateObjectsBlock.java
  del UpdateObjectsBlock.java.ub
  d:
  del UpdateObjectsBlock.java
  goto cont23
:error23
  echo *** ERROR *** - LAN might not be available
:cont23
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error25
  echo Putting UpdateOcAreasBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateOcAreasBlock.java  i:
  copy UpdateOcAreasBlock.class i:
  copy UpdateOcAreasBlock.java  mybak\UpdateOcAreasBlock.java.mybak
  i:
  copy UpdateOcAreasBlock.java.ub oldjava\UpdateOcAreasBlock.java
  del UpdateOcAreasBlock.java.ub
  d:
  del UpdateOcAreasBlock.java
  goto cont25
:error25
  echo *** ERROR *** - LAN might not be available
:cont25
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error27
  echo Putting UpdatePeriodsBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdatePeriodsBlock.java  i:
  copy UpdatePeriodsBlock.class i:
  copy UpdatePeriodsBlock.java  mybak\UpdatePeriodsBlock.java.mybak
  i:
  copy UpdatePeriodsBlock.java.ub oldjava\UpdatePeriodsBlock.java
  del UpdatePeriodsBlock.java.ub
  d:
  del UpdatePeriodsBlock.java
  goto cont27
:error27
  echo *** ERROR *** - LAN might not be available
:cont27
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error29
  echo Putting UpdatePlatformBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdatePlatformBlock.java  i:
  copy UpdatePlatformBlock.class i:
  copy UpdatePlatformBlock.java  mybak\UpdatePlatformBlock.java.mybak
  i:
  copy UpdatePlatformBlock.java.ub oldjava\UpdatePlatformBlock.java
  del UpdatePlatformBlock.java.ub
  d:
  del UpdatePlatformBlock.java
  goto cont29
:error29
  echo *** ERROR *** - LAN might not be available
:cont29
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error31
  echo Putting UpdatePriInvsBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdatePriInvsBlock.java  i:
  copy UpdatePriInvsBlock.class i:
  copy UpdatePriInvsBlock.java  mybak\UpdatePriInvsBlock.java.mybak
  i:
  copy UpdatePriInvsBlock.java.ub oldjava\UpdatePriInvsBlock.java
  del UpdatePriInvsBlock.java.ub
  d:
  del UpdatePriInvsBlock.java
  goto cont31
:error31
  echo *** ERROR *** - LAN might not be available
:cont31
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error33
  echo Putting UpdateProjectBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateProjectBlock.java  i:
  copy UpdateProjectBlock.class i:
  copy UpdateProjectBlock.java  mybak\UpdateProjectBlock.java.mybak
  i:
  copy UpdateProjectBlock.java.ub oldjava\UpdateProjectBlock.java
  del UpdateProjectBlock.java.ub
  d:
  del UpdateProjectBlock.java
  goto cont33
:error33
  echo *** ERROR *** - LAN might not be available
:cont33
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error35
  echo Putting UpdatePSDdataBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdatePSDdataBlock.java  i:
  copy UpdatePSDdataBlock.class i:
  copy UpdatePSDdataBlock.java  mybak\UpdatePSDdataBlock.java.mybak
  i:
  copy UpdatePSDdataBlock.java.ub oldjava\UpdatePSDdataBlock.java
  del UpdatePSDdataBlock.java.ub
  d:
  del UpdatePSDdataBlock.java
  goto cont35
:error35
  echo *** ERROR *** - LAN might not be available
:cont35
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error37
  echo Putting UpdateResLabBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateResLabBlock.java  i:
  copy UpdateResLabBlock.class i:
  copy UpdateResLabBlock.java  mybak\UpdateResLabBlock.java.mybak
  i:
  copy UpdateResLabBlock.java.ub oldjava\UpdateResLabBlock.java
  del UpdateResLabBlock.java.ub
  d:
  del UpdateResLabBlock.java
  goto cont37
:error37
  echo *** ERROR *** - LAN might not be available
:cont37
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error39
  echo Putting UpdateSamplesBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateSamplesBlock.java  i:
  copy UpdateSamplesBlock.class i:
  copy UpdateSamplesBlock.java  mybak\UpdateSamplesBlock.java.mybak
  i:
  copy UpdateSamplesBlock.java.ub oldjava\UpdateSamplesBlock.java
  del UpdateSamplesBlock.java.ub
  d:
  del UpdateSamplesBlock.java
  goto cont39
:error39
  echo *** ERROR *** - LAN might not be available
:cont39
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error41
  echo Putting UpdateSamplingBatches files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateSamplingBatches.java  i:
  copy UpdateSamplingBatches.class i:
  copy UpdateSamplingBatches.java  mybak\UpdateSamplingBatches.java.mybak
  i:
  copy UpdateSamplingBatches.java.ub oldjava\UpdateSamplingBatches.java
  del UpdateSamplingBatches.java.ub
  d:
  del UpdateSamplingBatches.java
  goto cont41
:error41
  echo *** ERROR *** - LAN might not be available
:cont41
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error42
  echo Putting UpdateSamplingBatches_del files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateSamplingBatches_del.java  i:
  copy UpdateSamplingBatches_del.class i:
  copy UpdateSamplingBatches_del.java  mybak\UpdateSamplingBatches_del.java.mybak
  i:
  copy UpdateSamplingBatches_del.java.ub oldjava\UpdateSamplingBatches_del.java
  del UpdateSamplingBatches_del.java.ub
  d:
  del UpdateSamplingBatches_del.java
  goto cont42
:error42
  echo *** ERROR *** - LAN might not be available
:cont42
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error43
  echo Putting UpdateSamplingBatches_keep files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateSamplingBatches_keep.java  i:
  copy UpdateSamplingBatches_keep.class i:
  copy UpdateSamplingBatches_keep.java  mybak\UpdateSamplingBatches_keep.java.mybak
  i:
  copy UpdateSamplingBatches_keep.java.ub oldjava\UpdateSamplingBatches_keep.java
  del UpdateSamplingBatches_keep.java.ub
  d:
  del UpdateSamplingBatches_keep.java
  goto cont43
:error43
  echo *** ERROR *** - LAN might not be available
:cont43
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error45
  echo Putting UpdateStationsBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateStationsBlock.java  i:
  copy UpdateStationsBlock.class i:
  copy UpdateStationsBlock.java  mybak\UpdateStationsBlock.java.mybak
  i:
  copy UpdateStationsBlock.java.ub oldjava\UpdateStationsBlock.java
  del UpdateStationsBlock.java.ub
  d:
  del UpdateStationsBlock.java
  goto cont45
:error45
  echo *** ERROR *** - LAN might not be available
:cont45
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error46
  echo Putting UpdateStationsBlock_keep files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateStationsBlock_keep.java  i:
  copy UpdateStationsBlock_keep.class i:
  copy UpdateStationsBlock_keep.java  mybak\UpdateStationsBlock_keep.java.mybak
  i:
  copy UpdateStationsBlock_keep.java.ub oldjava\UpdateStationsBlock_keep.java
  del UpdateStationsBlock_keep.java.ub
  d:
  del UpdateStationsBlock_keep.java
  goto cont46
:error46
  echo *** ERROR *** - LAN might not be available
:cont46
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error48
  echo Putting UpdateSurvNamBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateSurvNamBlock.java  i:
  copy UpdateSurvNamBlock.class i:
  copy UpdateSurvNamBlock.java  mybak\UpdateSurvNamBlock.java.mybak
  i:
  copy UpdateSurvNamBlock.java.ub oldjava\UpdateSurvNamBlock.java
  del UpdateSurvNamBlock.java.ub
  d:
  del UpdateSurvNamBlock.java
  goto cont48
:error48
  echo *** ERROR *** - LAN might not be available
:cont48
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error50
  echo Putting UpdateTCountryBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateTCountryBlock.java  i:
  copy UpdateTCountryBlock.class i:
  copy UpdateTCountryBlock.java  mybak\UpdateTCountryBlock.java.mybak
  i:
  copy UpdateTCountryBlock.java.ub oldjava\UpdateTCountryBlock.java
  del UpdateTCountryBlock.java.ub
  d:
  del UpdateTCountryBlock.java
  goto cont50
:error50
  echo *** ERROR *** - LAN might not be available
:cont50
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error52
  echo Putting UpdateTrackChBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateTrackChBlock.java  i:
  copy UpdateTrackChBlock.class i:
  copy UpdateTrackChBlock.java  mybak\UpdateTrackChBlock.java.mybak
  i:
  copy UpdateTrackChBlock.java.ub oldjava\UpdateTrackChBlock.java
  del UpdateTrackChBlock.java.ub
  d:
  del UpdateTrackChBlock.java
  goto cont52
:error52
  echo *** ERROR *** - LAN might not be available
:cont52
if not exist i:\Environ\medm\it_lab\javaS\survsum goto error54
  echo Putting UpdateZonesBlock files
  cd i:\Environ\medm\it_lab\javaS\survsum
  copy UpdateZonesBlock.java  i:
  copy UpdateZonesBlock.class i:
  copy UpdateZonesBlock.java  mybak\UpdateZonesBlock.java.mybak
  i:
  copy UpdateZonesBlock.java.ub oldjava\UpdateZonesBlock.java
  del UpdateZonesBlock.java.ub
  d:
  del UpdateZonesBlock.java
  goto cont54
:error54
  echo *** ERROR *** - LAN might not be available
:cont54
