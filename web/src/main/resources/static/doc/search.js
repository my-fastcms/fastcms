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
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/doUpload',
    desc: '上传附件',
});
api[1].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/detail',
    desc: '附件明细',
});
api[1].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/doDelete',
    desc: '删除附件',
});
api[1].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/browse',
    desc: '',
});
api[1].list[1].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/doUploadOfCKEditor',
    desc: '',
});
api[1].list[1].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/attachment/doUploadOfCKEditorBrowse',
    desc: '',
});
api[1].list.push({
    alias: 'ConfigController',
    order: '3',
    link: '',
    desc: '',
    list: []
})
api[1].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/config/doSave',
    desc: '',
});
api[1].list.push({
    alias: 'MenuController',
    order: '4',
    link: '',
    desc: '',
    list: []
})
api[1].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/menu/list',
    desc: '',
});
api[1].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/menu/save',
    desc: '',
});
api[1].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/menu/del',
    desc: '',
});
api[1].list.push({
    alias: 'PluginController',
    order: '5',
    link: '',
    desc: '',
    list: []
})
api[1].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/plugin/doInstall',
    desc: '',
});
api[1].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/plugin/doUnInstall',
    desc: '',
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
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/getPermissionList',
    desc: '获取角色权限列表',
});
api[1].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/role/saveRolePermission',
    desc: '保存角色权限',
});
api[1].list.push({
    alias: 'SetupController',
    order: '7',
    link: '',
    desc: '',
    list: []
})
api[1].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/setup/wechatMini',
    desc: '',
});
api[1].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/setup/pay',
    desc: '',
});
api[1].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/setup/icons',
    desc: '',
});
api[1].list.push({
    alias: 'UserController',
    order: '8',
    link: '用户管理',
    desc: '用户管理',
    list: []
})
api[1].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/list',
    desc: '用户列表',
});
api[1].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/getMenus',
    desc: '获取用户菜单',
});
api[1].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/doSave',
    desc: '保存用户信息',
});
api[1].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/doEdit',
    desc: '编辑用户信息',
});
api[1].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/doEditRole',
    desc: '',
});
api[1].list[7].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/tag/doSave',
    desc: '',
});
api[1].list[7].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/user/doEditTag',
    desc: '',
});
api.push({
    name: '基础-api',
    order: '3',
    list: []
})
api[2].list.push({
    alias: 'PaymentCallbackController',
    order: '1',
    link: '',
    desc: '',
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
    link: '',
    desc: '',
    list: []
})
api[2].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/upload/doUpload',
    desc: '',
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
    link: '微信小程序用户授权登录',
    desc: '微信小程序用户授权登录',
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
    url: 'http://127.0.0.1:8080/fastcms/api/api/wechat/user/getUserPhone',
    desc: '取用户手机号码',
});
api[2].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/wechat/user/loginByPhone',
    desc: '手机号码授权登录',
});
api.push({
    name: '基础-ucenter',
    order: '4',
    list: []
})
api[3].list.push({
    alias: 'UCenterController',
    order: '1',
    link: '',
    desc: '',
    list: []
})
api[3].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/register',
    desc: '',
});
api[3].list.push({
    alias: 'UCenterUserController',
    order: '2',
    link: '',
    desc: '',
    list: []
})
api[3].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/doSave',
    desc: '',
});
api[3].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/doEditPwd',
    desc: '',
});
api[3].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/user/doSaveAvatar',
    desc: '',
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
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/doSave',
    desc: '',
});
api[4].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/category/doSave',
    desc: '',
});
api[4].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/category/doDelete',
    desc: '',
});
api[4].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/comment/doSave',
    desc: '',
});
api[4].list[0].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/article/comment/doDelete',
    desc: '',
});
api[4].list.push({
    alias: 'PageController',
    order: '2',
    link: '',
    desc: '',
    list: []
})
api[4].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/list',
    desc: '',
});
api[4].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/doSave',
    desc: '',
});
api[4].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/comment/doSave',
    desc: '',
});
api[4].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/page/comment/doDelete',
    desc: '',
});
api[4].list.push({
    alias: 'TemplateController',
    order: '3',
    link: '模板管理接口',
    desc: '模板管理接口',
    list: []
})
api[4].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/list',
    desc: '',
});
api[4].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/install',
    desc: '',
});
api[4].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/doInstall',
    desc: '',
});
api[4].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/doUnInstall',
    desc: '',
});
api[4].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/edit',
    desc: '',
});
api[4].list[2].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/doEnable',
    desc: '',
});
api[4].list[2].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/doEditSave',
    desc: '',
});
api[4].list[2].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/doUpload',
    desc: '',
});
api[4].list[2].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/doDelFile',
    desc: '',
});
api[4].list[2].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/list',
    desc: '',
});
api[4].list[2].list.push({
    order: '11',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/edit',
    desc: '',
});
api[4].list[2].list.push({
    order: '12',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/doSave',
    desc: '',
});
api[4].list[2].list.push({
    order: '13',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/menu/doDelete',
    desc: '',
});
api[4].list[2].list.push({
    order: '14',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/admin/template/setting/doSave',
    desc: '',
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
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/detail',
    desc: '文章详情',
});
api[5].list.push({
    alias: 'ArticleCategoryApi',
    order: '2',
    link: '',
    desc: '',
    list: []
})
api[5].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/category/list',
    desc: '',
});
api[5].list.push({
    alias: 'ArticleCommentApi',
    order: '3',
    link: '',
    desc: '',
    list: []
})
api[5].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/comment/list',
    desc: '',
});
api[5].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/api/article/comment/doSaveComment',
    desc: '',
});
api.push({
    name: 'CMS-ucenter',
    order: '7',
    list: []
})
api[6].list.push({
    alias: 'UCenterArticleController',
    order: '1',
    link: '文章接口',
    desc: '文章接口',
    list: []
})
api[6].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/article/list',
    desc: '',
});
api[6].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/article/doSave',
    desc: '',
});
api[6].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/fastcms/api/ucenter/article/doSaveComment',
    desc: '',
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