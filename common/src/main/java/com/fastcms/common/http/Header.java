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

package com.fastcms.common.http;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.MapUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Header {

    public static String CLIENT_VERSION_HEADER = "Client-Version";
    public static String USER_AGENT_HEADER = "User-Agent";
    public static String REQUEST_SOURCE_HEADER = "Request-Source";
    public static String CONTENT_TYPE = "Content-Type";
    public static String CONTENT_LENGTH = "Content-Length";
    public static String ACCEPT_CHARSET = "Accept-Charset";
    public static String ACCEPT_ENCODING = "Accept-Encoding";
    public static String CONTENT_ENCODING = "Content-Encoding";
    public static String CONNECTION = "Requester";
    public static String REQUEST_ID = "RequestId";
    public static String REQUEST_MODULE = "Request-Module";

    public static final Header EMPTY = Header.newInstance();
    
    private final Map<String, String> header;
    
    private final Map<String, List<String>> originalResponseHeader;
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private static final String DEFAULT_ENCODING = "gzip";
    
    private Header() {
        header = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        originalResponseHeader = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
        addParam(CONTENT_TYPE, MediaType.APPLICATION_JSON);
        addParam(ACCEPT_CHARSET, DEFAULT_CHARSET);
        //addParam(HttpHeaderConsts.ACCEPT_ENCODING, DEFAULT_ENCODING);
    }
    
    public static Header newInstance() {
        return new Header();
    }

    /**
     * Add the key and value to the header.
     *
     * @param key the key
     * @param value the value
     * @return header
     */
    public Header addParam(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            header.put(key, value);
        }
        return this;
    }
    
    public Header setContentType(String contentType) {
        if (contentType == null) {
            contentType = MediaType.APPLICATION_JSON;
        }
        return addParam(CONTENT_TYPE, contentType);
    }
    
    public Header build() {
        return this;
    }
    
    public String getValue(String key) {
        return header.get(key);
    }
    
    public Map<String, String> getHeader() {
        return header;
    }
    
    public Iterator<Map.Entry<String, String>> iterator() {
        return header.entrySet().iterator();
    }
    
    /**
     * Transfer to KV part list. The odd index is key and the even index is value.
     *
     * @return KV string list
     */
    public List<String> toList() {
        List<String> list = new ArrayList<String>(header.size() * 2);
        Iterator<Map.Entry<String, String>> iterator = iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            list.add(entry.getKey());
            list.add(entry.getValue());
        }
        return list;
    }
    
    /**
     * Add all KV list to header. The odd index is key and the even index is value.
     *
     * @param list KV list
     * @return header
     */
    public Header addAll(List<String> list) {
        if ((list.size() & 1) != 0) {
            throw new IllegalArgumentException("list size must be a multiple of 2");
        }
        for (int i = 0; i < list.size(); ) {
            String key = list.get(i++);
            if (StringUtils.isNotEmpty(key)) {
                header.put(key, list.get(i++));
            }
        }
        return this;
    }
    
    /**
     * Add all parameters to header.
     *
     * @param params parameters
     */
    public void addAll(Map<String, String> params) {
        if (MapUtil.isNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                addParam(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * set original format response header.
     *
     * <p>Currently only corresponds to the response header of JDK.
     *
     * @param key original response header key
     * @param values original response header values
     */
    public void addOriginalResponseHeader(String key, List<String> values) {
        if (StringUtils.isNotEmpty(key)) {
            this.originalResponseHeader.put(key, values);
            addParam(key, values.get(0));
        }
    }
    
    /**
     * get original format response header.
     *
     * <p>Currently only corresponds to the response header of JDK.
     *
     * @return Map original response header
     */
    public Map<String, List<String>> getOriginalResponseHeader() {
        return this.originalResponseHeader;
    }
    
    public String getCharset() {
        String acceptCharset = getValue(ACCEPT_CHARSET);
        if (acceptCharset == null) {
            String contentType = getValue(CONTENT_TYPE);
            acceptCharset = StringUtils.isNotBlank(contentType) ? analysisCharset(contentType) : FastcmsConstants.ENCODE;
        }
        return acceptCharset;
    }
    
    private String analysisCharset(String contentType) {
        String[] values = contentType.split(";");
        String charset = FastcmsConstants.ENCODE;
        if (values.length == 0) {
            return charset;
        }
        for (String value : values) {
            if (value.startsWith("charset=")) {
                charset = value.substring("charset=".length());
            }
        }
        return charset;
    }
    
    public void clear() {
        header.clear();
        originalResponseHeader.clear();
    }
    
    @Override
    public String toString() {
        return "Header{" + "headerToMap=" + header + '}';
    }
}

