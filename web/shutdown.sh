#!/bin/bash

# Copyright (c) guangzhou xiaojudeng 2016-2012, wjun_java@163.com.
# Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# http://www.gnu.org/licenses/lgpl-3.0.txt
# http://www.xjd2020.com
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
cd `dirname $0`/..
target_dir=`pwd`

pid=`ps ax | grep -i 'fastcms.fastcms' | grep ${target_dir} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No fastcmsServer running."
        exit -1;
fi

echo "The fastcmsServer(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to fastcmsServer(${pid}) OK"