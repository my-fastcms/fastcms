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
if not exist "%JAVA_HOME%\bin\jps.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1

setlocal

set "PATH=%JAVA_HOME%\bin;%PATH%"

echo killing fastcms server

for /f "tokens=1" %%i in ('jps -m ^| find "fastcms.fastcms"') do ( taskkill /F /PID %%i )

echo Done!
