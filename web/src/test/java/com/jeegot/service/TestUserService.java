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
package com.fastcms.service;

import com.fastcms.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/28
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@SpringBootTest
@Slf4j
public class TestUserService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRoleService roleService;

    @Test
    public void testBatchInsert() {

        List<User> userList = new ArrayList<>();

        for(int i=0; i<10; i++){
            User user = new User();
            user.setUserName("user" + i);
            user.setNickName("nick" + i);
            user.setCreated(LocalDateTime.now());
            user.setUpdated(LocalDateTime.now());
            userList.add(user);
        }

        userService.saveBatch(userList, userList.size());

    }

    @Test
    public void testGetPermissionTreeNodeList() {
        List<IPermissionService.PermissionTreeNode> treeNodeList = permissionService.getPermissionByRoleId(1l);
        log.info("treeNodeList:" + treeNodeList.size());

        treeNodeList = permissionService.getPermissionByRoleId(1l);
        log.info("treeNodeList in cache:" + treeNodeList.size());
    }

    @Test
    public void testSaveRolePermission() {
        List<Long> list = new ArrayList<>();
        list.add(1l);
        list.add(2l);
        list.add(3l);
        list.add(4l);
        list.add(5l);
        roleService.saveRolePermission(2l, list);
    }

}
