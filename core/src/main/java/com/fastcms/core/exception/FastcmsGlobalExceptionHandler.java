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

import com.egzosn.pay.common.exception.PayErrorException;
import com.fastcms.common.exception.AccessException;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.monitor.MetricsMonitor;
import com.fastcms.core.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

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
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) throws IOException {
        MetricsMonitor.getIllegalArgumentException().increment();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionUtil.getAllExceptionMsg(e));
    }

    /**
     * For DataAccessException.
     *
     * @throws DataAccessException DataAccessException.
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) throws DataAccessException {
        MetricsMonitor.getDbException().increment();
        log.error("rootFile", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fastcms Database sql error");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> mismatchErrorHandler(MethodArgumentTypeMismatchException e) {
        log.error("CONSOLE", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtil.getAllExceptionMsg(e));
    }

    /**
     * For FastcmsException.
     *
     * @throws FastcmsException FastcmsException.
     */
    @ExceptionHandler(FastcmsException.class)
    public ResponseEntity<Object> handleFastcmsException(FastcmsException e) {
        MetricsMonitor.getFastcmsException().increment();
        return ResponseEntity.status(HttpStatus.OK).body(RestResultUtils.failed(e.getErrMsg()));
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<Object> handleAccessException(AccessException e) {
        return ResponseEntity.status(HttpStatus.OK).body(RestResultUtils.failed(e.getMessage()));
    }

    @ExceptionHandler(PayErrorException.class)
    public ResponseEntity<Object> handlePayErrorException(PayErrorException e) {
        return ResponseEntity.status(HttpStatus.OK).body(RestResultUtils.failed(e.getMessage()));
    }

}
