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

package com.fastcms.web.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.entity.UserAmountStatement;
import com.fastcms.service.IUserAmountPayoutService;
import com.fastcms.service.IUserAmountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 用户余额
 * @author： wjun_java@163.com
 * @date： 2022/4/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/user/amount")
public class UserAmountApi {

    @Autowired
    private IUserAmountPayoutService userAmountPayoutService;

    @Autowired
    private IUserAmountStatementService userAmountStatementService;

    /**
     * 用户提现
     * @param amount
     * @return
     * @throws FastcmsException
     */
    @PostMapping("cashout")
    public Object cashOut(@RequestParam("amount") BigDecimal amount) throws FastcmsException {
        return RestResultUtils.success(userAmountPayoutService.cashOut(AuthUtils.getUserId(), amount));
    }

    /**
     * 用户提现列表
     * @return
     */
    @GetMapping("cashout/list")
    public Object getUserCashOutList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uas.user_id", AuthUtils.getUserId());
        queryWrapper.orderByDesc("uas.created");
        return RestResultUtils.success(userAmountPayoutService.getUserCashOutList(queryWrapper));
    }

    /**
     * 用户收入列表
     * @param page
     * @return
     */
    @GetMapping("income/list")
    public Object getUserAmountIncomeList(PageModel page) {
        return RestResultUtils.success(userAmountStatementService.pageUserAmountStatement(page.toPage(), AuthUtils.getUserId(), UserAmountStatement.AMOUNT_ACTION_ADD, null));
    }

}
