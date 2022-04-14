var k=Object.defineProperty;var _=Object.getOwnPropertySymbols;var S=Object.prototype.hasOwnProperty,U=Object.prototype.propertyIsEnumerable;var g=(l,u,n)=>u in l?k(l,u,{enumerable:!0,configurable:!0,writable:!0,value:n}):l[u]=n,B=(l,u)=>{for(var n in u||(u={}))S.call(u,n)&&g(l,n,u[n]);if(_)for(var n of _(u))U.call(u,n)&&g(l,n,u[n]);return l};import{r as j,M as x,j as q,o as H,t as O,l as s,m as R,z as e,A as o,b as E,D as K,G as T,x as V,p as z}from"./vendor.d2ed1f2c.js";import{A as I}from"./index.450f86fb.js";import{c as M,e as N}from"./index.e27d3591.js";import{l as P}from"./index.6a67ebb2.js";import{C as $}from"./index.234db5ef.js";import{_ as G}from"./index.fd217d0f.js";import"./index.b7504bb1.js";const W={name:"pageWrite",components:{AttachDialog:I,ckeditor:$},setup(){const l=j(),u=x(),{proxy:n}=T(),m=q({fileType:"image",fit:"fill",params:{},ruleForm:{title:"",path:"",commentEnable:1,contentHtml:"",status:"publish"},rules:{title:{required:!0,message:"\u8BF7\u8F93\u5165\u9875\u9762\u6807\u9898",trigger:"blur"},path:{required:!0,message:"\u8BF7\u8F93\u5165\u9875\u9762\u8BBF\u95EE\u8DEF\u5F84",trigger:"blur"},contentHtml:{required:!0,message:"\u8BF7\u8F93\u5165\u9875\u9762\u8BE6\u60C5",trigger:"blur"},seoKeywords:{required:!0,message:"\u8BF7\u8F93\u5165SEO\u5173\u952E\u8BCD",trigger:"blur"},seoDescription:{required:!0,message:"\u8BF7\u8F93\u5165SEO\u63CF\u8FF0",trigger:"blur"},status:{required:!0,message:"\u8BF7\u9009\u62E9\u53D1\u5E03\u72B6\u6001",trigger:"blur"}}}),f=()=>{n.$refs.myRefForm.validate(t=>{if(t){let p=P.stringify(m.ruleForm,{arrayFormat:"repeat"});M(p).then(i=>{m.ruleForm.id=i.data,E.success("\u4FDD\u5B58\u6210\u529F")}).catch(i=>{E({showClose:!0,message:i.message?i.message:"\u7CFB\u7EDF\u9519\u8BEF",type:"error"})})}})},F=t=>{N(t).then(p=>{m.ruleForm=p.data})},d=()=>{l.value.openDialog(1)},r=t=>{m.ruleForm.thumbnail=t[0].path};return H(()=>{m.params=u;let t=m.params.query.id;t&&F(t)}),B({attachDialogRef:l,onSubmit:f,onAttachDialogOpen:d,getSelectAttach:r,getPageInfo:F},O(m))}},J={class:"container"},L=V("\u9009\u62E9\u56FE\u7247"),Q=z("div",{class:"sub-title"},"\u7ED3\u5408\u7F51\u7AD9\u6A21\u677F\u4F7F\u7528\uFF0C\u4E0D\u6B63\u786E\u586B\u5199\uFF0C\u8BBF\u95EE\u9875\u9762\u4F1A\u51FA\u73B0404",-1),X=V("\u4FDD \u5B58");function Y(l,u,n,m,f,F){const d=s("el-input"),r=s("el-form-item"),t=s("el-col"),p=s("ckeditor"),i=s("el-image"),D=s("el-link"),y=s("el-switch"),c=s("el-option"),h=s("el-select"),b=s("el-row"),C=s("el-button"),A=s("el-form"),w=s("el-card"),v=s("AttachDialog");return K(),R("div",J,[e(w,null,{default:o(()=>[e(A,{model:l.ruleForm,size:"small","label-width":"100px",rules:l.rules,ref:"myRefForm"},{default:o(()=>[e(b,{gutter:35},{default:o(()=>[e(t,{class:"mb20"},{default:o(()=>[e(r,{label:"\u6807\u9898",prop:"title"},{default:o(()=>[e(d,{modelValue:l.ruleForm.title,"onUpdate:modelValue":u[0]||(u[0]=a=>l.ruleForm.title=a),placeholder:"\u8BF7\u8F93\u5165\u9875\u9762\u6807\u9898",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{class:"mb20"},{default:o(()=>[e(r,{label:"\u8BBF\u95EE\u8DEF\u5F84",prop:"path"},{default:o(()=>[e(d,{modelValue:l.ruleForm.path,"onUpdate:modelValue":u[1]||(u[1]=a=>l.ruleForm.path=a),placeholder:"\u8BF7\u8F93\u5165\u9875\u9762\u8BBF\u95EE\u8DEF\u5F84",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{class:"mb20"},{default:o(()=>[e(r,{label:"\u9875\u9762\u8BE6\u60C5",prop:"contentHtml"},{default:o(()=>[e(p,{modelValue:l.ruleForm.contentHtml,"onUpdate:modelValue":u[2]||(u[2]=a=>l.ruleForm.contentHtml=a)},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{class:"mb20"},{default:o(()=>[e(r,{label:"\u7F29\u7565\u56FE",prop:"thumbnail"},{default:o(()=>[e(i,{style:{width:"100px",height:"100px"},src:l.ruleForm.thumbnail,fit:l.fit},null,8,["src","fit"])]),_:1}),e(r,null,{default:o(()=>[e(D,{type:"primary",onClick:m.onAttachDialogOpen},{default:o(()=>[L]),_:1},8,["onClick"])]),_:1})]),_:1}),e(t,{class:"mb20"},{default:o(()=>[e(r,{label:"\u9875\u9762\u6458\u8981",prop:"summary"},{default:o(()=>[e(d,{modelValue:l.ruleForm.summary,"onUpdate:modelValue":u[3]||(u[3]=a=>l.ruleForm.summary=a),type:"textarea",rows:2,placeholder:"\u8BF7\u8F93\u5165\u6587\u7AE0\u6458\u8981",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"SEO\u5173\u952E\u8BCD",prop:"seoKeywords"},{default:o(()=>[e(d,{type:"textarea",rows:2,modelValue:l.ruleForm.seoKeywords,"onUpdate:modelValue":u[4]||(u[4]=a=>l.ruleForm.seoKeywords=a),placeholder:"\u8BF7\u8F93\u5165seo\u5173\u952E\u8BCD",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"SEO\u63CF\u8FF0",prop:"seoDescription"},{default:o(()=>[e(d,{type:"textarea",rows:2,modelValue:l.ruleForm.seoDescription,"onUpdate:modelValue":u[5]||(u[5]=a=>l.ruleForm.seoDescription=a),placeholder:"\u8BF7\u8F93\u5165SEO\u63CF\u8FF0",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"\u6A21\u677F",prop:"suffix"},{default:o(()=>[e(d,{modelValue:l.ruleForm.suffix,"onUpdate:modelValue":u[6]||(u[6]=a=>l.ruleForm.suffix=a),placeholder:"\u8BF7\u8F93\u5165\u9875\u9762\u6A21\u677F\u540E\u7F00",clearable:""},null,8,["modelValue"]),Q]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[e(r,{label:"\u5141\u8BB8\u8BC4\u8BBA",prop:"commentEnable"},{default:o(()=>[e(y,{modelValue:l.ruleForm.commentEnable,"onUpdate:modelValue":u[7]||(u[7]=a=>l.ruleForm.commentEnable=a),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{class:"mb20"},{default:o(()=>[e(r,{label:"\u72B6\u6001",prop:"status"},{default:o(()=>[e(h,{modelValue:l.ruleForm.status,"onUpdate:modelValue":u[8]||(u[8]=a=>l.ruleForm.status=a),placeholder:"\u8BF7\u9009\u62E9\u72B6\u6001",clearable:"",class:"w100"},{default:o(()=>[e(c,{label:"\u53D1\u5E03",value:"publish"}),e(c,{label:"\u8349\u7A3F",value:"draft"})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1}),e(b,null,{default:o(()=>[e(r,null,{default:o(()=>[e(C,{type:"primary",onClick:m.onSubmit,size:"small"},{default:o(()=>[X]),_:1},8,["onClick"])]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1}),e(v,{ref:"attachDialogRef",onAttachHandler:m.getSelectAttach,fileType:l.fileType},null,8,["onAttachHandler","fileType"])])}var se=G(W,[["render",Y]]);export{se as default};