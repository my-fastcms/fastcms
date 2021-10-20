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
package com.fastcms.core.response;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public final class Response {

    private Response() {}

    private final static String CODE = "code";
    private final static String MESSAGE = "message";
    private final static String DATA = "data";
    private final static int SUCCESS_CODE = 1;
    private final static int ERROR_CODE = 0;

    public static ResponseEntity success() {
        return new ResponseMap().ok();
    }

    public static ResponseEntity success(String message) {
        return new ResponseMap().ok(message);
    }

    public static ResponseEntity success(Object data) {
        return new ResponseMap().ok(data);
    }

    public static ResponseEntity success(Map<String, Object> resultMap) {
        return new ResponseMap().ok(resultMap);
    }

    public static ResponseEntity fail(String message) {
        return fail(ERROR_CODE, message);
    }

    public static ResponseEntity fail(int code, String message) {
        return new ResponseMap().fail(code, message);
    }

    static class ResponseMap extends HashMap {

        public ResponseEntity<Map<String, Object>> ok() {
            put(CODE, SUCCESS_CODE);
            put(DATA, "ok");
            return ResponseEntity.ok().body(this);
        }

        public ResponseEntity<Map<String, Object>> ok(Object data) {
            put(CODE, SUCCESS_CODE);
            put(DATA, data);
            return ResponseEntity.ok().body(this);
        }

        public ResponseEntity<Map<String, Object>> fail(String message) {
            return fail(ERROR_CODE, message);
        }

        public ResponseEntity<Map<String, Object>> fail(int code, String message) {
            put(CODE, code);
            put(MESSAGE, message);
            return ResponseEntity.ok().body(this);
        }

    }

}
