var b=Object.defineProperty;var p=Object.getOwnPropertySymbols;var F=Object.prototype.hasOwnProperty,g=Object.prototype.propertyIsEnumerable;var f=(e,l,o)=>l in e?b(e,l,{enumerable:!0,configurable:!0,writable:!0,value:o}):e[l]=o,_=(e,l)=>{for(var o in l||(l={}))F.call(l,o)&&f(e,o,l[o]);if(p)for(var o of p(l))g.call(l,o)&&f(e,o,l[o]);return e};import{_ as w}from"./index.4a6e935a.js";import{j as V,t as h,l as r,m as v,z as u,A as a,D as x}from"./vendor.7efd8838.js";const C={name:"pagesFormRulesTwo",setup(){const e=V({form:{},rules:{phone:{required:!0,message:"\u8BF7\u8F93\u5165\u624B\u673A",trigger:"blur"},password:{required:!0,message:"\u8BF7\u8F93\u5165\u767B\u5F55\u5BC6\u7801",trigger:"blur"},auth:{required:!0,message:"\u8BF7\u8F93\u5165\u6743\u9650\u89D2\u8272",trigger:"blur"}}});return _({},h(e))}},R={class:"form-rules-two-container"};function A(e,l,o,D,U,$){const n=r("el-input"),t=r("el-form-item"),m=r("el-col"),d=r("el-option"),i=r("el-select"),c=r("el-row"),B=r("el-form");return x(),v("div",R,[u(B,{model:e.form,rules:e.rules,ref:"formRulesTwoRef",size:"small","label-width":"100px",class:"mt35"},{default:a(()=>[u(c,{gutter:35},{default:a(()=>[u(m,{xs:24,sm:12,md:8,lg:6,xl:4,class:"mb20"},{default:a(()=>[u(t,{label:"\u624B\u673A",prop:"phone"},{default:a(()=>[u(n,{modelValue:e.form.phone,"onUpdate:modelValue":l[0]||(l[0]=s=>e.form.phone=s),placeholder:"\u8BF7\u8F93\u5165\u624B\u673A",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),u(m,{xs:24,sm:12,md:8,lg:6,xl:4,class:"mb20"},{default:a(()=>[u(t,{label:"\u6027\u522B"},{default:a(()=>[u(i,{modelValue:e.form.sex,"onUpdate:modelValue":l[1]||(l[1]=s=>e.form.sex=s),placeholder:"\u8BF7\u9009\u62E9\u6027\u522B",clearable:"",class:"w100"},{default:a(()=>[u(d,{label:"\u7537",value:"1"}),u(d,{label:"\u5973",value:"2"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),u(m,{xs:24,sm:12,md:8,lg:6,xl:4,class:"mb20"},{default:a(()=>[u(t,{label:"\u767B\u5F55\u5BC6\u7801",prop:"password"},{default:a(()=>[u(n,{modelValue:e.form.password,"onUpdate:modelValue":l[2]||(l[2]=s=>e.form.password=s),placeholder:"\u8BF7\u8F93\u5165\u767B\u5F55\u5BC6\u7801",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),u(m,{xs:24,sm:12,md:8,lg:6,xl:4,class:"mb20"},{default:a(()=>[u(t,{label:"\u6743\u9650\u89D2\u8272",prop:"auth"},{default:a(()=>[u(n,{modelValue:e.form.auth,"onUpdate:modelValue":l[3]||(l[3]=s=>e.form.auth=s),placeholder:"\u8BF7\u8F93\u5165\u6743\u9650\u89D2\u8272",clearable:""},null,8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])])}var k=w(C,[["render",A]]);export{k as default};
