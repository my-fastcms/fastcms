<<<<<<< HEAD:web/src/main/resources/static/assets/js/write-ff1498a9.js
import{u as A}from"./vue-router-c1461dfc.js";import{A as C}from"./index-f108e1c0.js";import{P as H}from"./index-07ce180d.js";import{q as S}from"./qs-c5b6dbf2.js";import{_ as O}from"./index.vue_vue_type_style_index_0_lang-7ae68bb3.js";import{a as f}from"./element-plus-91d6d961.js";import{H as b,e as P,_ as T,i as R,ah as m,o as K,c as B,V as e,P as t,T as _,a as I,h as N}from"./@vue-6dabbe94.js";import"./index-a37b0464.js";import"./index-d6580e65.js";import"./pinia-b6f74250.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./client-dfa4cb81.js";import"./_plugin-vue_export-helper-c27b6911.js";import"./@ckeditor-2ec0d23b.js";import"./color-convert-d47c3cb3.js";import"./color-name-b7949e8c.js";import"./vanilla-colorful-3f59310b.js";const M={class:"container"},$=I("div",{class:"sub-title"},"结合网站模板使用，不正确填写，访问页面会出现404",-1),W=b({name:"pageWrite"}),Te=b({...W,setup(j){const n=H(),i=P(),g=A(),{proxy:V}=N(),l=T({fileType:"image",fit:"fill",params:{},ruleForm:{title:"",path:"",commentEnable:1,contentHtml:"",status:"publish"},rules:{title:{required:!0,message:"请输入页面标题",trigger:"blur"},path:{required:!0,message:"请输入页面访问路径",trigger:"blur"},contentHtml:{required:!0,message:"请输入页面详情",trigger:"blur"},seoKeywords:{required:!0,message:"请输入SEO关键词",trigger:"blur"},seoDescription:{required:!0,message:"请输入SEO描述",trigger:"blur"},status:{required:!0,message:"请选择发布状态",trigger:"blur"}}}),y=()=>{V.$refs.myRefForm.validate(p=>{if(p){let o=S.stringify(l.ruleForm,{arrayFormat:"repeat"});n.addPage(o).then(s=>{l.ruleForm.id=s.data,f.success("保存成功")}).catch(s=>{f({showClose:!0,message:s.message?s.message:"系统错误",type:"error"})})}})},F=p=>{n.getPage(p).then(o=>{l.ruleForm=o.data})},x=()=>{i.value.openDialog(1)},h=p=>{l.ruleForm.thumbnail=p[0].path};return R(()=>{l.params=g;let p=l.params.query.id;p&&F(p)}),(p,o)=>{const s=m("el-input"),a=m("el-form-item"),u=m("el-col"),w=m("el-image"),v=m("el-link"),E=m("el-switch"),d=m("el-option"),q=m("el-select"),c=m("el-row"),D=m("el-button"),U=m("el-form"),k=m("el-card");return K(),B("div",M,[e(k,null,{default:t(()=>[e(U,{model:l.ruleForm,"label-width":"100px",rules:l.rules,ref:"myRefForm"},{default:t(()=>[e(c,{gutter:35},{default:t(()=>[e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"标题",prop:"title"},{default:t(()=>[e(s,{modelValue:l.ruleForm.title,"onUpdate:modelValue":o[0]||(o[0]=r=>l.ruleForm.title=r),placeholder:"请输入页面标题",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"访问路径",prop:"path"},{default:t(()=>[e(s,{modelValue:l.ruleForm.path,"onUpdate:modelValue":o[1]||(o[1]=r=>l.ruleForm.path=r),placeholder:"请输入页面访问路径",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"页面详情",prop:"contentHtml"},{default:t(()=>[e(O,{modelValue:l.ruleForm.contentHtml,"onUpdate:modelValue":o[2]||(o[2]=r=>l.ruleForm.contentHtml=r)},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"缩略图",prop:"thumbnail"},{default:t(()=>[e(w,{style:{width:"100px",height:"100px"},src:l.ruleForm.thumbnail,fit:l.fit},null,8,["src","fit"])]),_:1}),e(a,null,{default:t(()=>[e(v,{type:"primary",onClick:x},{default:t(()=>[_("选择图片")]),_:1})]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"页面摘要",prop:"summary"},{default:t(()=>[e(s,{modelValue:l.ruleForm.summary,"onUpdate:modelValue":o[3]||(o[3]=r=>l.ruleForm.summary=r),type:"textarea",rows:2,placeholder:"请输入文章摘要",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"SEO关键词",prop:"seoKeywords"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoKeywords,"onUpdate:modelValue":o[4]||(o[4]=r=>l.ruleForm.seoKeywords=r),placeholder:"请输入seo关键词",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"SEO描述",prop:"seoDescription"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoDescription,"onUpdate:modelValue":o[5]||(o[5]=r=>l.ruleForm.seoDescription=r),placeholder:"请输入SEO描述",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"模板",prop:"suffix"},{default:t(()=>[e(s,{modelValue:l.ruleForm.suffix,"onUpdate:modelValue":o[6]||(o[6]=r=>l.ruleForm.suffix=r),placeholder:"请输入页面模板后缀",clearable:""},null,8,["modelValue"]),$]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"允许评论",prop:"commentEnable"},{default:t(()=>[e(E,{modelValue:l.ruleForm.commentEnable,"onUpdate:modelValue":o[7]||(o[7]=r=>l.ruleForm.commentEnable=r),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"状态",prop:"status"},{default:t(()=>[e(q,{modelValue:l.ruleForm.status,"onUpdate:modelValue":o[8]||(o[8]=r=>l.ruleForm.status=r),placeholder:"请选择状态",clearable:"",class:"w100"},{default:t(()=>[e(d,{label:"发布",value:"publish"}),e(d,{label:"草稿",value:"draft"})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1}),e(c,null,{default:t(()=>[e(a,null,{default:t(()=>[e(D,{type:"primary",onClick:y},{default:t(()=>[_("保 存")]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1}),e(C,{ref_key:"attachDialogRef",ref:i,onAttachHandler:h,fileType:l.fileType},null,8,["fileType"])])}}});export{Te as default};
=======
import{u as A}from"./vue-router-c1461dfc.js";import{A as C}from"./index-f26a851d.js";import{P as H}from"./index-d233017d.js";import{q as S}from"./qs-c5b6dbf2.js";import{_ as O}from"./index.vue_vue_type_style_index_0_lang-936b5bba.js";import{a as f}from"./element-plus-91d6d961.js";import{H as b,e as P,_ as T,i as R,ah as m,o as K,c as B,V as e,P as t,T as _,a as I,h as N}from"./@vue-6dabbe94.js";import"./index-c9db26a7.js";import"./index-aa5b7dfa.js";import"./pinia-b6f74250.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./client-0f616fa7.js";import"./_plugin-vue_export-helper-c27b6911.js";import"./@ckeditor-2ec0d23b.js";import"./color-convert-d47c3cb3.js";import"./color-name-b7949e8c.js";import"./vanilla-colorful-3f59310b.js";const M={class:"container"},$=I("div",{class:"sub-title"},"结合网站模板使用，不正确填写，访问页面会出现404",-1),W=b({name:"pageWrite"}),Te=b({...W,setup(j){const n=H(),i=P(),g=A(),{proxy:V}=N(),l=T({fileType:"image",fit:"fill",params:{},ruleForm:{title:"",path:"",commentEnable:1,contentHtml:"",status:"publish"},rules:{title:{required:!0,message:"请输入页面标题",trigger:"blur"},path:{required:!0,message:"请输入页面访问路径",trigger:"blur"},contentHtml:{required:!0,message:"请输入页面详情",trigger:"blur"},seoKeywords:{required:!0,message:"请输入SEO关键词",trigger:"blur"},seoDescription:{required:!0,message:"请输入SEO描述",trigger:"blur"},status:{required:!0,message:"请选择发布状态",trigger:"blur"}}}),y=()=>{V.$refs.myRefForm.validate(p=>{if(p){let o=S.stringify(l.ruleForm,{arrayFormat:"repeat"});n.addPage(o).then(s=>{l.ruleForm.id=s.data,f.success("保存成功")}).catch(s=>{f({showClose:!0,message:s.message?s.message:"系统错误",type:"error"})})}})},F=p=>{n.getPage(p).then(o=>{l.ruleForm=o.data})},x=()=>{i.value.openDialog(1)},h=p=>{l.ruleForm.thumbnail=p[0].path};return R(()=>{l.params=g;let p=l.params.query.id;p&&F(p)}),(p,o)=>{const s=m("el-input"),a=m("el-form-item"),u=m("el-col"),w=m("el-image"),v=m("el-link"),E=m("el-switch"),d=m("el-option"),q=m("el-select"),c=m("el-row"),D=m("el-button"),U=m("el-form"),k=m("el-card");return K(),B("div",M,[e(k,null,{default:t(()=>[e(U,{model:l.ruleForm,"label-width":"100px",rules:l.rules,ref:"myRefForm"},{default:t(()=>[e(c,{gutter:35},{default:t(()=>[e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"标题",prop:"title"},{default:t(()=>[e(s,{modelValue:l.ruleForm.title,"onUpdate:modelValue":o[0]||(o[0]=r=>l.ruleForm.title=r),placeholder:"请输入页面标题",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"访问路径",prop:"path"},{default:t(()=>[e(s,{modelValue:l.ruleForm.path,"onUpdate:modelValue":o[1]||(o[1]=r=>l.ruleForm.path=r),placeholder:"请输入页面访问路径",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"页面详情",prop:"contentHtml"},{default:t(()=>[e(O,{modelValue:l.ruleForm.contentHtml,"onUpdate:modelValue":o[2]||(o[2]=r=>l.ruleForm.contentHtml=r)},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"缩略图",prop:"thumbnail"},{default:t(()=>[e(w,{style:{width:"100px",height:"100px"},src:l.ruleForm.thumbnail,fit:l.fit},null,8,["src","fit"])]),_:1}),e(a,null,{default:t(()=>[e(v,{type:"primary",onClick:x},{default:t(()=>[_("选择图片")]),_:1})]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"页面摘要",prop:"summary"},{default:t(()=>[e(s,{modelValue:l.ruleForm.summary,"onUpdate:modelValue":o[3]||(o[3]=r=>l.ruleForm.summary=r),type:"textarea",rows:2,placeholder:"请输入文章摘要",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"SEO关键词",prop:"seoKeywords"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoKeywords,"onUpdate:modelValue":o[4]||(o[4]=r=>l.ruleForm.seoKeywords=r),placeholder:"请输入seo关键词",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"SEO描述",prop:"seoDescription"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoDescription,"onUpdate:modelValue":o[5]||(o[5]=r=>l.ruleForm.seoDescription=r),placeholder:"请输入SEO描述",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"模板",prop:"suffix"},{default:t(()=>[e(s,{modelValue:l.ruleForm.suffix,"onUpdate:modelValue":o[6]||(o[6]=r=>l.ruleForm.suffix=r),placeholder:"请输入页面模板后缀",clearable:""},null,8,["modelValue"]),$]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(a,{label:"允许评论",prop:"commentEnable"},{default:t(()=>[e(E,{modelValue:l.ruleForm.commentEnable,"onUpdate:modelValue":o[7]||(o[7]=r=>l.ruleForm.commentEnable=r),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(a,{label:"状态",prop:"status"},{default:t(()=>[e(q,{modelValue:l.ruleForm.status,"onUpdate:modelValue":o[8]||(o[8]=r=>l.ruleForm.status=r),placeholder:"请选择状态",clearable:"",class:"w100"},{default:t(()=>[e(d,{label:"发布",value:"publish"}),e(d,{label:"草稿",value:"draft"})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1}),e(c,null,{default:t(()=>[e(a,null,{default:t(()=>[e(D,{type:"primary",onClick:y},{default:t(()=>[_("保 存")]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1}),e(C,{ref_key:"attachDialogRef",ref:i,onAttachHandler:h,fileType:l.fileType},null,8,["fileType"])])}}});export{Te as default};
>>>>>>> 192aa1406293d953116f56b9fc878904d9c5643d:web/src/main/resources/static/assets/js/write-5ab78552.js
