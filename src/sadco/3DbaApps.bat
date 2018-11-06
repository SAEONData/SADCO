cd ..

rem edm tables for sadco
rem --------------------
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=edm_client          ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=edm_instrument      ppack=sadco > ub.html

@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_data            ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_depth           ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_depth_notes     ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_depth_quality   ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_mooring         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_planam          ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=cur_watphy          ppack=sadco > ub.html

@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=wet_data            ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=wet_period          ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=wet_period_notes    ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=edm pprefix= ptable=wet_station         ppack=sadco > ub.html

rem sadco tables
rem ------------
rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=vos_main       ppack=sadco > ub.html

@rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=log_mrn_loads  ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=log_sessions   ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=log_users      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=log_vos_loads  ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=sad_user_dset  ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=    ptable=sad_users      ppack=sadco > ub.html

@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=constituent    ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=country        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=institutes     ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=inventory      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=investigators  ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=inv_stats      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=PLANAM         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=positions      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=samples        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=sedphy         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=sedpol1        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=sedpol2        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=sfri_grid      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=station        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=station_stats  ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=survey         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=survey_constituent ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=survey_notes   ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=tisanimal      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=tisanpart      ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=tisphy         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=tispol1        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=tispol2        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watchem1       ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watchem2       ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watchl         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watcurrents    ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watnut         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watphy         ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watpol1        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=watpol2        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=weather        ppack=sadco > ub.html
@rem java DbaApp pscreen=genb powner=sadco pprefix=Mrn ptable=zones_covered  ppack=sadco > ub.html
pause
