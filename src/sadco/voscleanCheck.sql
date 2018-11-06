select count(*) from vos_main where
LATITUDE between 35.0 and 35.00099 and LONGITUDE between 22.2 and 22.20099 ;

select count(*) from vos_main where
LATITUDE between 35.0 and 35.00009 and LONGITUDE between 22.2 and 22.20009;

select count(*) from vos_main where
LATITUDE between 35.0 and 35.00004 and LONGITUDE between 22.2 and 22.20004;


select count(*) from vos_main where
LATITUDE = 35.0 and LONGITUDE = 22.2;

select count(*) from vos_main where
LATITUDE between -10 and -9;

select count(*) from vos_main where
LATITUDE between 37.0 and 37.09999 and LONGITUDE between 0.1 and 0.99999

select count(*) from vos_main where
LATITUDE between 37 and 38 and
(longitude < 0 or longitude >= 50);


select count(*) from vos_main where
LATITUDE >= -10 and latitude < -9;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.90001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.80001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.70001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.60001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.50001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.40001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.30001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.20001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.10001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.00001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.90001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.80001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.70001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.60001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.50001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.40001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.30001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.20001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.10001;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.00001;

select latitude, longitude, date_time from vos_main where
LATITUDE between -10.0 and -9.90001 order by date_time;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.90001 and
longitude > 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -9.90001 and
longitude < 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -0.00001 and LONGITUDE  between 0.0 and 50;

select count(*) from vos_main where
LATITUDE between -10.0 and -0.00001;

select count(*) from vos_main where
LATITUDE between -10.0 and -7.00001 and LONGITUDE  between 0.0 and 50;

delete from vos_main where load_id = 401;
delete from vos_main where load_id = 402;
delete from vos_main where load_id = 403;
delete from vos_main where load_id = 404;
delete from vos_main where load_id = 405;
delete from vos_main where load_id = 406;
delete from vos_main where load_id = 407;
delete from vos_main where load_id = 408;
delete from vos_main where load_id = 409;
delete from vos_main where load_id = 410;
delete from vos_arch where load_id = 401;
delete from vos_arch where load_id = 402;
delete from vos_arch where load_id = 403;
delete from vos_arch where load_id = 404;
delete from vos_arch where load_id = 405;
delete from vos_arch where load_id = 406;
delete from vos_arch where load_id = 407;
delete from vos_arch where load_id = 408;
delete from vos_arch where load_id = 409;
delete from vos_arch where load_id = 410;
delete from vos_reject where load_id = 401;
delete from vos_reject where load_id = 402;
delete from vos_reject where load_id = 403;
delete from vos_reject where load_id = 404;
delete from vos_reject where load_id = 405;
delete from vos_reject where load_id = 406;
delete from vos_reject where load_id = 407;
delete from vos_reject where load_id = 408;
delete from vos_reject where load_id = 409;
delete from vos_reject where load_id = 410;

select count(*) from vos_main where longitude >= 50;
select count(*) from vos_main where longitude < 0;

select count(*) from vos_main where
LATITUDE >= 10 and LATITUDE < 11;

select count(*) from vos_main where
LATITUDE between 10 and 10.99999;

select count(*) from vos_main where
LATITUDE between 11 and 11.99999;


select count(*) from vos_main where
atmospheric_pressure < 950 and
LATITUDE between 10 and 10.99999;

update VOS_MAIN set ATMOSPHERIC_PRESSURE=null where
DATE_TIME = to_date('1999-11-06 12:00:00','rrrr-mm-dd hh24:mi:ss') and
LATITUDE = 10.0 and LONGITUDE = 0.7 and CALLSIGN = 'KGXA';

select ATMOSPHERIC_PRESSURE from VOS_MAIN where
DATE_TIME = to_date('1999-11-06 12:00:00','rrrr-mm-dd hh24:mi:ss') and
LATITUDE = 10.0 and LONGITUDE = 0.7 and CALLSIGN = 'KGXA';

select count(*) from vos_main2 where
LATITUDE between -5 and -4.00001;

select count(*) from vos_main2 where
LATITUDE between 0 and 0.99999;

vosarch2 -9
select count(*) from vos_arch2 where
LATITUDE between -9.0 and -8.90001 and LONGITUDE between -25.0 and -24.90001;
5911 recs

select count(*) from vos_arch2 where
LATITUDE  = -9.0 and LONGITUDE = -25.0;
5911 recs

select count(*) from vos_arch2 where
LATITUDE between -9.0 and -8.90001 and LONGITUDE between -24.99999 and -24.90001;
0 recs


vosarch2 -7
select count(*) from vos_arch2 where
LATITUDE between -7.0 and -6.90001 and LONGITUDE between -28.0 and -27.90001;
7390 recs

select count(*) from vos_arch2 where
LATITUDE = -7.0  and LONGITUDE = -28.0;
7390 recs

select count(*) from vos_arch2 where
LATITUDE between -7.0 and -6.90001 and LONGITUDE between -26.0 and -25.00001;
6020 recs

select count(*) from vos_arch2 where
LATITUDE = -7.0 and LONGITUDE = -26.0;
6019 recs



vosarch2 -6
select count(*) from vos_arch2 where
LATITUDE between -6.0 and -5.90001 and LONGITUDE between -28.0 and -27.90001;
6507 recs

select count(*) from vos_arch2 where
LATITUDE = -6.0 and LONGITUDE = -28.0;
6507 recs



vosarch2 -5
select count(*) from vos_arch2 where
LATITUDE between -5.0 and -4.90001 and LONGITUDE between -29.0 and -28.90001;
6102 recs

select count(*) from vos_arch2 where
LATITUDE = -5.0 and LONGITUDE = -29.0;
6102 recs



vosarch2 -4
select count(*) from vos_arch2 where
LATITUDE between -4.0 and -3.90001 and LONGITUDE between -29.0 and -28.90001;
6200 recs

select count(*) from vos_arch2 where
LATITUDE = -4.0 and LONGITUDE = -29.0;
6200 recs



vosarch2 -3
select count(*) from vos_arch2 where
LATITUDE between -3.0 and -2.90001 and LONGITUDE between -29.0 and -28.90001;
7372 recs

select count(*) from vos_arch2 where
LATITUDE = -3.0 and LONGITUDE = -29.0;
7372 recs

select count(*) from vos_arch2 where
LATITUDE between -3.0 and -2.90001 and LONGITUDE between -28.09999 and -28.0;
5894 recs

select count(*) from vos_arch2 where
LATITUDE = -3.0 and LONGITUDE = -28.0;
5894 recs


vosarch2 -2
select count(*) from vos_arch2 where
LATITUDE between -2.0 and -1.90001 and LONGITUDE between -30.0 and -29.90001;
6621 recs

select count(*) from vos_arch2 where
LATITUDE = -2.0 and LONGITUDE = -30.0;
6621 recs

select count(*) from vos_arch2 where
LATITUDE between -2.0 and -1.90001 and LONGITUDE between -29.09999 and -29.0;
6974 recs

select count(*) from vos_arch2 where
LATITUDE = -2.0 and LONGITUDE = -29;
6974 recs



vosarch2 -1
select count(*) from vos_arch2 where
LATITUDE between -1.0 and -0.90001 and LONGITUDE between -30.0 and -29.90001;
6462 recs

select count(*) from vos_arch2 where
LATITUDE = -1.0 and LONGITUDE = -30.0;
6462 recs

select count(*) from vos_arch2 where
LATITUDE between -1.0 and -0.90001 and LONGITUDE between -29.09999 and -29.0;
6774 recs

select count(*) from vos_arch2 where
LATITUDE = -1.0 and LONGITUDE = -29.0;
6774 recs


select count(*) from vos_arch where longitude < -30;
  COUNT(*)
----------
     39553
select count(*) from vos_arch where longitude >= 70;
  COUNT(*)
----------
        46
select count(*) from vos_arch2 where longitude < -30;
  COUNT(*)
----------
     12477
select count(*) from vos_arch2 where longitude >= 70;
  COUNT(*)
----------
     51825

select min(longitude), max(longitude) from vos_main;
MIN(LONGITUDE) MAX(LONGITUDE)
-------------- --------------
             0           49.9
select min(longitude), max(longitude) from vos_main2;
MIN(LONGITUDE) MAX(LONGITUDE)
-------------- --------------
             0           49.9
select min(longitude), max(longitude) from vos_arch;
MIN(LONGITUDE) MAX(LONGITUDE)
-------------- --------------
           -31             70
select min(longitude), max(longitude) from vos_arch2;
MIN(LONGITUDE) MAX(LONGITUDE)
-------------- --------------
           -35             75
delete from vos_arch where longitude < -30;
39553 rows deleted
delete from vos_arch where longitude >= 70;
46 rows deleted
delete from vos_arch2 where longitude < -30;
12477 rows deleted
delete from vos_arch2 where longitude >= 70;
51825 rows deleted
