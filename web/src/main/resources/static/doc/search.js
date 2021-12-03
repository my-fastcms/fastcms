let api = [];
const apiDocListSize = 7
api.push({
    name: 'default',
    order: '1',
    list: []
})
api.push({
    name: '基础-admin',
    order: '2',
    list: []
})
api[1].list.push({
    alias: 'AdminController',
    order: '1',
    link: '登录授权',
    desc: '登录授权',
    list: []
})
api[1].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/login',
    desc: '登录',
});
api[1].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/captcha',
    desc: '验证码',
});
api[1].list.push({
    alias: 'AttachmentController',
    order: '2',
    link: '附件管理',
    desc: '附件管理',
    list: []
})
api[1].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/list',
    desc: '附件列表',
});
api[1].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/upload',
    desc: '上传附件',
});
api[1].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/update/{attachId}',
    desc: '修改附件',
});
api[1].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/get/{attachId}',
    desc: '附件明细',
});
api[1].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/delete/{attachId}',
    desc: '删除附件',
});
api[1].list[1].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/upload/ckeditor',
    desc: '上传附件(编辑器)',
});
api[1].list[1].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/upload/cKeditor/browse',
    desc: '上传附件(浏览)',
});
api[1].list.push({
    alias: 'ConfigController',
    order: '3',
    link: '配置',
    desc: '配置',
    list: []
})
api[1].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/config/save',
    desc: '保存配置',
});
api[1].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/config/list',
    desc: '获取配置',
});
api[1].list.push({
    alias: 'MenuController',
    order: '4',
    link: '菜单管理',
    desc: '菜单管理',
    list: []
})
api[1].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/menu/list',
    desc: '菜单列表',
});
api[1].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/menu/save',
    desc: '保存菜单',
});
api[1].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/menu/delete/{menuId}',
    desc: '删除菜单',
});
api[1].list.push({
    alias: 'PluginController',
    order: '5',
    link: '插件管理',
    desc: '插件管理',
    list: []
})
api[1].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/plugin/install',
    desc: '上传插件',
});
api[1].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/plugin/unInstall',
    desc: '卸载插件',
});
api[1].list.push({
    alias: 'RoleController',
    order: '6',
    link: '角色管理',
    desc: '角色管理',
    list: []
})
api[1].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/list',
    desc: '角色列表',
});
api[1].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/save',
    desc: '保存角色',
});
api[1].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/delete/{roleId}',
    desc: '删除角色',
});
api[1].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/list/select',
    desc: '获取角色列表，不分页',
});
api[1].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/{roleId}/permissions',
    desc: '获取角色权限列表',
});
api[1].list[5].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/{roleId}/permissions/save',
    desc: '保存角色权限',
});
api[1].list.push({
    alias: 'UserController',
    order: '7',
    link: '用户管理',
    desc: '用户管理',
    list: []
})
api[1].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/list',
    desc: '用户列表',
});
api[1].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/menus',
    desc: '获取用户菜单',
});
api[1].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/save',
    desc: '保存用户信息',
});
api[1].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/{userId}/get',
    desc: '获取用户详细信息',
});
api[1].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/delete/{userId}',
    desc: '编辑用户信息',
});
api[1].list[6].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/{userId}/roles/save/',
    desc: '分配角色',
});
api[1].list[6].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/{userId}/tags/save',
    desc: '分配标签',
});
api.push({
    name: '基础-api',
    order: '3',
    list: []
})
api[2].list.push({
    alias: 'PaymentCallbackController',
    order: '1',
    link: '支付回调',
    desc: '支付回调',
    list: []
})
api[2].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/payback/{platform}',
    desc: '',
});
api[2].list.push({
    alias: 'UploadApi',
    order: '2',
    link: '文件上传',
    desc: '文件上传',
    list: []
})
api[2].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/upload/',
    desc: '上传',
});
api[2].list.push({
    alias: 'UserApi',
    order: '3',
    link: '用户',
    desc: '用户',
    list: []
})
api[2].list.push({
    alias: 'WechatMiniUserApi',
    order: '4',
    link: '微信小程序用户',
    desc: '微信小程序用户',
    list: []
})
api[2].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/wechat/user/code2session',
    desc: 'code2session',
});
api[2].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/wechat/user/login',
    desc: '登录',
});
api[2].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/wechat/user/phone',
    desc: '取用户手机号码',
});
api[2].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/wechat/user/login/phone',
    desc: '手机号码授权登录',
});
api.push({
    name: '基础-ucenter',
    order: '4',
    list: []
})
api[3].list.push({
    alias: 'UCenterUserController',
    order: '1',
    link: '用户管理',
    desc: '用户管理',
    list: []
})
api[3].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/save',
    desc: '保存用户信息',
});
api[3].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/password/update',
    desc: '修改密码',
});
api[3].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/avatar/save',
    desc: '保存头像',
});
api[3].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/register',
    desc: '用户注册',
});
api.push({
    name: 'CMS-admin',
    order: '5',
    list: []
})
api[4].list.push({
    alias: 'ArticleController',
    order: '1',
    link: '文章管理',
    desc: '文章管理',
    list: []
})
api[4].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/list',
    desc: '文章列表',
});
api[4].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/save',
    desc: '保存文章',
});
api[4].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/category/save',
    desc: '保存分类',
});
api[4].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/get/{articleId}',
    desc: '文章详情',
});
api[4].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/delete/{articleId}',
    desc: '删除文章',
});
api[4].list[0].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/category/list',
    desc: '分类列表',
});
api[4].list[0].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/category/delete/{categoryId}',
    desc: '删除分类',
});
api[4].list[0].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/comment/save',
    desc: '保存评论',
});
api[4].list[0].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/comment/delete/{commentId}',
    desc: '删除评论',
});
api[4].list.push({
    alias: 'PageController',
    order: '2',
    link: '页面管理',
    desc: '页面管理',
    list: []
})
api[4].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/list',
    desc: '页面列表',
});
api[4].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/save',
    desc: '保存页面',
});
api[4].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/comment/save',
    desc: '保存评论',
});
api[4].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/comment/delete/{commentId}',
    desc: '删除评论',
});
api[4].list.push({
    alias: 'TemplateController',
    order: '3',
    link: '模板管理',
    desc: '模板管理',
    list: []
})
api[4].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/list',
    desc: '模板列表',
});
api[4].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/current',
    desc: '获取当前模板',
});
api[4].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/install',
    desc: '安装模板',
});
api[4].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/unInstall',
    desc: '卸载模板',
});
api[4].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/edit',
    desc: '编辑模板',
});
api[4].list[2].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/enable/{templateId}',
    desc: '激活模板',
});
api[4].list[2].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/save',
    desc: '保存模板',
});
api[4].list[2].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/upload',
    desc: '上传模板文件',
});
api[4].list[2].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/file/delete/{filePath}',
    desc: '删除模板文件',
});
api[4].list[2].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/list',
    desc: '菜单列表',
});
api[4].list[2].list.push({
    order: '11',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/get/{menuId}',
    desc: '菜单信息',
});
api[4].list[2].list.push({
    order: '12',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/save',
    desc: '保存菜单',
});
api[4].list[2].list.push({
    order: '13',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/delete/{menuId}',
    desc: '删除菜单',
});
api.push({
    name: 'CMS-api',
    order: '6',
    list: []
})
api[5].list.push({
    alias: 'ArticleApi',
    order: '1',
    link: '文章接口',
    desc: '文章接口',
    list: []
})
api[5].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/list',
    desc: '文章列表',
});
api[5].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/detail/{articleId}',
    desc: '文章详情',
});
api[5].list.push({
    alias: 'ArticleCategoryApi',
    order: '2',
    link: '文章分类',
    desc: '文章分类',
    list: []
})
api[5].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/category/list',
    desc: '分类列表',
});
api[5].list.push({
    alias: 'ArticleCommentApi',
    order: '3',
    link: '文章评论',
    desc: '文章评论',
    list: []
})
api[5].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/comment/list',
    desc: '评论列表',
});
api[5].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/comment/save',
    desc: '保存评论',
});
api.push({
    name: 'CMS-ucenter',
    order: '7',
    list: []
})
api[6].list.push({
    alias: 'UCenterArticleController',
    order: '1',
    link: '文章管理',
    desc: '文章管理',
    list: []
})
api[6].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/article/list',
    desc: '文章列表',
});
api[6].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/article/save',
    desc: '保存评论',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchGroup = [];
        for (let i = 0; i < api.length; i++) {

            let apiGroup = api[i];

            let searchArr = [];
            for (let i = 0; i < apiGroup.list.length; i++) {
                let apiData = apiGroup.list[i];
                const desc = apiData.desc;
                if (desc.indexOf(searchValue) > -1) {
                    searchArr.push({
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: apiData.list
                    });
                } else {
                    let methodList = apiData.list || [];
                    let methodListTemp = [];
                    for (let j = 0; j < methodList.length; j++) {
                        const methodData = methodList[j];
                        const methodDesc = methodData.desc;
                        if (methodDesc.indexOf(searchValue) > -1) {
                            methodListTemp.push(methodData);
                            break;
                        }
                    }
                    if (methodListTemp.length > 0) {
                        const data = {
                            order: apiData.order,
                            desc: apiData.desc,
                            link: apiData.link,
                            list: methodListTemp
                        };
                        searchArr.push(data);
                    }
                }
            }
            if (apiGroup.name.indexOf(searchValue) > -1) {
                searchGroup.push({
                    name: apiGroup.name,
                    order: apiGroup.order,
                    list: searchArr
                });
                continue;
            }
            if (searchArr.length === 0) {
                continue;
            }
            searchGroup.push({
                name: apiGroup.name,
                order: apiGroup.order,
                list: searchArr
            });
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchGroup,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiGroups, liClass, display) {
    let html = "";
    let doc;
    if (apiGroups.length > 0) {
         if (apiDocListSize == 1) {
            let apiData = apiGroups[0].list;
            for (let j = 0; j < apiData.length; j++) {
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                html += '<ul class="sectlevel2" style="'+display+'">';
                doc = apiData[j].list;
                for (let m = 0; m < doc.length; m++) {
                    let spanString;
                    if (doc[m].deprecated == 'true') {
                        spanString='<span class="line-through">';
                    } else {
                        spanString='<span>';
                    }
                    html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].href + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                }
                html += '</ul>';
                html += '</li>';
            }
        } else {
            for (let i = 0; i < apiGroups.length; i++) {
                let apiGroup = apiGroups[i];
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_' + apiGroup.name + '">' + apiGroup.order + '.&nbsp;' + apiGroup.name + '</a>';
                html += '<ul class="sectlevel1">';

                let apiData = apiGroup.list;
                for (let j = 0; j < apiData.length; j++) {
                    html += '<li class="'+liClass+'">';
                    html += '<a class="dd" href="#_'+apiGroup.order+'_'+ apiData[j].order + '_'+ apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated == 'true') {
                           spanString='<span class="line-through">';
                       } else {
                           spanString='<span>';
                       }
                       html += '<li><a href="#_'+apiGroup.order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">'+apiGroup.order+'.' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                   }
                    html += '</ul>';
                    html += '</li>';
                }

                html += '</ul>';
                html += '</li>';
            }
        }
    }
    return html;
}