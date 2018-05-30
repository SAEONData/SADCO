cd ..

rem menu tables
rem -----------
java DbaApp pscreen=genb powner=dba2 pprefix= ptable=dmn_menu_items   ppack=menusp > ub.html
java DbaApp pscreen=genb powner=dba2 pprefix= ptable=dmn_parameters   ppack=menusp > ub.html
java DbaApp pscreen=genb powner=dba2 pprefix= ptable=dmn_param_select ppack=menusp > ub.html
java DbaApp pscreen=genb powner=dba2 pprefix= ptable=dmn_script_type  ppack=menusp > ub.html

pause
