let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'ArticleApi',
    order: '1',
    link: '文章接口',
    desc: '文章接口',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/article/list',
    desc: '文章列表',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/article/detail',
    desc: '文章详情',
});
api[0].list.push({
    alias: 'ArticleCategoryApi',
    order: '2',
    link: '',
    desc: '',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/article/category/list',
    desc: '',
});
api[0].list.push({
    alias: 'ArticleCommentApi',
    order: '3',
    link: '',
    desc: '',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/article/comment/list',
    desc: '',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/article/comment/doSaveComment',
    desc: '',
});
api[0].list.push({
    alias: 'ArticleController',
    order: '4',
    link: '',
    desc: '',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/article/list',
    desc: '',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/article/doSave',
    desc: '',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/article/category/doSave',
    desc: '',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/article/category/doDelete',
    desc: '',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/article/comment/doSave',
    desc: '',
});
api[0].list[3].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/article/comment/doDelete',
    desc: '',
});
api[0].list.push({
    alias: 'PageController',
    order: '5',
    link: '',
    desc: '',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/page/list',
    desc: '',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/page/doSave',
    desc: '',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/page/comment/doSave',
    desc: '',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/page/comment/doDelete',
    desc: '',
});
api[0].list.push({
    alias: 'TemplateController',
    order: '6',
    link: '',
    desc: '',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/list',
    desc: '',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/install',
    desc: '',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/doInstall',
    desc: '',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/doUnInstall',
    desc: '',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/edit',
    desc: '',
});
api[0].list[5].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/doEnable',
    desc: '',
});
api[0].list[5].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/doEditSave',
    desc: '',
});
api[0].list[5].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/doUpload',
    desc: '',
});
api[0].list[5].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/doDelFile',
    desc: '',
});
api[0].list[5].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/menu/list',
    desc: '',
});
api[0].list[5].list.push({
    order: '11',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/menu/edit',
    desc: '',
});
api[0].list[5].list.push({
    order: '12',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/menu/doSave',
    desc: '',
});
api[0].list[5].list.push({
    order: '13',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/menu/doDelete',
    desc: '',
});
api[0].list[5].list.push({
    order: '14',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/setting',
    desc: '',
});
api[0].list[5].list.push({
    order: '15',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/template/setting/doSave',
    desc: '',
});
api[0].list.push({
    alias: 'UCenterArticleController',
    order: '7',
    link: '',
    desc: '',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING+/article/list',
    desc: '',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING+/article/doSave',
    desc: '',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING+/article/doSaveComment',
    desc: '',
});
api[0].list.push({
    alias: 'TemplateIndexController',
    order: '8',
    link: '',
    desc: '',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/;	http:/127.0.0.1:8080/index',
    desc: '',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/p/{path}',
    desc: '',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/a/{id}',
    desc: '',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/a/c/{id}',
    desc: '',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/favicon.ico',
    desc: '',
});
api[0].list.push({
    alias: 'AdminController',
    order: '9',
    link: '',
    desc: '',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING/login',
    desc: '',
});
api[0].list[8].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING/captcha',
    desc: '',
});
api[0].list.push({
    alias: 'AttachmentController',
    order: '10',
    link: '',
    desc: '',
    list: []
})
api[0].list[9].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/list',
    desc: '',
});
api[0].list[9].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/doUpload',
    desc: '',
});
api[0].list[9].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/detail',
    desc: '',
});
api[0].list[9].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/doDelete',
    desc: '',
});
api[0].list[9].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/browse',
    desc: '',
});
api[0].list[9].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/doUploadOfCKEditor',
    desc: '',
});
api[0].list[9].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/attachment/doUploadOfCKEditorBrowse',
    desc: '',
});
api[0].list.push({
    alias: 'ConfigController',
    order: '11',
    link: '',
    desc: '',
    list: []
})
api[0].list[10].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/config/doSave',
    desc: '',
});
api[0].list.push({
    alias: 'MenuController',
    order: '12',
    link: '',
    desc: '',
    list: []
})
api[0].list[11].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/menu/list',
    desc: '',
});
api[0].list[11].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/menu/save',
    desc: '',
});
api[0].list[11].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/menu/del',
    desc: '',
});
api[0].list.push({
    alias: 'PluginController',
    order: '13',
    link: '',
    desc: '',
    list: []
})
api[0].list[12].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/plugin/doInstall',
    desc: '',
});
api[0].list[12].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/plugin/doUnInstall',
    desc: '',
});
api[0].list.push({
    alias: 'RoleController',
    order: '14',
    link: '',
    desc: '',
    list: []
})
api[0].list[13].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/role/list',
    desc: '',
});
api[0].list[13].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/role/save',
    desc: '',
});
api[0].list[13].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/role/getPermissionList',
    desc: '',
});
api[0].list[13].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/role/saveRolePermission',
    desc: '',
});
api[0].list.push({
    alias: 'SetupController',
    order: '15',
    link: '',
    desc: '',
    list: []
})
api[0].list[14].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/setup/wechatMini',
    desc: '',
});
api[0].list[14].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/setup/pay',
    desc: '',
});
api[0].list[14].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/setup/icons',
    desc: '',
});
api[0].list.push({
    alias: 'UserController',
    order: '16',
    link: '',
    desc: '',
    list: []
})
api[0].list[15].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/list',
    desc: '',
});
api[0].list[15].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/getMenus',
    desc: '',
});
api[0].list[15].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/doSave',
    desc: '',
});
api[0].list[15].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/doEdit',
    desc: '',
});
api[0].list[15].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/doEditRole',
    desc: '',
});
api[0].list[15].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/tag/doSave',
    desc: '',
});
api[0].list[15].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.ADMIN_MAPPING+/user/doEditTag',
    desc: '',
});
api[0].list.push({
    alias: 'PaymentCallbackController',
    order: '17',
    link: '',
    desc: '',
    list: []
})
api[0].list[16].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/payback/{platform}',
    desc: '',
});
api[0].list.push({
    alias: 'UploadApi',
    order: '18',
    link: '',
    desc: '',
    list: []
})
api[0].list[17].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/upload/doUpload',
    desc: '',
});
api[0].list.push({
    alias: 'UserApi',
    order: '19',
    link: '',
    desc: '',
    list: []
})
api[0].list.push({
    alias: 'WechatMiniUserApi',
    order: '20',
    link: '微信小程序用户授权登录',
    desc: '微信小程序用户授权登录',
    list: []
})
api[0].list[19].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/wechat/user/code2session',
    desc: '',
});
api[0].list[19].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/wechat/user/doLogin',
    desc: '',
});
api[0].list[19].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/wechat/user/getUserPhone',
    desc: '小程序用户通过昵称授权后，再通过获取手机号授权获取用户手机号码',
});
api[0].list[19].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.API_MAPPING+/wechat/user/doLoginByPhone',
    desc: '用户直接通过手机号码授权登录注意:用户通过手机号码授权登录，后台是获取不到用户的昵称跟头像的，需要手动设置小程序前端可以通过WXML标签直接显示用户头像昵称&lt;open-datatype="userAvatarUrl"&gt;&lt;/open-data&gt;&lt;open-datatype="userNickName"&gt;&lt;/open-data&gt;',
});
api[0].list.push({
    alias: 'UCenterController',
    order: '21',
    link: '',
    desc: '',
    list: []
})
api[0].list[20].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING/doRegister',
    desc: '',
});
api[0].list.push({
    alias: 'UCenterUserController',
    order: '22',
    link: '',
    desc: '',
    list: []
})
api[0].list[21].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING+/user/doSave',
    desc: '',
});
api[0].list[21].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING+/user/doEditPwd',
    desc: '',
});
api[0].list[21].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:8080/FastcmsConstants.UCENTER_MAPPING+/user/doSaveAvatar',
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