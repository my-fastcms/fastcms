import{q as f}from"./qs-c5b6dbf2.js";import{C as F}from"./index-cc181d37.js";import{a as _}from"./element-plus-91d6d961.js";import{H as d,e as h,_ as x,i as v,ah as r,o as V,c as w,V as e,P as t,T as C}from"./@vue-6dabbe94.js";import{_ as E}from"./_plugin-vue_export-helper-c27b6911.js";import"./@intlify-ea47d1db.js";import"./source-map-7d7e1c08.js";import"./side-channel-394f276c.js";import"./get-intrinsic-bd2830fd.js";import"./has-symbols-e8f3ca0e.js";import"./has-proto-f7d0b240.js";import"./function-bind-22e7ee79.js";import"./has-26d28e02.js";import"./call-bind-e5c1c8b0.js";import"./object-inspect-8fd4bade.js";import"./index-b6a6dad6.js";import"./pinia-b6f74250.js";import"./vue-router-c1461dfc.js";import"./vue-i18n-b3581196.js";import"./vue-b1ba6f14.js";import"./js-cookie-edb2da2a.js";import"./@element-plus-64e6e14a.js";import"./nprogress-08a53ce8.js";import"./axios-4a70c6fc.js";import"./mitt-f7ef348c.js";import"./vue-grid-layout-ec2a62c3.js";import"./lodash-es-9851428c.js";import"./@vueuse-e129c873.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-f6fdf7b4.js";import"./async-validator-dee29e8b.js";import"./memoize-one-297ddbcb.js";import"./escape-html-b8998962.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-880a26aa.js";const q={class:"personal"},A=d({name:"templateSet"}),j=d({...A,setup(B){const p=F(),c=h(),o=x({fit:"fill",ruleForm:{enableStaticWebSite:!1,enableFakeStatic:!1,fakeStaticSuffix:".html"},rules:{fakeStaticSuffix:[{required:!0,message:"请输入伪静态访问后缀",trigger:"blur"}]}}),y=()=>{c.value.validate(i=>{if(i){let a=f.stringify(o.ruleForm,{arrayFormat:"repeat"});p.saveConfig(a).then(()=>{_.success("保存成功")}).catch(s=>{_({showClose:!0,message:s.message?s.message:"系统错误",type:"error"})})}})};return v(()=>{let i=new Array;Object.keys(o.ruleForm).forEach(l=>{i.push(l)});let s=f.stringify({configKeys:i},{arrayFormat:"repeat"});p.getConfigList(s).then(l=>{l.data.forEach(m=>{o.ruleForm[m.key]=m.jsonValue})})}),(i,a)=>{const s=r("el-switch"),l=r("el-form-item"),m=r("el-col"),b=r("el-input"),u=r("el-row"),g=r("el-button"),S=r("el-form"),k=r("el-card");return V(),w("div",q,[e(u,null,{default:t(()=>[e(m,{span:24},{default:t(()=>[e(k,{shadow:"hover"},{default:t(()=>[e(S,{model:o.ruleForm,rules:o.rules,ref_key:"myRefForm",ref:c,size:"small","label-width":"150px",class:"mt35 mb35"},{default:t(()=>[e(u,{gutter:35},{default:t(()=>[e(m,{class:"mb20"},{default:t(()=>[e(l,{label:"是否开启伪静态"},{default:t(()=>[e(s,{modelValue:o.ruleForm.enableFakeStatic,"onUpdate:modelValue":a[0]||(a[0]=n=>o.ruleForm.enableFakeStatic=n),"active-color":"#13ce66"},null,8,["modelValue"])]),_:1})]),_:1}),e(m,{class:"mb20"},{default:t(()=>[e(l,{label:"伪静态访问后缀",prop:"fakeStaticSuffix"},{default:t(()=>[e(b,{modelValue:o.ruleForm.fakeStaticSuffix,"onUpdate:modelValue":a[1]||(a[1]=n=>o.ruleForm.fakeStaticSuffix=n),placeholder:"请输入伪静态访问后缀",readonly:"",clearable:""},null,8,["modelValue"])]),_:1})]),_:1})]),_:1}),e(m,null,{default:t(()=>[e(l,null,{default:t(()=>[e(g,{type:"primary",onClick:y,icon:"el-icon-position"},{default:t(()=>[C("保 存")]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1})]),_:1})]),_:1})])}}});const de=E(j,[["__scopeId","data-v-60233e7f"]]);export{de as default};