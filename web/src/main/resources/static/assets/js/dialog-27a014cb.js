<<<<<<< HEAD:web/src/main/resources/static/assets/js/dialog-27a014cb.js
import{h as B,j as E,M as j,_ as O}from"./index-d6580e65.js";import{s as G}from"./pinia-b6f74250.js";import{H as v,e as D,_ as J,i as Q,ah as i,o as _,c,V as e,P as o,a as U,T as s,U as b,S as W,F as I,u as X,n as Y,aP as Z}from"./@vue-6dabbe94.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";const $={class:"system-menu-dialog-container"},ee={key:0},le={class:"dialog-footer"},oe=v({name:"systemMenuDialog"}),Re=v({...oe,emits:["refresh"],setup(te,{expose:y,emit:w}){const A=Z(()=>O(()=>import("./index-0898e0dc.js"),["assets/js/index-0898e0dc.js","assets/js/index.vue_vue_type_script_setup_true_name_iconSelector_lang-9cd586c8.js","assets/js/index-d6580e65.js","assets/js/@vue-6dabbe94.js","assets/js/pinia-b6f74250.js","assets/js/vue-router-c1461dfc.js","assets/js/vue-i18n-b3581196.js","assets/js/@intlify-ea47d1db.js","assets/js/source-map-7d7e1c08.js","assets/js/vue-b1ba6f14.js","assets/js/js-cookie-edb2da2a.js","assets/js/@element-plus-64e6e14a.js","assets/js/nprogress-08a53ce8.js","assets/css/nprogress-8b89e2e0.css","assets/js/axios-4a70c6fc.js","assets/js/qs-c5b6dbf2.js","assets/js/side-channel-394f276c.js","assets/js/get-intrinsic-bd2830fd.js","assets/js/has-symbols-e8f3ca0e.js","assets/js/has-proto-f7d0b240.js","assets/js/function-bind-22e7ee79.js","assets/js/has-26d28e02.js","assets/js/call-bind-e5c1c8b0.js","assets/js/object-inspect-8fd4bade.js","assets/js/element-plus-91d6d961.js","assets/js/lodash-es-9851428c.js","assets/js/@vueuse-e129c873.js","assets/js/@popperjs-c75af06c.js","assets/js/@ctrl-f8748455.js","assets/js/dayjs-f6fdf7b4.js","assets/js/async-validator-dee29e8b.js","assets/js/memoize-one-297ddbcb.js","assets/js/escape-html-b8998962.js","assets/js/normalize-wheel-es-ed76fb12.js","assets/js/@floating-ui-880a26aa.js","assets/js/mitt-f7ef348c.js","assets/js/vue-grid-layout-ec2a62c3.js","assets/css/fastcms-01cf5f8f.css","assets/css/index-47b5c078.css"])),V=j(),g=D(),x=D(),L=B(),{routesList:N}=G(L),l=J({ruleForm:{id:null,parentId:0,name:"",path:"",component:"",sortNum:0,title:"",icon:"",link:"",isHide:!1,isKeepAlive:!0,isAffix:!0,isLink:!1,isIframe:!1},rules:{name:{required:!0,message:"请输入路由名称",trigger:"blur"},path:{required:!0,message:"请输入路由地址",trigger:"blur"},title:{required:!0,message:"请输入菜单名称",trigger:"blur"},component:{required:!0,message:"请输入组件地址",trigger:"blur"}},menuData:[],dialog:{isShowDialog:!1,type:"",title:"",submitTxt:""}}),F=n=>{const t=[];return n.map(u=>{var r;u.title=E.global.t((r=u.meta)==null?void 0:r.title),t.push({...u}),u.children&&F(u.children)}),t},S=(n,t)=>{n==="edit"?(l.dialog.title="修改菜单",l.dialog.submitTxt="修 改",V.getMenu(t.id).then(u=>{delete u.data.created,delete u.data.updated,l.ruleForm=u.data})):(l.dialog.title="新增菜单",l.dialog.submitTxt="新 增",Y(()=>{g.value.resetFields(),l.ruleForm.id=null,l.ruleForm.parentId=t==null?0:t.id||0})),l.dialog.type=n,l.dialog.isShowDialog=!0},k=()=>{l.dialog.isShowDialog=!1},M=()=>{l.ruleForm.isIframe?l.ruleForm.isLink=!0:l.ruleForm.isLink=!1},T=()=>{k()},P=()=>{const n=x.value.getCheckedNodes();l.ruleForm.parentId=n[0].value},H=()=>{new Promise(n=>{g.value.validate(t=>{t&&(n(t),V.saveMenu(l.ruleForm).then(()=>{k(),w("refresh")}))})})};return Q(()=>{l.menuData=F(N.value)}),y({openDialog:S}),(n,t)=>{const u=i("el-cascader"),r=i("el-form-item"),m=i("el-col"),p=i("el-input"),R=i("el-input-number"),d=i("el-radio"),f=i("el-radio-group"),q=i("el-row"),K=i("el-form"),h=i("el-button"),z=i("el-dialog");return _(),c("div",$,[e(z,{title:l.dialog.title,modelValue:l.dialog.isShowDialog,"onUpdate:modelValue":t[13]||(t[13]=a=>l.dialog.isShowDialog=a),width:"769px"},{footer:o(()=>[U("span",le,[e(h,{onClick:T,size:"default"},{default:o(()=>[s("取 消")]),_:1}),e(h,{type:"primary",onClick:H,size:"default"},{default:o(()=>[s(b(l.dialog.submitTxt),1)]),_:1})])]),default:o(()=>[e(K,{ref_key:"menuDialogFormRef",ref:g,model:l.ruleForm,rules:l.rules,size:"default","label-width":"80px"},{default:o(()=>[e(q,{gutter:35},{default:o(()=>[e(m,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:o(()=>[e(r,{label:"上级菜单"},{default:o(()=>[e(u,{ref_key:"parentMenuCascader",ref:x,options:l.menuData,props:{checkStrictly:!0,value:"id",label:"title"},placeholder:"请选择上级菜单",clearable:"",class:"w100",modelValue:l.ruleForm.parentId,"onUpdate:modelValue":t[0]||(t[0]=a=>l.ruleForm.parentId=a),emitPath:!1,"show-all-levels":!1,onChange:P},{default:o(({node:a,data:C})=>[U("span",null,b(C.title),1),a.isLeaf?W("",!0):(_(),c("span",ee," ("+b(C.children.length)+") ",1))]),_:1},8,["options","modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"菜单名称",prop:"title"},{default:o(()=>[e(p,{modelValue:l.ruleForm.title,"onUpdate:modelValue":t[1]||(t[1]=a=>l.ruleForm.title=a),placeholder:"格式：message.router.xxx",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),(_(),c(I,{key:0},[e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"路由名称",prop:"name"},{default:o(()=>[e(p,{modelValue:l.ruleForm.name,"onUpdate:modelValue":t[2]||(t[2]=a=>l.ruleForm.name=a),placeholder:"路由中的 name 值",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"路由路径",prop:"path"},{default:o(()=>[e(p,{modelValue:l.ruleForm.path,"onUpdate:modelValue":t[3]||(t[3]=a=>l.ruleForm.path=a),placeholder:"路由中的 path 值",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"菜单图标",prop:"icon"},{default:o(()=>[e(X(A),{placeholder:"请输入菜单图标",modelValue:l.ruleForm.icon,"onUpdate:modelValue":t[4]||(t[4]=a=>l.ruleForm.icon=a)},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"组件路径",prop:"component"},{default:o(()=>[e(p,{modelValue:l.ruleForm.component,"onUpdate:modelValue":t[5]||(t[5]=a=>l.ruleForm.component=a),placeholder:"组件路径",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"菜单排序",prop:"sortNum"},{default:o(()=>[e(R,{modelValue:l.ruleForm.sortNum,"onUpdate:modelValue":t[6]||(t[6]=a=>l.ruleForm.sortNum=a),"controls-position":"right",placeholder:"请输入排序",class:"w100"},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否外链",prop:"isLink"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isLink,"onUpdate:modelValue":t[7]||(t[7]=a=>l.ruleForm.isLink=a),disabled:l.ruleForm.isIframe},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("是")]),_:1}),e(d,{label:!1},{default:o(()=>[s("否")]),_:1})]),_:1},8,["modelValue","disabled"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"链接地址",prop:"link"},{default:o(()=>[e(p,{modelValue:l.ruleForm.link,"onUpdate:modelValue":t[8]||(t[8]=a=>l.ruleForm.link=a),placeholder:"外链/内嵌时链接地址（http:xxx.com）",clearable:"",disabled:!l.ruleForm.isLink},null,8,["modelValue","disabled"])]),_:1})]),_:1})],64)),(_(),c(I,{key:1},[e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否隐藏",prop:"isHide"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isHide,"onUpdate:modelValue":t[9]||(t[9]=a=>l.ruleForm.isHide=a)},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("隐藏")]),_:1}),e(d,{label:!1},{default:o(()=>[s("不隐藏")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"页面缓存",prop:"isKeepAlive"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isKeepAlive,"onUpdate:modelValue":t[10]||(t[10]=a=>l.ruleForm.isKeepAlive=a)},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("缓存")]),_:1}),e(d,{label:!1},{default:o(()=>[s("不缓存")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否固定",prop:"isAffix"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isAffix,"onUpdate:modelValue":t[11]||(t[11]=a=>l.ruleForm.isAffix=a)},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("固定")]),_:1}),e(d,{label:!1},{default:o(()=>[s("不固定")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否内嵌",prop:"isIframe"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isIframe,"onUpdate:modelValue":t[12]||(t[12]=a=>l.ruleForm.isIframe=a),onChange:M},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("是")]),_:1}),e(d,{label:!1},{default:o(()=>[s("否")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1})],64))]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["title","modelValue"])])}}});export{Re as default};
=======
import{h as B,j as E,M as j,_ as O}from"./index-aa5b7dfa.js";import{s as G}from"./pinia-b6f74250.js";import{H as v,e as D,_ as J,i as Q,ah as i,o as _,c,V as e,P as o,a as U,T as s,U as b,S as W,F as I,u as X,n as Y,aP as Z}from"./@vue-6dabbe94.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./qs-c5b6dbf2.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./element-plus-91d6d961.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";const $={class:"system-menu-dialog-container"},ee={key:0},le={class:"dialog-footer"},oe=v({name:"systemMenuDialog"}),Re=v({...oe,emits:["refresh"],setup(te,{expose:y,emit:w}){const A=Z(()=>O(()=>import("./index-1a226afa.js"),["assets/js/index-1a226afa.js","assets/js/index.vue_vue_type_script_setup_true_name_iconSelector_lang-9eddc8da.js","assets/js/index-aa5b7dfa.js","assets/js/@vue-6dabbe94.js","assets/js/pinia-b6f74250.js","assets/js/vue-router-c1461dfc.js","assets/js/vue-i18n-b3581196.js","assets/js/@intlify-ea47d1db.js","assets/js/source-map-7d7e1c08.js","assets/js/vue-b1ba6f14.js","assets/js/js-cookie-edb2da2a.js","assets/js/@element-plus-64e6e14a.js","assets/js/nprogress-08a53ce8.js","assets/css/nprogress-8b89e2e0.css","assets/js/axios-4a70c6fc.js","assets/js/qs-c5b6dbf2.js","assets/js/side-channel-394f276c.js","assets/js/get-intrinsic-bd2830fd.js","assets/js/has-symbols-e8f3ca0e.js","assets/js/has-proto-f7d0b240.js","assets/js/function-bind-22e7ee79.js","assets/js/has-26d28e02.js","assets/js/call-bind-e5c1c8b0.js","assets/js/object-inspect-8fd4bade.js","assets/js/element-plus-91d6d961.js","assets/js/lodash-es-9851428c.js","assets/js/@vueuse-e129c873.js","assets/js/@popperjs-c75af06c.js","assets/js/@ctrl-f8748455.js","assets/js/dayjs-f6fdf7b4.js","assets/js/async-validator-dee29e8b.js","assets/js/memoize-one-297ddbcb.js","assets/js/escape-html-b8998962.js","assets/js/normalize-wheel-es-ed76fb12.js","assets/js/@floating-ui-880a26aa.js","assets/js/mitt-f7ef348c.js","assets/js/vue-grid-layout-ec2a62c3.js","assets/css/fastcms-01cf5f8f.css","assets/css/index-47b5c078.css"])),V=j(),g=D(),x=D(),L=B(),{routesList:N}=G(L),l=J({ruleForm:{id:null,parentId:0,name:"",path:"",component:"",sortNum:0,title:"",icon:"",link:"",isHide:!1,isKeepAlive:!0,isAffix:!0,isLink:!1,isIframe:!1},rules:{name:{required:!0,message:"请输入路由名称",trigger:"blur"},path:{required:!0,message:"请输入路由地址",trigger:"blur"},title:{required:!0,message:"请输入菜单名称",trigger:"blur"},component:{required:!0,message:"请输入组件地址",trigger:"blur"}},menuData:[],dialog:{isShowDialog:!1,type:"",title:"",submitTxt:""}}),F=n=>{const t=[];return n.map(u=>{var r;u.title=E.global.t((r=u.meta)==null?void 0:r.title),t.push({...u}),u.children&&F(u.children)}),t},S=(n,t)=>{n==="edit"?(l.dialog.title="修改菜单",l.dialog.submitTxt="修 改",V.getMenu(t.id).then(u=>{delete u.data.created,delete u.data.updated,l.ruleForm=u.data})):(l.dialog.title="新增菜单",l.dialog.submitTxt="新 增",Y(()=>{g.value.resetFields(),l.ruleForm.id=null,l.ruleForm.parentId=t==null?0:t.id||0})),l.dialog.type=n,l.dialog.isShowDialog=!0},k=()=>{l.dialog.isShowDialog=!1},M=()=>{l.ruleForm.isIframe?l.ruleForm.isLink=!0:l.ruleForm.isLink=!1},T=()=>{k()},P=()=>{const n=x.value.getCheckedNodes();l.ruleForm.parentId=n[0].value},H=()=>{new Promise(n=>{g.value.validate(t=>{t&&(n(t),V.saveMenu(l.ruleForm).then(()=>{k(),w("refresh")}))})})};return Q(()=>{l.menuData=F(N.value)}),y({openDialog:S}),(n,t)=>{const u=i("el-cascader"),r=i("el-form-item"),m=i("el-col"),p=i("el-input"),R=i("el-input-number"),d=i("el-radio"),f=i("el-radio-group"),q=i("el-row"),K=i("el-form"),h=i("el-button"),z=i("el-dialog");return _(),c("div",$,[e(z,{title:l.dialog.title,modelValue:l.dialog.isShowDialog,"onUpdate:modelValue":t[13]||(t[13]=a=>l.dialog.isShowDialog=a),width:"769px"},{footer:o(()=>[U("span",le,[e(h,{onClick:T,size:"default"},{default:o(()=>[s("取 消")]),_:1}),e(h,{type:"primary",onClick:H,size:"default"},{default:o(()=>[s(b(l.dialog.submitTxt),1)]),_:1})])]),default:o(()=>[e(K,{ref_key:"menuDialogFormRef",ref:g,model:l.ruleForm,rules:l.rules,size:"default","label-width":"80px"},{default:o(()=>[e(q,{gutter:35},{default:o(()=>[e(m,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:o(()=>[e(r,{label:"上级菜单"},{default:o(()=>[e(u,{ref_key:"parentMenuCascader",ref:x,options:l.menuData,props:{checkStrictly:!0,value:"id",label:"title"},placeholder:"请选择上级菜单",clearable:"",class:"w100",modelValue:l.ruleForm.parentId,"onUpdate:modelValue":t[0]||(t[0]=a=>l.ruleForm.parentId=a),emitPath:!1,"show-all-levels":!1,onChange:P},{default:o(({node:a,data:C})=>[U("span",null,b(C.title),1),a.isLeaf?W("",!0):(_(),c("span",ee," ("+b(C.children.length)+") ",1))]),_:1},8,["options","modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"菜单名称",prop:"title"},{default:o(()=>[e(p,{modelValue:l.ruleForm.title,"onUpdate:modelValue":t[1]||(t[1]=a=>l.ruleForm.title=a),placeholder:"格式：message.router.xxx",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),(_(),c(I,{key:0},[e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"路由名称",prop:"name"},{default:o(()=>[e(p,{modelValue:l.ruleForm.name,"onUpdate:modelValue":t[2]||(t[2]=a=>l.ruleForm.name=a),placeholder:"路由中的 name 值",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"路由路径",prop:"path"},{default:o(()=>[e(p,{modelValue:l.ruleForm.path,"onUpdate:modelValue":t[3]||(t[3]=a=>l.ruleForm.path=a),placeholder:"路由中的 path 值",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"菜单图标",prop:"icon"},{default:o(()=>[e(X(A),{placeholder:"请输入菜单图标",modelValue:l.ruleForm.icon,"onUpdate:modelValue":t[4]||(t[4]=a=>l.ruleForm.icon=a)},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"组件路径",prop:"component"},{default:o(()=>[e(p,{modelValue:l.ruleForm.component,"onUpdate:modelValue":t[5]||(t[5]=a=>l.ruleForm.component=a),placeholder:"组件路径",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"菜单排序",prop:"sortNum"},{default:o(()=>[e(R,{modelValue:l.ruleForm.sortNum,"onUpdate:modelValue":t[6]||(t[6]=a=>l.ruleForm.sortNum=a),"controls-position":"right",placeholder:"请输入排序",class:"w100"},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否外链",prop:"isLink"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isLink,"onUpdate:modelValue":t[7]||(t[7]=a=>l.ruleForm.isLink=a),disabled:l.ruleForm.isIframe},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("是")]),_:1}),e(d,{label:!1},{default:o(()=>[s("否")]),_:1})]),_:1},8,["modelValue","disabled"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"链接地址",prop:"link"},{default:o(()=>[e(p,{modelValue:l.ruleForm.link,"onUpdate:modelValue":t[8]||(t[8]=a=>l.ruleForm.link=a),placeholder:"外链/内嵌时链接地址（http:xxx.com）",clearable:"",disabled:!l.ruleForm.isLink},null,8,["modelValue","disabled"])]),_:1})]),_:1})],64)),(_(),c(I,{key:1},[e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否隐藏",prop:"isHide"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isHide,"onUpdate:modelValue":t[9]||(t[9]=a=>l.ruleForm.isHide=a)},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("隐藏")]),_:1}),e(d,{label:!1},{default:o(()=>[s("不隐藏")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"页面缓存",prop:"isKeepAlive"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isKeepAlive,"onUpdate:modelValue":t[10]||(t[10]=a=>l.ruleForm.isKeepAlive=a)},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("缓存")]),_:1}),e(d,{label:!1},{default:o(()=>[s("不缓存")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否固定",prop:"isAffix"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isAffix,"onUpdate:modelValue":t[11]||(t[11]=a=>l.ruleForm.isAffix=a)},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("固定")]),_:1}),e(d,{label:!1},{default:o(()=>[s("不固定")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(m,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"是否内嵌",prop:"isIframe"},{default:o(()=>[e(f,{modelValue:l.ruleForm.isIframe,"onUpdate:modelValue":t[12]||(t[12]=a=>l.ruleForm.isIframe=a),onChange:M},{default:o(()=>[e(d,{label:!0},{default:o(()=>[s("是")]),_:1}),e(d,{label:!1},{default:o(()=>[s("否")]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1})],64))]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["title","modelValue"])])}}});export{Re as default};
>>>>>>> 192aa1406293d953116f56b9fc878904d9c5643d:web/src/main/resources/static/assets/js/dialog-c336f1b2.js
