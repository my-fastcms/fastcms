var B=Object.defineProperty;var w=Object.getOwnPropertySymbols;var A=Object.prototype.hasOwnProperty,k=Object.prototype.propertyIsEnumerable;var b=(o,e,a)=>e in o?B(o,e,{enumerable:!0,configurable:!0,writable:!0,value:a}):o[e]=a,D=(o,e)=>{for(var a in e||(e={}))A.call(e,a)&&b(o,a,e[a]);if(w)for(var a of w(e))k.call(e,a)&&b(o,a,e[a]);return o};import{b as y,u as T,e as z}from"./index.7816d20e.js";import{_ as U,S as R}from"./index.d1227e45.js";import{j,o as H,t as I,l as r,m as M,z as s,A as u,E as v,b as c,D as p,p as N,Q as _,L as f,x as m}from"./vendor.d2ed1f2c.js";const S={name:"articleManager",setup(){const o=j({tableData:[],limit:1,uploadUrl:"fastcms/api/admin/template/install",headers:{Authorization:R.get("token")}}),e=()=>{y().then(t=>{o.tableData=t.data}).catch(()=>{})},a=t=>{v.confirm("\u6B64\u64CD\u4F5C\u5C06\u5378\u8F7D\u6B64["+t.id+"]\u6A21\u677F, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u5378\u8F7D",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{T(t.id).then(()=>{c.success("\u5378\u8F7D\u6210\u529F"),e()}).catch(n=>{c.error(n.message)})}).catch(()=>{})},l=t=>{v.confirm("\u786E\u8BA4\u542F\u7528["+t.id+"]\u6A21\u677F\u5417?","\u63D0\u793A",{confirmButtonText:"\u542F\u7528",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{z(t.id).then(()=>{c.success("\u542F\u7528\u6210\u529F"),e()}).catch(n=>{c.error(n.message)})}).catch(()=>{})},h=t=>{t.code==200?(c.success("\u4E0A\u4F20\u6210\u529F"),e()):c.error(t.message)},F=t=>{c.error("\u4E0A\u4F20\u5931\u8D25,"+t)},i=()=>{};return H(()=>{e()}),D({onRowUnInstall:a,onRowEnable:l,initTableData:e,uploadSuccess:h,onHandleUploadError:F,onHandleExceed:i},I(o))}},V={class:"mb15"},L=m("\u5B89\u88C5\u6A21\u677F"),Q=m("\u4F7F\u7528\u4E2D"),$=m("\u542F\u7528"),q=m("\u5378\u8F7D");function G(o,e,a,l,h,F){const i=r("el-button"),t=r("el-upload"),n=r("el-table-column"),E=r("el-tag"),x=r("el-table"),C=r("el-card");return p(),M("div",null,[s(C,{shadow:"hover"},{default:u(()=>[N("div",V,[s(t,{action:o.uploadUrl,name:"file",headers:o.headers,"show-file-list":!1,"on-success":l.uploadSuccess,"on-error":l.onHandleUploadError,"on-exceed":l.onHandleExceed,accept:".zip"},{default:u(()=>[s(i,{class:"mt15",size:"small",type:"primary",icon:"iconfont icon-shuxingtu"},{default:u(()=>[L]),_:1})]),_:1},8,["action","headers","on-success","on-error","on-exceed"])]),s(x,{data:o.tableData,stripe:"",style:{width:"100%"}},{default:u(()=>[s(n,{prop:"id",label:"ID","show-overflow-tooltip":""}),s(n,{prop:"name",label:"\u6A21\u677F\u540D\u79F0","show-overflow-tooltip":""}),s(n,{prop:"path",label:"\u6A21\u677F\u8DEF\u5F84","show-overflow-tooltip":""}),s(n,{prop:"version",label:"\u7248\u672C","show-overflow-tooltip":""}),s(n,{prop:"provider",label:"\u4F5C\u8005","show-overflow-tooltip":""}),s(n,{prop:"description",label:"\u63CF\u8FF0","show-overflow-tooltip":""}),s(n,{label:"\u64CD\u4F5C",width:"90"},{default:u(d=>[d.row.active==!0?(p(),_(E,{key:0,type:"success"},{default:u(()=>[Q]),_:1})):f("",!0),d.row.active==!1?(p(),_(i,{key:1,size:"mini",type:"text",onClick:g=>l.onRowEnable(d.row)},{default:u(()=>[$]),_:2},1032,["onClick"])):f("",!0),d.row.active==!1?(p(),_(i,{key:2,size:"mini",type:"text",onClick:g=>l.onRowUnInstall(d.row)},{default:u(()=>[q]),_:2},1032,["onClick"])):f("",!0)]),_:1})]),_:1},8,["data"])]),_:1})])}var W=U(S,[["render",G]]);export{W as default};