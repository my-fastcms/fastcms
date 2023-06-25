import{u as K}from"./vue-router-c1461dfc.js";import{A as F}from"./index-0b6f3bad.js";import{C as B}from"./client-803b3680.js";import{q as I}from"./qs-c5b6dbf2.js";import{_ as M}from"./index.vue_vue_type_style_index_0_lang-c2aecb73.js";import{a as h}from"./element-plus-91d6d961.js";import{H as x,e as p,_ as P,i as z,ah as s,o as d,c as V,V as e,P as t,T as c,F as W,a8 as $,O as j}from"./@vue-6dabbe94.js";import"./index-2927ba89.js";import"./index-a6ee13d3.js";import"./pinia-b6f74250.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./client-578c7487.js";import"./_plugin-vue_export-helper-c27b6911.js";import"./@ckeditor-2ec0d23b.js";import"./color-convert-d47c3cb3.js";import"./color-name-b7949e8c.js";import"./vanilla-colorful-3f59310b.js";const G={class:"container"},J=x({name:"clientArticleWrite"}),Ie=x({...J,setup(Q){const n=B(),f=p(),_=p(),g=p(),w=K(),l=P({fit:"fill",row:null,isShowDialog:!1,params:{},categories:[],tags:[],ruleForm:{title:"",commentEnable:1,contentHtml:"",summary:"",seoKeywords:"",seoDescription:"",outLink:"",sortNum:0,thumbnail:"",suffix:"",articleCategory:null,articleTag:null,attachTitle:"",thumbnailUrl:""},rules:{title:{required:!0,message:"请输入文章标题",trigger:"blur"},contentHtml:{required:!0,message:"请输入文章详情",trigger:"blur"},thumbnail:{required:!0,message:"请选择缩略图",trigger:"blur"},summary:{required:!0,message:"请输入文章摘要",trigger:"blur"},seoKeywords:{required:!0,message:"请输入SEO关键词",trigger:"blur"},seoDescription:{required:!0,message:"请输入SEO描述",trigger:"blur"}}}),C=()=>{n.getArticleCategoryList().then(a=>{l.categories=a.data}),n.getArticleTagList().then(a=>{a.data.forEach(o=>{l.tags.push(o)})})},k=()=>{g.value.validate(a=>{if(a){let o=I.stringify(l.ruleForm,{arrayFormat:"repeat"});n.addArticle(o).then(i=>{l.ruleForm.id=i.data,h.success("保存成功")}).catch(i=>{h({showClose:!0,message:i.message?i.message:"系统错误",type:"error"})})}})},A=a=>{n.getArticle(a).then(o=>{l.ruleForm=o.data})},D=()=>{f.value.openDialog(1)},T=()=>{_.value.openDialog(1)},U=a=>{l.ruleForm.thumbnail=a[0].filePath,l.ruleForm.thumbnailUrl=a[0].path},E=a=>{l.ruleForm.attachId=a[0].id,l.ruleForm.attachTitle=a[0].fileName};return z(()=>{l.params=w,C();let a=l.params.query.id;a&&A(a)}),(a,o)=>{const i=s("el-input"),m=s("el-form-item"),u=s("el-col"),v=s("el-image"),b=s("el-link"),q=s("el-switch"),S=s("el-cascader"),H=s("el-option"),L=s("el-select"),y=s("el-row"),O=s("el-button"),R=s("el-form"),N=s("el-card");return d(),V("div",G,[e(N,null,{default:t(()=>[e(R,{model:l.ruleForm,"label-width":"100px",rules:l.rules,ref_key:"myRefForm",ref:g},{default:t(()=>[e(y,{gutter:35},{default:t(()=>[e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"标题",prop:"title"},{default:t(()=>[e(i,{modelValue:l.ruleForm.title,"onUpdate:modelValue":o[0]||(o[0]=r=>l.ruleForm.title=r),placeholder:"请输入文章标题",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"文章详情",prop:"contentHtml"},{default:t(()=>[e(M,{modelValue:l.ruleForm.contentHtml,"onUpdate:modelValue":o[1]||(o[1]=r=>l.ruleForm.contentHtml=r),isClient:!0},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"缩略图",prop:"thumbnail"},{default:t(()=>[e(v,{style:{width:"100px",height:"100px"},src:l.ruleForm.thumbnailUrl,fit:l.fit},null,8,["src","fit"])]),_:1}),e(m,null,{default:t(()=>[e(b,{type:"primary",onClick:D},{default:t(()=>[c("选择图片")]),_:1})]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"文章摘要",prop:"summary"},{default:t(()=>[e(i,{modelValue:l.ruleForm.summary,"onUpdate:modelValue":o[2]||(o[2]=r=>l.ruleForm.summary=r),type:"textarea",rows:2,placeholder:"请输入文章摘要",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"SEO关键词",prop:"seoKeywords"},{default:t(()=>[e(i,{type:"textarea",rows:2,modelValue:l.ruleForm.seoKeywords,"onUpdate:modelValue":o[3]||(o[3]=r=>l.ruleForm.seoKeywords=r),placeholder:"请输入seo关键词",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"SEO描述",prop:"seoDescription"},{default:t(()=>[e(i,{type:"textarea",rows:2,modelValue:l.ruleForm.seoDescription,"onUpdate:modelValue":o[4]||(o[4]=r=>l.ruleForm.seoDescription=r),placeholder:"请输入SEO描述",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"外链",prop:"outLink"},{default:t(()=>[e(i,{modelValue:l.ruleForm.outLink,"onUpdate:modelValue":o[5]||(o[5]=r=>l.ruleForm.outLink=r),placeholder:"请输入外链",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"允许评论",prop:"commentEnable"},{default:t(()=>[e(q,{modelValue:l.ruleForm.commentEnable,"onUpdate:modelValue":o[6]||(o[6]=r=>l.ruleForm.commentEnable=r),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"分类",prop:"categories"},{default:t(()=>[e(S,{ref:"categoryCascader",modelValue:l.ruleForm.articleCategory,"onUpdate:modelValue":o[7]||(o[7]=r=>l.ruleForm.articleCategory=r),options:l.categories,props:{multiple:!0,label:"label",value:"id",children:"children"},"collapse-tags":"",clearable:""},null,8,["modelValue","options"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"标签",prop:"tags"},{default:t(()=>[e(L,{modelValue:l.ruleForm.articleTag,"onUpdate:modelValue":o[8]||(o[8]=r=>l.ruleForm.articleTag=r),class:"w100",multiple:"",filterable:"","default-first-option":"",placeholder:"请选择标签"},{default:t(()=>[(d(!0),V(W,null,$(l.tags,r=>(d(),j(H,{key:r.id,label:r.tagName,value:r.tagName},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:24,md:24,lg:24,xl:24,class:"mb20"},{default:t(()=>[e(m,{label:"附件",prop:"attachTitle"},{default:t(()=>[e(i,{modelValue:l.ruleForm.attachTitle,"onUpdate:modelValue":o[9]||(o[9]=r=>l.ruleForm.attachTitle=r),readonly:""},null,8,["modelValue"])]),_:1}),e(m,null,{default:t(()=>[e(b,{type:"primary",onClick:T},{default:t(()=>[c("选择附件")]),_:1})]),_:1})]),_:1})]),_:1}),e(y,null,{default:t(()=>[e(m,null,{default:t(()=>[e(O,{type:"primary",onClick:k,size:"default"},{default:t(()=>[c("保 存")]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1}),e(F,{ref_key:"thumbnailDialogRef",ref:f,onAttachHandler:U,isClient:!0},null,512),e(F,{ref_key:"attachDialogRef",ref:_,onAttachHandler:E,isClient:!0},null,512)])}}});export{Ie as default};