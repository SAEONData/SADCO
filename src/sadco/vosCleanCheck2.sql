spool vosCleanCheck2

prompt from D:\MyFiles\Java\AppsS\sadco\vosCleanCheck2.sql

prompt Main : Surface_Temperature
select count(*) from vos_main where
Surface_Temperature < -2 or Surface_Temperature > 37;

prompt Main : Drybulb
select count(*) from vos_main where
Drybulb < -25 or Drybulb > 40;

prompt Main : Wetbulb
select count(*) from vos_main where
Wetbulb < -25 or Wetbulb > 40;

prompt Main : Dewpoint
select count(*) from vos_main where
Dewpoint < -25 or Dewpoint > 40;

prompt Main : Atmospheric_Pressure
select count(*) from vos_main where
Atmospheric_Pressure < 930 or Atmospheric_Pressure > 1050;

prompt Main : Swell_Height
select count(*) from vos_main where
Swell_Height < 0 or Swell_Height > 17.5;

prompt Main : Swell_Period
select count(*) from vos_main where
Swell_Period < 0 or Swell_Period > 25;

prompt Main : Wave_Height
select count(*) from vos_main where
Wave_Height < 0 or Wave_Height > 17.5;

prompt Main : Wave_Period
select count(*) from vos_main where
Wave_Period < 0 or Wave_Period > 20;

prompt Main : Wind_Speed
select count(*) from vos_main where
Wind_Speed < 0 or Wind_Speed > 40;

prompt Main2 : Surface_Temperature
select count(*) from vos_main2 where
Surface_Temperature < -2 or Surface_Temperature > 37;

prompt Main2 : Drybulb
select count(*) from vos_main2 where
Drybulb < -25 or Drybulb > 40;

prompt Main2 : Wetbulb
select count(*) from vos_main2 where
Wetbulb < -25 or Wetbulb > 40;

prompt Main2 : Dewpoint
select count(*) from vos_main2 where
Dewpoint < -25 or Dewpoint > 40;

prompt Main2 : Atmospheric_Pressure
select count(*) from vos_main2 where
Atmospheric_Pressure < 930 or Atmospheric_Pressure > 1050;

prompt Main2 : Swell_Height
select count(*) from vos_main2 where
Swell_Height < 0 or Swell_Height > 17.5;

prompt Main2 : Swell_Period
select count(*) from vos_main2 where
Swell_Period < 0 or Swell_Period > 25;

prompt Main2 : Wave_Height
select count(*) from vos_main2 where
Wave_Height < 0 or Wave_Height > 17.5;

prompt Main2 : Wave_Period
select count(*) from vos_main2 where
Wave_Period < 0 or Wave_Period > 20;

prompt Main2 : Wind_Speed
select count(*) from vos_main2 where
Wind_Speed < 0 or Wind_Speed > 40;

prompt Arch : Surface_Temperature
select count(*) from vos_arch where
Surface_Temperature < -2 or Surface_Temperature > 37;

prompt Arch : Drybulb
select count(*) from vos_arch where
Drybulb < -25 or Drybulb > 40;

prompt Arch : Wetbulb
select count(*) from vos_arch where
Wetbulb < -25 or Wetbulb > 40;

prompt Arch : Dewpoint
select count(*) from vos_arch where
Dewpoint < -25 or Dewpoint > 40;

prompt Arch : Atmospheric_Pressure
select count(*) from vos_arch where
Atmospheric_Pressure < 930 or Atmospheric_Pressure > 1050;

prompt Arch : Swell_Height
select count(*) from vos_arch where
Swell_Height < 0 or Swell_Height > 17.5;

prompt Arch : Swell_Period
select count(*) from vos_arch where
Swell_Period < 0 or Swell_Period > 25;

prompt Arch : Wave_Height
select count(*) from vos_arch where
Wave_Height < 0 or Wave_Height > 17.5;

prompt Arch : Wave_Period
select count(*) from vos_arch where
Wave_Period < 0 or Wave_Period > 20;

prompt Arch : Wind_Speed
select count(*) from vos_arch where
Wind_Speed < 0 or Wind_Speed > 40;

prompt Arch2 : Surface_Temperature
select count(*) from vos_arch2 where
Surface_Temperature < -2 or Surface_Temperature > 37;

prompt Arch2 : Drybulb
select count(*) from vos_arch2 where
Drybulb < -25 or Drybulb > 40;

prompt Arch2 : Wetbulb
select count(*) from vos_arch2 where
Wetbulb < -25 or Wetbulb > 40;

prompt Arch2 : Dewpoint
select count(*) from vos_arch2 where
Dewpoint < -25 or Dewpoint > 40;

prompt Arch2 : Atmospheric_Pressure
select count(*) from vos_arch2 where
Atmospheric_Pressure < 930 or Atmospheric_Pressure > 1050;

prompt Arch2 : Swell_Height
select count(*) from vos_arch2 where
Swell_Height < 0 or Swell_Height > 17.5;

prompt Arch2 : Swell_Period
select count(*) from vos_arch2 where
Swell_Period < 0 or Swell_Period > 25;

prompt Arch2 : Wave_Height
select count(*) from vos_arch2 where
Wave_Height < 0 or Wave_Height > 17.5;

prompt Arch2 : Wave_Period
select count(*) from vos_arch2 where
Wave_Period < 0 or Wave_Period > 20;

prompt Arch2 : Wind_Speed
select count(*) from vos_arch2 where
Wind_Speed < 0 or Wind_Speed > 40;

spool off


