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
import org.apache.commons.lang.StringUtils;

/**
 * @author： wjun_java@163.com
 * @date： 2022/03/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class MediaType {
    
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    
    public static final String APPLICATION_SVG_XML = "application/svg+xml";
    
    public static final String APPLICATION_XHTML_XML = "application/xhtml+xml";
    
    public static final String APPLICATION_XML = "application/xml;charset=UTF-8";
    
    public static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    
    public static final String MULTIPART_FORM_DATA = "multipart/form-data;charset=UTF-8";
    
    public static final String TEXT_HTML = "text/html;charset=UTF-8";
    
    public static final String TEXT_PLAIN = "text/plain;charset=UTF-8";
    
    private MediaType(String type, String charset) {
        this.type = type;
        this.charset = charset;
    }
    
    /**
     * content type.
     */
    private final String type;
    
    /**
     * content type charset.
     */
    private final String charset;
    
    /**
     * Parse the given String contentType into a {@code MediaType} object.
     *
     * @param contentType mediaType
     * @return MediaType
     */
    public static MediaType valueOf(String contentType) {
        if (StringUtils.isEmpty(contentType)) {
            throw new IllegalArgumentException("MediaType must not be empty");
        }
        String[] values = contentType.split(";");
        String charset = FastcmsConstants.ENCODE;
        for (String value : values) {
            if (value.startsWith("charset=")) {
                charset = value.substring("charset=".length());
            }
        }
        return new MediaType(values[0], charset);
    }
    
    /**
     * Use the given contentType and charset to assemble into a {@code MediaType} object.
     *
     * @param contentType contentType
     * @param charset charset
     * @return MediaType
     */
    public static MediaType valueOf(String contentType, String charset) {
        if (StringUtils.isEmpty(contentType)) {
            throw new IllegalArgumentException("MediaType must not be empty");
        }
        String[] values = contentType.split(";");
        return new MediaType(values[0], StringUtils.isEmpty(charset) ? FastcmsConstants.ENCODE : charset);
    }
    
    public String getType() {
        return type;
    }
    
    public String getCharset() {
        return charset;
    }
    
    @Override
    public String toString() {
        return type + ";charset=" + charset;
    }
}
