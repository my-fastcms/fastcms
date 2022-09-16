@echo off
rem Copyright (c) guangzhou xiaojudeng 2016-2022, wjun_java@163.com.
rem Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
rem you may not use this file except in compliance with the License.
rem You may obtain a copy of the License at
rem http://www.gnu.org/licenses/lgpl-3.0.txt
rem http://www.xjd2020.com
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

setlocal enabledelayedexpansion

set BASE_DIR=%~dp0

set SERVER=fastcms-server
set "FASTCMS_JVM_OPTS=-Xms512m -Xmx512m -Xmn256m"

rem set fastcms options
set "FASTCMS_OPTS=-jar -Dfile.encoding=utf-8 %BASE_DIR%\%SERVER%.jar --spring.profiles.active=prod"

set COMMAND="%JAVA%" %FASTCMS_JVM_OPTS% %FASTCMS_OPTS% fastcms.fastcms %*

rem start fastcms command
%COMMAND%
