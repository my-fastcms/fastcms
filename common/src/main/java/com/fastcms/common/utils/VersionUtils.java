/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fastcms.common.utils;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Objects;
import java.util.Properties;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class VersionUtils {
    
    public static String version;
    
    private static String clientVersion;
    
    /**
     * current version.
     */
    public static final String VERSION_PLACEHOLDER = "${project.version}";
    
    private static final String FASTCMS_VERSION_FILE = "fastcms-version.txt";
    
    static {
        try (InputStream in = VersionUtils.class.getClassLoader().getResourceAsStream(FASTCMS_VERSION_FILE)) {
            Properties props = new Properties();
            props.load(in);
            String val = props.getProperty("version");
            if (val != null && !VERSION_PLACEHOLDER.equals(val)) {
                version = val;
                clientVersion = "Fastcms:v" + VersionUtils.version;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static final Comparator<String> STRING_COMPARATOR = String::compareTo;
    
    /**
     * compare two version who is latest.
     *
     * @param versionA version A, like x.y.z(-beta)
     * @param versionB version B, like x.y.z(-beta)
     * @return compare result
     */
    public static int compareVersion(final String versionA, final String versionB) {
        final String[] sA = versionA.split("\\.");
        final String[] sB = versionB.split("\\.");
        int expectSize = 3;
        if (sA.length != expectSize || sB.length != expectSize) {
            throw new IllegalArgumentException("version must be like x.y.z(-beta)");
        }
        int first = Objects.compare(sA[0], sB[0], STRING_COMPARATOR);
        if (first != 0) {
            return first;
        }
        int second = Objects.compare(sA[1], sB[1], STRING_COMPARATOR);
        if (second != 0) {
            return second;
        }
        return Objects.compare(sA[2].split("-")[0], sB[2].split("-")[0], STRING_COMPARATOR);
    }
    
    public static String getFullClientVersion() {
        return clientVersion;
    }
}
