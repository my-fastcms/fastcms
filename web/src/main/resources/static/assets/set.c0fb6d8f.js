var z=Object.defineProperty;var g=Object.getOwnPropertySymbols;var j=Object.prototype.hasOwnProperty,I=Object.prototype.propertyIsEnumerable;var h=(e,a,t)=>a in e?z(e,a,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[a]=t,v=(e,a)=>{for(var t in a||(a={}))j.call(a,t)&&h(e,t,a[t]);if(g)for(var t of g(a))I.call(a,t)&&h(e,t,a[t]);return e};import{_ as O,f as R}from"./index.6210ba4b.js";import{l as B}from"./index.3de71bd1.js";import{A as U,g as N,s as P}from"./index.3c989183.js";import{r as T,j as W,J as q,o as H,t as K,l as s,m as C,z as u,A as l,b as D,D as f,Z as L,Y as $,Q as G,G as J,B as Q,C as Y,p as F,x as y}from"./vendor.7efd8838.js";import"./index.17c0aa60.js";const Z={name:"attachSet",components:{AttachDialog:U},setup(){const e=T(),{proxy:a}=J(),t=W({fit:"fill",posOptions:[{value:"leftup",label:"\u5DE6\u4E0A"},{value:"rightup",label:"\u53F3\u4E0A"},{value:"middle",label:"\u4E2D\u95F4"},{value:"leftdown",label:"\u5DE6\u4E0B"},{value:"rightdown",label:"\u53F3\u4E0B"}],ruleForm:{imageMaxSize:0,otherMaxSize:0,enableWatermark:!0,waterMarkPos:"middle",diaphaneity:0,waterMarkTxt:"",waterMarkFile:""},rules:{imageMaxSize:[{required:!0,message:"\u8BF7\u8F93\u5165\u5141\u8BB8\u4E0A\u4F20\u56FE\u7247\u5927\u5C0F\u6700\u5927\u503C",trigger:"blur"}],otherMaxSize:[{required:!0,message:"\u8BF7\u8F93\u5165\u5141\u8BB8\u4E0A\u4F20\u5176\u4ED6\u6587\u4EF6\u5927\u5C0F\u6700\u5927\u503C",trigger:"blur"}]}}),d=q(()=>R(new Date)),E=()=>{a.$refs.myRefForm.validate(o=>{if(o){let n=B.stringify(t.ruleForm,{arrayFormat:"repeat"});P(n).then(()=>{D.success("\u4FDD\u5B58\u6210\u529F")}).catch(i=>{D({showClose:!0,message:i.message?i.message:"\u7CFB\u7EDF\u9519\u8BEF",type:"error"})})}})},b=o=>{t.ruleForm.waterMarkFile=o[0].path},m=()=>{e.value.openDialog(1)};return H(()=>{let o=new Array;Object.keys(t.ruleForm).forEach(c=>{o.push(c)});let i=B.stringify({configKeys:o},{arrayFormat:"repeat"});N(i).then(c=>{c.data.forEach(_=>{t.ruleForm[_.key]=_.jsonValue})})}),v({getSelectAttach:b,attachDialogRef:e,currentTime:d,onSubmit:E,onAttachDialogOpen:m},K(t))}},p=e=>(Q("data-v-73e171bc"),e=e(),Y(),e),X={class:"personal"},ee=p(()=>F("div",{class:"personal-edit-title mb15"},"\u6587\u4EF6\u5927\u5C0F\u9650\u5236",-1)),ue=p(()=>F("div",{class:"sub-title"},"\u5355\u4F4DM\uFF0C0\u8868\u793A\u4E0D\u9650\u5236\u6587\u4EF6\u5927\u5C0F",-1)),le=p(()=>F("div",{class:"sub-title"},"\u5355\u4F4DM\uFF0C0\u8868\u793A\u4E0D\u9650\u5236\u6587\u4EF6\u5927\u5C0F",-1)),ae=p(()=>F("div",{class:"personal-edit-title mb15"},"\u6C34\u5370\u8BBE\u7F6E",-1)),te=y("\u9009\u62E9\u56FE\u7247"),oe=p(()=>F("div",{class:"sub-title"},"\u4E0A\u4F20\u6C34\u5370\u56FE\u7247\u8BF7\u5148\u5173\u95ED\u6C34\u5370\u529F\u80FD",-1)),re=y("\u4FDD \u5B58");function se(e,a,t,d,E,b){const m=s("el-input"),o=s("el-form-item"),n=s("el-col"),i=s("el-row"),c=s("el-switch"),_=s("el-option"),k=s("el-select"),A=s("el-image"),M=s("el-link"),w=s("el-button"),V=s("el-form"),S=s("el-card"),x=s("AttachDialog");return f(),C("div",X,[u(i,null,{default:l(()=>[u(n,{span:24},{default:l(()=>[u(S,{shadow:"hover"},{default:l(()=>[u(V,{model:e.ruleForm,rules:e.rules,ref:"myRefForm",size:"small","label-width":"150px",class:"mt35 mb35"},{default:l(()=>[ee,u(i,{gutter:35},{default:l(()=>[u(n,{class:"mb20"},{default:l(()=>[u(o,{label:"\u56FE\u7247\u5927\u5C0F\u8BBE\u7F6E",prop:"imageMaxSize"},{default:l(()=>[u(m,{modelValue:e.ruleForm.imageMaxSize,"onUpdate:modelValue":a[0]||(a[0]=r=>e.ruleForm.imageMaxSize=r),placeholder:"\u8BF7\u8F93\u5165\u5141\u8BB8\u4E0A\u4F20\u56FE\u7247\u7684\u6700\u5927\u503C\uFF0C\u5355\u4F4DMB",onkeyup:"value=value.replace(/[^\\d]/g,'')",clearable:""},null,8,["modelValue"]),ue]),_:1})]),_:1}),u(n,{class:"mb20"},{default:l(()=>[u(o,{label:"\u5176\u4ED6\u6587\u4EF6\u5927\u5C0F\u8BBE\u7F6E",prop:"otherMaxSize"},{default:l(()=>[u(m,{modelValue:e.ruleForm.otherMaxSize,"onUpdate:modelValue":a[1]||(a[1]=r=>e.ruleForm.otherMaxSize=r),placeholder:"\u8BF7\u8F93\u5165\u5141\u8BB8\u4E0A\u4F20\u5176\u4ED6\u6587\u4EF6\u7684\u6700\u5927\u503C\uFF0C\u5355\u4F4DMB",onkeyup:"value=value.replace(/[^\\d]/g,'')",clearable:""},null,8,["modelValue"]),le]),_:1})]),_:1})]),_:1}),ae,u(i,{gutter:35},{default:l(()=>[u(n,{class:"mb20"},{default:l(()=>[u(o,{label:"\u662F\u5426\u5F00\u542F\u6C34\u5370"},{default:l(()=>[u(c,{modelValue:e.ruleForm.enableWatermark,"onUpdate:modelValue":a[2]||(a[2]=r=>e.ruleForm.enableWatermark=r),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),u(n,{class:"mb20"},{default:l(()=>[u(o,{label:"\u6C34\u5370\u4F4D\u7F6E"},{default:l(()=>[u(k,{modelValue:e.ruleForm.waterMarkPos,"onUpdate:modelValue":a[3]||(a[3]=r=>e.ruleForm.waterMarkPos=r),placeholder:"\u8BF7\u9009\u62E9",class:"w100"},{default:l(()=>[(f(!0),C(L,null,$(e.posOptions,r=>(f(),G(_,{key:r.value,label:r.label,value:r.value},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1}),u(n,{class:"mb20"},{default:l(()=>[u(o,{label:"\u900F\u660E\u5EA6"},{default:l(()=>[u(m,{modelValue:e.ruleForm.diaphaneity,"onUpdate:modelValue":a[4]||(a[4]=r=>e.ruleForm.diaphaneity=r),placeholder:"\u8BF7\u8F93\u5165\u900F\u660E\u5EA6",onkeyup:"value=value.replace(/[^\\d]/g,'')",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),u(n,{class:"mb20"},{default:l(()=>[u(o,{label:"\u6C34\u5370\u56FE\u7247"},{default:l(()=>[u(A,{style:{width:"100px",height:"100px"},src:e.ruleForm.waterMarkFile,fit:e.fit},null,8,["src","fit"])]),_:1}),u(o,null,{default:l(()=>[u(M,{type:"primary",onClick:d.onAttachDialogOpen},{default:l(()=>[te]),_:1},8,["onClick"]),oe]),_:1})]),_:1})]),_:1}),u(n,null,{default:l(()=>[u(o,null,{default:l(()=>[u(w,{type:"primary",onClick:d.onSubmit,icon:"el-icon-position"},{default:l(()=>[re]),_:1},8,["onClick"])]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1})]),_:1})]),_:1}),u(x,{ref:"attachDialogRef",onAttachHandler:d.getSelectAttach},null,8,["onAttachHandler"])])}var pe=O(Z,[["render",se],["__scopeId","data-v-73e171bc"]]);export{pe as default};
