@echo off  
   
color 1f  
:menu  
echo -------------------------------------------------------  
echo +      1 - compile     2 - deploy      3 - version    +  
echo -------------------------------------------------------  
   
:input  
set /p input=please select:  
   
if "%input%"== "1" goto compile  
if "%input%"== "2" goto deploy  
if "%input%"== "3" goto version  
goto refresh  
   
:compile  
echo starting clean target directory and compile project...  
mvn clean package -Dmaven.test.skip=true &&pause  
exit  
   
:version  
echo this operation will change the project version, including the version of all the module projects  
set /p version=please input a new version:  
call mvn clean versions:set -DnewVersion=%version%  
del /s pom.xml.versionsBackup  
echo successfully changed project version, the new version is: %version%  
pause  
exit  
   
:deploy  
echo starting compile project and deploy jar file to maven repository...  
mvn deploy -Dmaven.test.skip=true &&pause  
exit  
   
:refresh  
echo invalid input &&pause &&cls &&goto menu