<<<<<<< HEAD:web/src/main/resources/static/assets/js/write-454ab46a.js
import{u as z}from"./vue-router-c1461dfc.js";import{A as x}from"./index-f108e1c0.js";import{A as M}from"./index-8373c6c3.js";import{q as P}from"./qs-c5b6dbf2.js";import{_ as W}from"./index.vue_vue_type_style_index_0_lang-7ae68bb3.js";import{a as w}from"./element-plus-91d6d961.js";import{H as C,e as d,_ as $,i as j,ah as i,o as c,c as k,V as e,P as t,u as G,T as f,F as J,a8 as Q,a as X,O as Y}from"./@vue-6dabbe94.js";import"./index-a37b0464.js";import"./index-d6580e65.js";import"./pinia-b6f74250.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./client-dfa4cb81.js";import"./_plugin-vue_export-helper-c27b6911.js";import"./@ckeditor-2ec0d23b.js";import"./color-convert-d47c3cb3.js";import"./color-name-b7949e8c.js";import"./vanilla-colorful-3f59310b.js";const Z={class:"container"},ee=X("div",{class:"sub-title"},"结合网站模板使用，不正确填写，访问页面会出现404",-1),le=C({name:"articleWrite"}),je=C({...le,setup(te){const v=W,g=d(),n=M(),_=d(),b=d(),y=d(),T=z(),l=$({fileType:"image",fit:"fill",row:null,isShowDialog:!1,params:{},categories:[],tags:[],ruleForm:{thumbnailUrl:"",articleTag:"",attachTitle:"",title:"",commentEnable:!0,contentHtml:"",summary:"",seoKeywords:"",seoDescription:"",outLink:"",sortNum:0,thumbnail:"",suffix:"",status:"publish",articleCategory:[],attachId:null},rules:{title:{required:!0,message:"请输入文章标题",trigger:"blur"},contentHtml:{required:!0,message:"请输入文章详情",trigger:"blur"},thumbnail:{required:!0,message:"请选择缩略图",trigger:"blur"},summary:{required:!0,message:"请输入文章摘要",trigger:"blur"},seoKeywords:{required:!0,message:"请输入SEO关键词",trigger:"blur"},seoDescription:{required:!0,message:"请输入SEO描述",trigger:"blur"},status:{required:!0,message:"请选择发布状态",trigger:"blur"}}}),U=()=>{n.getArticleCategoryList().then(r=>{l.categories=r.data}),n.getArticleTagList().then(r=>{r.data.forEach(o=>{l.tags.push(o)})})},A=()=>{g.value.validate(r=>{if(r){y.value.getCheckedNodes(!0).map(s=>{l.ruleForm.articleCategory.push(s.value)});let o=P.stringify(l.ruleForm,{arrayFormat:"repeat"});n.addArticle(o).then(s=>{l.ruleForm.id=s.data,w.success("保存成功")}).catch(s=>{w({showClose:!0,message:s.message?s.message:"系统错误",type:"error"})})}})},D=r=>{n.getArticle(r).then(o=>{l.ruleForm=o.data})},E=()=>{_.value.openDialog(1)},N=()=>{b.value.openDialog(1)},q=r=>{l.ruleForm.thumbnail=r[0].filePath,l.ruleForm.thumbnailUrl=r[0].path},S=r=>{l.ruleForm.attachId=r[0].id,l.ruleForm.attachTitle=r[0].fileName},H=()=>{};return j(()=>{l.params=T,U();let r=l.params.query.id;r&&D(r)}),(r,o)=>{const s=i("el-input"),m=i("el-form-item"),u=i("el-col"),L=i("el-image"),F=i("el-link"),O=i("el-switch"),R=i("el-cascader"),p=i("el-option"),V=i("el-select"),h=i("el-row"),K=i("el-button"),B=i("el-form"),I=i("el-card");return c(),k("div",Z,[e(I,null,{default:t(()=>[e(B,{model:l.ruleForm,size:"default","label-width":"100px",rules:l.rules,ref_key:"myRefForm",ref:g},{default:t(()=>[e(h,{gutter:35},{default:t(()=>[e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"标题",prop:"title"},{default:t(()=>[e(s,{modelValue:l.ruleForm.title,"onUpdate:modelValue":o[0]||(o[0]=a=>l.ruleForm.title=a),placeholder:"请输入文章标题",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"文章详情",prop:"contentHtml"},{default:t(()=>[e(G(v),{style:{width:"100%"},modelValue:l.ruleForm.contentHtml,"onUpdate:modelValue":o[1]||(o[1]=a=>l.ruleForm.contentHtml=a)},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"缩略图",prop:"thumbnail"},{default:t(()=>[e(L,{style:{width:"100px",height:"100px"},src:l.ruleForm.thumbnailUrl,fit:l.fit,"preview-src-list":[l.ruleForm.thumbnailUrl]},null,8,["src","fit","preview-src-list"])]),_:1}),e(m,null,{default:t(()=>[e(F,{type:"primary",onClick:E},{default:t(()=>[f("选择图片")]),_:1})]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"文章摘要",prop:"summary"},{default:t(()=>[e(s,{modelValue:l.ruleForm.summary,"onUpdate:modelValue":o[2]||(o[2]=a=>l.ruleForm.summary=a),type:"textarea",rows:2,placeholder:"请输入文章摘要",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"SEO关键词",prop:"seoKeywords"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoKeywords,"onUpdate:modelValue":o[3]||(o[3]=a=>l.ruleForm.seoKeywords=a),placeholder:"请输入seo关键词",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"SEO描述",prop:"seoDescription"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoDescription,"onUpdate:modelValue":o[4]||(o[4]=a=>l.ruleForm.seoDescription=a),placeholder:"请输入SEO描述",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"排序",prop:"sortNum"},{default:t(()=>[e(s,{modelValue:l.ruleForm.sortNum,"onUpdate:modelValue":o[5]||(o[5]=a=>l.ruleForm.sortNum=a),type:"number",placeholder:"请输入排序序号",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"外链",prop:"outLink"},{default:t(()=>[e(s,{modelValue:l.ruleForm.outLink,"onUpdate:modelValue":o[6]||(o[6]=a=>l.ruleForm.outLink=a),placeholder:"请输入外链",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"模板",prop:"suffix"},{default:t(()=>[e(s,{modelValue:l.ruleForm.suffix,"onUpdate:modelValue":o[7]||(o[7]=a=>l.ruleForm.suffix=a),placeholder:"请输入文章模板后缀",clearable:""},null,8,["modelValue"]),ee]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"允许评论",prop:"commentEnable"},{default:t(()=>[e(O,{modelValue:l.ruleForm.commentEnable,"onUpdate:modelValue":o[8]||(o[8]=a=>l.ruleForm.commentEnable=a),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"分类",prop:"categories"},{default:t(()=>[e(R,{ref_key:"categoryCascader",ref:y,onChange:H,modelValue:l.ruleForm.articleCategory,"onUpdate:modelValue":o[9]||(o[9]=a=>l.ruleForm.articleCategory=a),options:l.categories,props:{multiple:!0,label:"label",value:"id",children:"children"},"collapse-tags":"",clearable:""},null,8,["modelValue","options"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"标签",prop:"tags"},{default:t(()=>[e(V,{modelValue:l.ruleForm.articleTag,"onUpdate:modelValue":o[10]||(o[10]=a=>l.ruleForm.articleTag=a),class:"w100",multiple:"",filterable:"","allow-create":"","default-first-option":"",placeholder:"可直接输入标签名称"},{default:t(()=>[(c(!0),k(J,null,Q(l.tags,a=>(c(),Y(p,{key:a.id,label:a.tagName,value:a.tagName},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"附件",prop:"attachTitle"},{default:t(()=>[e(s,{modelValue:l.ruleForm.attachTitle,"onUpdate:modelValue":o[11]||(o[11]=a=>l.ruleForm.attachTitle=a),readonly:""},null,8,["modelValue"])]),_:1}),e(m,null,{default:t(()=>[e(F,{type:"primary",onClick:N},{default:t(()=>[f("选择附件")]),_:1})]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"状态",prop:"status"},{default:t(()=>[e(V,{modelValue:l.ruleForm.status,"onUpdate:modelValue":o[12]||(o[12]=a=>l.ruleForm.status=a),placeholder:"请选择状态",clearable:"",class:"w100"},{default:t(()=>[e(p,{label:"发布",value:"publish"}),e(p,{label:"草稿",value:"draft"})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1}),e(h,null,{default:t(()=>[e(m,null,{default:t(()=>[e(K,{type:"primary",onClick:A,size:"default"},{default:t(()=>[f("保 存")]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1}),e(x,{ref_key:"thumbnailDialogRef",ref:_,onAttachHandler:q,fileType:l.fileType},null,8,["fileType"]),e(x,{ref_key:"attachDialogRef",ref:b,onAttachHandler:S},null,512)])}}});export{je as default};
=======
import{u as z}from"./vue-router-c1461dfc.js";import{A as x}from"./index-f26a851d.js";import{A as M}from"./index-01b7e964.js";import{q as P}from"./qs-c5b6dbf2.js";import{_ as W}from"./index.vue_vue_type_style_index_0_lang-936b5bba.js";import{a as w}from"./element-plus-91d6d961.js";import{H as C,e as d,_ as $,i as j,ah as i,o as c,c as k,V as e,P as t,u as G,T as f,F as J,a8 as Q,a as X,O as Y}from"./@vue-6dabbe94.js";import"./index-c9db26a7.js";import"./index-aa5b7dfa.js";import"./pinia-b6f74250.js";import"./vue-i18n-b3581196.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";import"./client-0f616fa7.js";import"./_plugin-vue_export-helper-c27b6911.js";import"./@ckeditor-2ec0d23b.js";import"./color-convert-d47c3cb3.js";import"./color-name-b7949e8c.js";import"./vanilla-colorful-3f59310b.js";const Z={class:"container"},ee=X("div",{class:"sub-title"},"结合网站模板使用，不正确填写，访问页面会出现404",-1),le=C({name:"articleWrite"}),je=C({...le,setup(te){const v=W,g=d(),n=M(),_=d(),b=d(),y=d(),T=z(),l=$({fileType:"image",fit:"fill",row:null,isShowDialog:!1,params:{},categories:[],tags:[],ruleForm:{thumbnailUrl:"",articleTag:"",attachTitle:"",title:"",commentEnable:!0,contentHtml:"",summary:"",seoKeywords:"",seoDescription:"",outLink:"",sortNum:0,thumbnail:"",suffix:"",status:"publish",articleCategory:[],attachId:null},rules:{title:{required:!0,message:"请输入文章标题",trigger:"blur"},contentHtml:{required:!0,message:"请输入文章详情",trigger:"blur"},thumbnail:{required:!0,message:"请选择缩略图",trigger:"blur"},summary:{required:!0,message:"请输入文章摘要",trigger:"blur"},seoKeywords:{required:!0,message:"请输入SEO关键词",trigger:"blur"},seoDescription:{required:!0,message:"请输入SEO描述",trigger:"blur"},status:{required:!0,message:"请选择发布状态",trigger:"blur"}}}),U=()=>{n.getArticleCategoryList().then(r=>{l.categories=r.data}),n.getArticleTagList().then(r=>{r.data.forEach(o=>{l.tags.push(o)})})},A=()=>{g.value.validate(r=>{if(r){y.value.getCheckedNodes(!0).map(s=>{l.ruleForm.articleCategory.push(s.value)});let o=P.stringify(l.ruleForm,{arrayFormat:"repeat"});n.addArticle(o).then(s=>{l.ruleForm.id=s.data,w.success("保存成功")}).catch(s=>{w({showClose:!0,message:s.message?s.message:"系统错误",type:"error"})})}})},D=r=>{n.getArticle(r).then(o=>{l.ruleForm=o.data})},E=()=>{_.value.openDialog(1)},N=()=>{b.value.openDialog(1)},q=r=>{l.ruleForm.thumbnail=r[0].filePath,l.ruleForm.thumbnailUrl=r[0].path},S=r=>{l.ruleForm.attachId=r[0].id,l.ruleForm.attachTitle=r[0].fileName},H=()=>{};return j(()=>{l.params=T,U();let r=l.params.query.id;r&&D(r)}),(r,o)=>{const s=i("el-input"),m=i("el-form-item"),u=i("el-col"),L=i("el-image"),F=i("el-link"),O=i("el-switch"),R=i("el-cascader"),p=i("el-option"),V=i("el-select"),h=i("el-row"),K=i("el-button"),B=i("el-form"),I=i("el-card");return c(),k("div",Z,[e(I,null,{default:t(()=>[e(B,{model:l.ruleForm,size:"default","label-width":"100px",rules:l.rules,ref_key:"myRefForm",ref:g},{default:t(()=>[e(h,{gutter:35},{default:t(()=>[e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"标题",prop:"title"},{default:t(()=>[e(s,{modelValue:l.ruleForm.title,"onUpdate:modelValue":o[0]||(o[0]=a=>l.ruleForm.title=a),placeholder:"请输入文章标题",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"文章详情",prop:"contentHtml"},{default:t(()=>[e(G(v),{style:{width:"100%"},modelValue:l.ruleForm.contentHtml,"onUpdate:modelValue":o[1]||(o[1]=a=>l.ruleForm.contentHtml=a)},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"缩略图",prop:"thumbnail"},{default:t(()=>[e(L,{style:{width:"100px",height:"100px"},src:l.ruleForm.thumbnailUrl,fit:l.fit,"preview-src-list":[l.ruleForm.thumbnailUrl]},null,8,["src","fit","preview-src-list"])]),_:1}),e(m,null,{default:t(()=>[e(F,{type:"primary",onClick:E},{default:t(()=>[f("选择图片")]),_:1})]),_:1})]),_:1}),e(u,{class:"mb20"},{default:t(()=>[e(m,{label:"文章摘要",prop:"summary"},{default:t(()=>[e(s,{modelValue:l.ruleForm.summary,"onUpdate:modelValue":o[2]||(o[2]=a=>l.ruleForm.summary=a),type:"textarea",rows:2,placeholder:"请输入文章摘要",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"SEO关键词",prop:"seoKeywords"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoKeywords,"onUpdate:modelValue":o[3]||(o[3]=a=>l.ruleForm.seoKeywords=a),placeholder:"请输入seo关键词",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"SEO描述",prop:"seoDescription"},{default:t(()=>[e(s,{type:"textarea",rows:2,modelValue:l.ruleForm.seoDescription,"onUpdate:modelValue":o[4]||(o[4]=a=>l.ruleForm.seoDescription=a),placeholder:"请输入SEO描述",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"排序",prop:"sortNum"},{default:t(()=>[e(s,{modelValue:l.ruleForm.sortNum,"onUpdate:modelValue":o[5]||(o[5]=a=>l.ruleForm.sortNum=a),type:"number",placeholder:"请输入排序序号",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"外链",prop:"outLink"},{default:t(()=>[e(s,{modelValue:l.ruleForm.outLink,"onUpdate:modelValue":o[6]||(o[6]=a=>l.ruleForm.outLink=a),placeholder:"请输入外链",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"模板",prop:"suffix"},{default:t(()=>[e(s,{modelValue:l.ruleForm.suffix,"onUpdate:modelValue":o[7]||(o[7]=a=>l.ruleForm.suffix=a),placeholder:"请输入文章模板后缀",clearable:""},null,8,["modelValue"]),ee]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"允许评论",prop:"commentEnable"},{default:t(()=>[e(O,{modelValue:l.ruleForm.commentEnable,"onUpdate:modelValue":o[8]||(o[8]=a=>l.ruleForm.commentEnable=a),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"分类",prop:"categories"},{default:t(()=>[e(R,{ref_key:"categoryCascader",ref:y,onChange:H,modelValue:l.ruleForm.articleCategory,"onUpdate:modelValue":o[9]||(o[9]=a=>l.ruleForm.articleCategory=a),options:l.categories,props:{multiple:!0,label:"label",value:"id",children:"children"},"collapse-tags":"",clearable:""},null,8,["modelValue","options"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"标签",prop:"tags"},{default:t(()=>[e(V,{modelValue:l.ruleForm.articleTag,"onUpdate:modelValue":o[10]||(o[10]=a=>l.ruleForm.articleTag=a),class:"w100",multiple:"",filterable:"","allow-create":"","default-first-option":"",placeholder:"可直接输入标签名称"},{default:t(()=>[(c(!0),k(J,null,Q(l.tags,a=>(c(),Y(p,{key:a.id,label:a.tagName,value:a.tagName},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"附件",prop:"attachTitle"},{default:t(()=>[e(s,{modelValue:l.ruleForm.attachTitle,"onUpdate:modelValue":o[11]||(o[11]=a=>l.ruleForm.attachTitle=a),readonly:""},null,8,["modelValue"])]),_:1}),e(m,null,{default:t(()=>[e(F,{type:"primary",onClick:N},{default:t(()=>[f("选择附件")]),_:1})]),_:1})]),_:1}),e(u,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:t(()=>[e(m,{label:"状态",prop:"status"},{default:t(()=>[e(V,{modelValue:l.ruleForm.status,"onUpdate:modelValue":o[12]||(o[12]=a=>l.ruleForm.status=a),placeholder:"请选择状态",clearable:"",class:"w100"},{default:t(()=>[e(p,{label:"发布",value:"publish"}),e(p,{label:"草稿",value:"draft"})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1}),e(h,null,{default:t(()=>[e(m,null,{default:t(()=>[e(K,{type:"primary",onClick:A,size:"default"},{default:t(()=>[f("保 存")]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1}),e(x,{ref_key:"thumbnailDialogRef",ref:_,onAttachHandler:q,fileType:l.fileType},null,8,["fileType"]),e(x,{ref_key:"attachDialogRef",ref:b,onAttachHandler:S},null,512)])}}});export{je as default};
>>>>>>> 192aa1406293d953116f56b9fc878904d9c5643d:web/src/main/resources/static/assets/js/write-804c15bf.js
