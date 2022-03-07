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
package com.fastcms.core.exception;

import com.fastcms.common.exception.AccessException;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.core.monitor.MetricsMonitor;
import com.fastcms.core.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@ControllerAdvice
public class FastcmsGlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(FastcmsGlobalExceptionHandler.class);

    /**
     * For IllegalArgumentException, we are returning void with status code as 400, so our error-page will be used in
     * this case.
     *
     * @throws IllegalArgumentException IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) throws IOException {
        MetricsMonitor.getIllegalArgumentException().increment();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionUtil.getAllExceptionMsg(ex));
    }

    /**
     * For FastcmsException.
     *
     * @throws FastcmsException FastcmsException.
     */
    @ExceptionHandler(FastcmsException.class)
    public ResponseEntity<String> handleFastcmsException(FastcmsException ex) throws IOException {
        MetricsMonitor.getFastcmsException().increment();
        return ResponseEntity.status(ex.getErrCode()).body(ExceptionUtil.getAllExceptionMsg(ex));
    }

    /**
     * For DataAccessException.
     *
     * @throws DataAccessException DataAccessException.
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) throws DataAccessException {
        MetricsMonitor.getDbException().increment();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.getAllExceptionMsg(ex));
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<String> handleAccessException(AccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionUtil.getAllExceptionMsg(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("CONSOLE", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.getAllExceptionMsg(e));
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<String> handleRunTimeException(BadSqlGrammarException e) {
        log.error("CONSOLE", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.getAllExceptionMsg(e));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> mismatchErrorHandler(MethodArgumentTypeMismatchException e) {
        log.error("参数转换失败，方法：" + Objects.requireNonNull(e.getParameter().getMethod()).getName() + ",参数：" + e.getName() + "，信息：" + e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.getAllExceptionMsg(e));
    }

}
