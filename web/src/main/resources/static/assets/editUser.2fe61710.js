var w=Object.defineProperty;var y=Object.getOwnPropertySymbols;var h=Object.prototype.hasOwnProperty,A=Object.prototype.propertyIsEnumerable;var C=(a,u,t)=>u in a?w(a,u,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[u]=t,N=(a,u)=>{for(var t in u||(u={}))h.call(u,t)&&C(a,t,u[t]);if(y)for(var t of y(u))A.call(u,t)&&C(a,t,u[t]);return a};import{s as v,c as L,r as x,a as S,b as T}from"./index.1736b408.js";import{l as _}from"./index.34a00848.js";import{_ as z}from"./index.58e32a1e.js";import{j as R,o as j,a8 as I,t as M,l as n,m as U,z as l,A as o,b,E as k,D,p as P,Y as q,X as G,Q,G as X,x as B}from"./vendor.07d41a1e.js";const Y={name:"systemEditUser",setup(a,u){const{proxy:t}=X(),e=R({loading:!1,isShowDialog:!1,deptList:[],tags:[],roleList:[],ruleForm:{id:null,userName:"",nickName:"",mobile:"",email:"",company:"",address:"",sex:"1",status:"1",tagList:"",roleList:""},rules:{userName:{required:!0,message:"\u8BF7\u8F93\u5165\u8D26\u53F7",trigger:"blur"},nickName:{required:!0,message:"\u8BF7\u8F93\u5165\u6635\u79F0",trigger:"blur"}}}),E=r=>{console.log(r),e.isShowDialog=!0,e.ruleForm.id=r.id},c=r=>{console.log(r),e.isShowDialog=!1},i=()=>{c(),g()},m=()=>{t.$refs.myRefForm.validate(r=>{if(r){let p=_.stringify(e.ruleForm,{arrayFormat:"repeat"});v(p).then(()=>{c(),g(),u.emit("reloadTable")}).catch(s=>{b({showClose:!0,message:s.message?s.message:"\u7CFB\u7EDF\u9519\u8BEF",type:"error"})})}})},d=()=>{k.confirm("\u786E\u5B9A\u5C06\u7528\u6237["+e.ruleForm.userName+"]\u8BBE\u7F6E\u4E3A\u5458\u5DE5\u5417?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{e.loading=!0;let r=_.stringify({userId:e.ruleForm.id,userType:1},{arrayFormat:"repeat"});L(r).then(()=>{e.loading=!1,b.success("\u8BBE\u7F6E\u6210\u529F"),c(),g(),u.emit("reloadTable")}).catch(p=>{e.loading=!1,b.error(p.message)})}).catch(()=>{})},F=()=>{k.confirm("\u786E\u5B9A\u91CD\u7F6E\u5458\u5DE5["+e.ruleForm.userName+"]\u8D26\u53F7\u5BC6\u7801\u5417?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{e.loading=!0;let r=_.stringify({userId:e.ruleForm.id},{arrayFormat:"repeat"});x(r).then(()=>{e.loading=!1,b.success("\u64CD\u4F5C\u6210\u529F"),c(),g(),u.emit("reloadTable")}).catch(p=>{e.loading=!1,b.error(p.message)})}).catch(()=>{})},f=()=>{S().then(r=>{e.tags=r.data})},V=()=>{e.ruleForm.id&&e.ruleForm.id!=null&&T(e.ruleForm.id).then(r=>{e.ruleForm.id=r.data.id,e.ruleForm.userName=r.data.userName,e.ruleForm.nickName=r.data.nickName,e.ruleForm.mobile=r.data.mobile,e.ruleForm.email=r.data.email,e.ruleForm.company=r.data.company,e.ruleForm.address=r.data.address,e.ruleForm.sex=r.data.sex+"",e.ruleForm.status=r.data.status+"",e.ruleForm.tagList=r.data.tagList})};j(()=>{f()}),I(()=>{V()});const g=()=>{e.ruleForm.id=null,e.ruleForm.userName="",e.ruleForm.nickName="",e.ruleForm.mobile="",e.ruleForm.email="",e.ruleForm.company="",e.ruleForm.address="",e.ruleForm.sex="",e.ruleForm.status="",e.ruleForm.roleList="",e.ruleForm.deptList=""};return N({openDialog:E,closeDialog:c,onCancel:i,onSubmit:m,onSetSystemUser:d,onRestUserPassword:F,loadRoleList:f},M(e))}},$={class:"system-menu-container"},H={class:"dialog-footer"},J=B("\u53D6 \u6D88"),K=B("\u8BBE\u4E3A\u5458\u5DE5"),O=B("\u91CD\u7F6E\u5BC6\u7801"),W=B("\u4FDD \u5B58");function Z(a,u,t,e,E,c){const i=n("el-input"),m=n("el-form-item"),d=n("el-col"),F=n("el-option"),f=n("el-select"),V=n("el-row"),g=n("el-form"),r=n("el-button"),p=n("el-dialog");return D(),U("div",$,[l(p,{title:"\u7F16\u8F91\u7528\u6237",modelValue:a.isShowDialog,"onUpdate:modelValue":u[9]||(u[9]=s=>a.isShowDialog=s),width:"769px"},{footer:o(()=>[P("span",H,[l(r,{onClick:e.onCancel,size:"small"},{default:o(()=>[J]),_:1},8,["onClick"]),l(r,{loading:a.loading,onClick:e.onSetSystemUser,size:"small"},{default:o(()=>[K]),_:1},8,["loading","onClick"]),l(r,{loading:a.loading,onClick:e.onRestUserPassword,size:"small"},{default:o(()=>[O]),_:1},8,["loading","onClick"]),l(r,{type:"primary",onClick:e.onSubmit,size:"small"},{default:o(()=>[W]),_:1},8,["onClick"])])]),default:o(()=>[l(g,{model:a.ruleForm,size:"small","label-width":"80px",rules:a.rules,ref:"myRefForm"},{default:o(()=>[l(V,{gutter:35},{default:o(()=>[l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u8D26\u53F7",prop:"userName"},{default:o(()=>[l(i,{modelValue:a.ruleForm.userName,"onUpdate:modelValue":u[0]||(u[0]=s=>a.ruleForm.userName=s),readonly:"",placeholder:"\u8BF7\u8F93\u5165\u8D26\u53F7"},null,8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u6635\u79F0",prop:"nickName"},{default:o(()=>[l(i,{modelValue:a.ruleForm.nickName,"onUpdate:modelValue":u[1]||(u[1]=s=>a.ruleForm.nickName=s),placeholder:"\u8BF7\u8F93\u5165\u6635\u79F0",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u624B\u673A\u53F7\u7801",prop:"mobile"},{default:o(()=>[l(i,{modelValue:a.ruleForm.mobile,"onUpdate:modelValue":u[2]||(u[2]=s=>a.ruleForm.mobile=s),placeholder:"\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u7535\u5B50\u90AE\u7BB1",prop:"email"},{default:o(()=>[l(i,{modelValue:a.ruleForm.email,"onUpdate:modelValue":u[3]||(u[3]=s=>a.ruleForm.email=s),placeholder:"\u8BF7\u8F93\u5165\u7535\u5B50\u90AE\u7BB1",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u516C\u53F8",prop:"company"},{default:o(()=>[l(i,{modelValue:a.ruleForm.company,"onUpdate:modelValue":u[4]||(u[4]=s=>a.ruleForm.company=s),placeholder:"\u8BF7\u8F93\u5165\u516C\u53F8\u540D\u79F0",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u5730\u5740",prop:"address"},{default:o(()=>[l(i,{modelValue:a.ruleForm.address,"onUpdate:modelValue":u[5]||(u[5]=s=>a.ruleForm.address=s),placeholder:"\u8BF7\u8F93\u5165\u5730\u5740",clearable:""},null,8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u6027\u522B"},{default:o(()=>[l(f,{modelValue:a.ruleForm.sex,"onUpdate:modelValue":u[6]||(u[6]=s=>a.ruleForm.sex=s),placeholder:"\u8BF7\u9009\u62E9\u6027\u522B",clearable:"",class:"w100"},{default:o(()=>[l(F,{label:"\u7537",value:"1"}),l(F,{label:"\u5973",value:"0"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),l(d,{xs:24,sm:12,md:12,lg:12,xl:12,class:"mb20"},{default:o(()=>[l(m,{label:"\u72B6\u6001"},{default:o(()=>[l(f,{modelValue:a.ruleForm.status,"onUpdate:modelValue":u[7]||(u[7]=s=>a.ruleForm.status=s),placeholder:"\u8BF7\u9009\u62E9\u72B6\u6001",clearable:"",class:"w100"},{default:o(()=>[l(F,{label:"\u542F\u7528",value:"1"}),l(F,{label:"\u7981\u7528",value:"0"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),l(d,{class:"mb20"},{default:o(()=>[l(m,{label:"\u7528\u6237\u6807\u7B7E"},{default:o(()=>[l(f,{modelValue:a.ruleForm.tagList,"onUpdate:modelValue":u[8]||(u[8]=s=>a.ruleForm.tagList=s),class:"w100",multiple:"",filterable:"","allow-create":"","default-first-option":"",placeholder:"\u53EF\u76F4\u63A5\u8F93\u5165\u6807\u7B7E\u540D\u79F0"},{default:o(()=>[(D(!0),U(q,null,G(a.tags,s=>(D(),Q(F,{key:s.id,label:s.title,value:s.title},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["modelValue"])])}var re=z(Y,[["render",Z]]);export{re as default};