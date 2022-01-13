var k=Object.defineProperty;var g=Object.getOwnPropertySymbols;var A=Object.prototype.hasOwnProperty,B=Object.prototype.propertyIsEnumerable;var V=(l,u,n)=>u in l?k(l,u,{enumerable:!0,configurable:!0,writable:!0,value:n}):l[u]=n,E=(l,u)=>{for(var n in u||(u={}))A.call(u,n)&&V(l,n,u[n]);if(g)for(var n of g(u))B.call(u,n)&&V(l,n,u[n]);return l};import{I as S}from"./index.a85c5e3e.js";import{_ as x,H as L}from"./index.2e163b1f.js";import{j as I,t as h,l as i,m as U,z as e,A as a,b as y,D as H,p as K,G as j,x as D}from"./vendor.14a30896.js";const q={name:"systemEditMenu",components:{IconSelector:S},setup(l,u){const{proxy:n}=j(),o=I({isShowDialog:!1,ruleForm:{id:null,name:"",path:"",component:"",menuSort:"",title:"",icon:"",isHide:"",isKeepAlive:"",isAffix:"",isLink:"",isIframe:""},rules:{name:{required:!0,message:"\u8BF7\u8F93\u5165\u8DEF\u7531\u540D\u79F0",trigger:"blur"},path:{required:!0,message:"\u8BF7\u8F93\u5165\u8DEF\u7531\u5730\u5740",trigger:"blur"},title:{required:!0,message:"\u8BF7\u8F93\u5165\u83DC\u5355\u540D\u79F0",trigger:"blur"},component:{required:!0,message:"\u8BF7\u8F93\u5165\u7EC4\u4EF6\u5730\u5740",trigger:"blur"}}}),b=r=>{o.ruleForm.id=r.id,o.ruleForm.name=r.name,o.ruleForm.path=r.path,o.ruleForm.component=r.component,o.ruleForm.isLink=r.isLink?"true":"",o.ruleForm.menuSort="",o.ruleForm.title=r.meta.title,o.ruleForm.icon=r.meta.icon,o.ruleForm.isHide=r.meta.isHide?"true":"false",o.ruleForm.isKeepAlive=r.meta.isKeepAlive?"true":"false",o.ruleForm.isAffix=r.meta.isAffix?"true":"false",o.ruleForm.isLink=r.meta.isLink?r.isLink:"",o.ruleForm.isIframe=r.meta.isIframe?"true":"",o.isShowDialog=!0},p=r=>{console.log(r),o.isShowDialog=!1},d=()=>{o.ruleForm.isIframe==="true"?o.ruleForm.isLink="true":o.ruleForm.isLink=""},s=()=>{p(),f()},t=()=>{n.$refs.myRefForm.validate(r=>{r&&L(o.ruleForm).then(()=>{p(),u.emit("reloadTable")}).catch(F=>{y({showClose:!0,message:F.message?F.message:"\u7CFB\u7EDF\u9519\u8BEF",type:"error"})})})},f=()=>{o.ruleForm.name="",o.ruleForm.component="",o.ruleForm.isLink="",o.ruleForm.menuSort="",o.ruleForm.title="",o.ruleForm.icon="",o.ruleForm.isHide="",o.ruleForm.isKeepAlive="",o.ruleForm.isAffix="",o.ruleForm.isLink="",o.ruleForm.isIframe=""};return E({openDialog:b,closeDialog:p,onSelectIframeChange:d,onCancel:s,onSubmit:t},h(o))}},w={class:"system-menu-container"},z={class:"dialog-footer"},M=D("\u53D6 \u6D88"),N=D("\u7F16 \u8F91");function R(l,u,n,o,b,p){const d=i("el-input"),s=i("el-form-item"),t=i("el-col"),f=i("IconSelector"),r=i("el-option"),F=i("el-select"),C=i("el-row"),_=i("el-form"),c=i("el-button"),v=i("el-dialog");return H(),U("div",w,[e(v,{title:"\u7F16\u8F91\u8DEF\u7531",modelValue:l.isShowDialog,"onUpdate:modelValue":u[12]||(u[12]=m=>l.isShowDialog=m),width:"769px"},{footer:a(()=>[K("span",z,[e(c,{onClick:o.onCancel,size:"small"},{default:a(()=>[M]),_:1},8,["onClick"]),e(c,{type:"primary",onClick:o.onSubmit,size:"small"},{default:a(()=>[N]),_:1},8,["onClick"])])]),default:a(()=>[e(_,{model:l.ruleForm,size:"small","label-width":"80px",rules:l.rules,ref:"myRefForm"},{default:a(()=>[e(C,{gutter:35},{default:a(()=>[e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u83DC\u5355\u540D\u79F0",prop:"title"},{default:a(()=>[e(d,{modelValue:l.ruleForm.title,"onUpdate:modelValue":u[0]||(u[0]=m=>l.ruleForm.title=m),placeholder:"\u683C\u5F0F\uFF1Amessage.router.xxx",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u83DC\u5355\u56FE\u6807",prop:"icon"},{default:a(()=>[e(f,{placeholder:"\u8BF7\u8F93\u5165\u83DC\u5355\u56FE\u6807",modelValue:l.ruleForm.icon,"onUpdate:modelValue":u[1]||(u[1]=m=>l.ruleForm.icon=m)},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u8DEF\u7531\u540D\u79F0",prop:"name"},{default:a(()=>[e(d,{modelValue:l.ruleForm.name,"onUpdate:modelValue":u[2]||(u[2]=m=>l.ruleForm.name=m),placeholder:"\u8DEF\u7531\u540D\u79F0\uFF08\u8DEF\u7531\u4E2D\u7684name\u503C\uFF09",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u8DEF\u7531\u5730\u5740",prop:"path"},{default:a(()=>[e(d,{modelValue:l.ruleForm.path,"onUpdate:modelValue":u[3]||(u[3]=m=>l.ruleForm.path=m),placeholder:"\u8DEF\u7531\u5730\u5740",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u7EC4\u4EF6\u5730\u5740",prop:"component"},{default:a(()=>[e(d,{modelValue:l.ruleForm.component,"onUpdate:modelValue":u[4]||(u[4]=m=>l.ruleForm.component=m),placeholder:"\u7EC4\u4EF6\u5730\u5740",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u662F\u5426\u9690\u85CF"},{default:a(()=>[e(F,{modelValue:l.ruleForm.isHide,"onUpdate:modelValue":u[5]||(u[5]=m=>l.ruleForm.isHide=m),placeholder:"\u8BF7\u9009\u62E9\u662F\u5426\u9690\u85CF",clearable:"",class:"w100"},{default:a(()=>[e(r,{label:"\u662F",value:"true"}),e(r,{label:"\u5426",value:"false"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u662F\u5426\u7F13\u5B58"},{default:a(()=>[e(F,{modelValue:l.ruleForm.isKeepAlive,"onUpdate:modelValue":u[6]||(u[6]=m=>l.ruleForm.isKeepAlive=m),placeholder:"\u8BF7\u9009\u62E9\u662F\u5426\u7F13\u5B58",clearable:"",class:"w100"},{default:a(()=>[e(r,{label:"\u662F",value:"true"}),e(r,{label:"\u5426",value:"false"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u662F\u5426\u56FA\u5B9A"},{default:a(()=>[e(F,{modelValue:l.ruleForm.isAffix,"onUpdate:modelValue":u[7]||(u[7]=m=>l.ruleForm.isAffix=m),placeholder:"\u8BF7\u9009\u62E9\u662F\u5426\u56FA\u5B9A",clearable:"",class:"w100"},{default:a(()=>[e(r,{label:"\u662F",value:"true"}),e(r,{label:"\u5426",value:"false"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u662F\u5426\u5916\u94FE"},{default:a(()=>[e(F,{modelValue:l.ruleForm.isLink,"onUpdate:modelValue":u[8]||(u[8]=m=>l.ruleForm.isLink=m),placeholder:"\u8BF7\u9009\u62E9\u662F\u5426\u5916\u94FE",clearable:"",class:"w100",disabled:l.ruleForm.isIframe==="true"},{default:a(()=>[e(r,{label:"\u662F",value:"true"}),e(r,{label:"\u5426",value:"false"})]),_:1},8,["modelValue","disabled"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u662F\u5426\u5185\u5D4C"},{default:a(()=>[e(F,{modelValue:l.ruleForm.isIframe,"onUpdate:modelValue":u[9]||(u[9]=m=>l.ruleForm.isIframe=m),placeholder:"\u8BF7\u9009\u62E9\u662F\u5426iframe",clearable:"",class:"w100",onChange:o.onSelectIframeChange},{default:a(()=>[e(r,{label:"\u662F",value:"true"}),e(r,{label:"\u5426",value:"false"})]),_:1},8,["modelValue","onChange"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u94FE\u63A5\u5730\u5740"},{default:a(()=>[e(d,{modelValue:l.ruleForm.isLink,"onUpdate:modelValue":u[10]||(u[10]=m=>l.ruleForm.isLink=m),placeholder:"\u5916\u94FE/\u5185\u5D4C\u65F6\u94FE\u63A5\u5730\u5740\uFF08http:xxx.com\uFF09",clearable:"",disabled:l.ruleForm.isLink===""||l.ruleForm.isLink==="false"},null,8,["modelValue","disabled"])]),_:1})]),_:1}),e(t,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:a(()=>[e(s,{label:"\u83DC\u5355\u6392\u5E8F"},{default:a(()=>[e(d,{modelValue:l.ruleForm.menuSort,"onUpdate:modelValue":u[11]||(u[11]=m=>l.ruleForm.menuSort=m),type:"number",placeholder:"\u83DC\u5355\u6392\u5E8F",clearable:""},null,8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["modelValue"])])}var O=x(q,[["render",R]]);export{O as default};
