var S=Object.defineProperty;var w=Object.getOwnPropertySymbols;var U=Object.prototype.hasOwnProperty,B=Object.prototype.propertyIsEnumerable;var F=(e,o,n)=>o in e?S(e,o,{enumerable:!0,configurable:!0,writable:!0,value:n}):e[o]=n,E=(e,o)=>{for(var n in o||(o={}))U.call(o,n)&&F(e,n,o[n]);if(w)for(var n of w(o))B.call(o,n)&&F(e,n,o[n]);return e};import{s as b,_ as R,S as v}from"./index.2e163b1f.js";import{r as k,j as x,o as I,t as y,l as u,m as H,z as t,A as s,E as N,b as c,D as V,p as z,x as D}from"./vendor.14a30896.js";function j(e){return b({url:"/admin/plugin/list",method:"get",params:e})}function M(e){return b({url:"/admin/plugin/unInstall/"+e,method:"post"})}function T(e){return b({url:"/admin/plugin/config/url/"+e,method:"get"})}const A={name:"pluginManager",setup(){const e=k(null),o=x({dialogVisible:!1,pluginConfigUrl:"",limit:1,uploadUrl:"fastcms/api/admin/plugin/install",headers:{Authorization:v.get("token")},tableData:{data:[],total:0,loading:!1,param:{pageNum:1,pageSize:10}}}),n=()=>{j(o.tableData.param).then(a=>{o.tableData.data=a.data.records,o.tableData.total=a.data.total}).catch(()=>{})},l=a=>{N.confirm("\u6B64\u64CD\u4F5C\u5C06\u5378\u8F7D\u63D2\u4EF6, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u5378\u8F7D",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{M(a.pluginId).then(()=>{c.success("\u5378\u8F7D\u6210\u529F"),n()}).catch(i=>{c.error(i.message)})}).catch(()=>{})};let d=null;const _=a=>{d=a,o.dialogVisible=!0},p=()=>{!d||T(d.pluginId).then(a=>{o.pluginConfigUrl=a.data+"?accessToken="+v.get("token");const i=e.value;i&&i.tagName.toUpperCase()==="IFRAME"}).catch(a=>{console.log(a),c.error("\u63D2\u4EF6\u4E0D\u652F\u6301\u914D\u7F6E")})},g=()=>{o.dialogVisible=!1},r=a=>{o.tableData.param.pageSize=a},f=a=>{o.tableData.param.pageNum=a},m=a=>{a.code==200?(c.success("\u5B89\u88C5\u6210\u529F"),n()):c.error(a.message)},h=a=>{c.error("\u5B89\u88C5\u5931\u8D25,"+a)},C=()=>{};return I(()=>{n()}),E({handleClose:g,beforeOnRowConfig:_,onRowConfig:p,onRowUnInstall:l,onHandleSizeChange:r,onHandleCurrentChange:f,initTableData:n,uploadSuccess:m,onHandleUploadError:h,onHandleExceed:C,iframeRef:e},y(o))}},O={class:"mb15"},P=D("\u5B89\u88C5\u63D2\u4EF6"),L=D("\u914D\u7F6E"),q=D("\u5378\u8F7D"),G=["src"];function J(e,o,n,l,d,_){const p=u("el-button"),g=u("el-upload"),r=u("el-table-column"),f=u("el-table"),m=u("el-pagination"),h=u("el-card"),C=u("el-dialog");return V(),H("div",null,[t(h,{shadow:"hover"},{default:s(()=>[z("div",O,[t(g,{action:e.uploadUrl,name:"file",headers:e.headers,"show-file-list":!1,"on-success":l.uploadSuccess,"on-error":l.onHandleUploadError,"on-exceed":l.onHandleExceed,accept:".jar,.zip"},{default:s(()=>[t(p,{class:"mt15",size:"small",type:"primary",icon:"iconfont icon-shuxingtu"},{default:s(()=>[P]),_:1})]),_:1},8,["action","headers","on-success","on-error","on-exceed"])]),t(f,{data:e.tableData.data,stripe:"",style:{width:"100%"}},{default:s(()=>[t(r,{prop:"pluginId",label:"ID","show-overflow-tooltip":""}),t(r,{prop:"pluginClass",label:"\u63D2\u4EF6\u540D\u79F0","show-overflow-tooltip":""}),t(r,{prop:"provider",label:"\u4F5C\u8005","show-overflow-tooltip":""}),t(r,{prop:"pluginState",label:"\u72B6\u6001","show-overflow-tooltip":""}),t(r,{prop:"description",label:"\u63CF\u8FF0","show-overflow-tooltip":""}),t(r,{prop:"path",label:"\u64CD\u4F5C",width:"90"},{default:s(a=>[t(p,{size:"mini",type:"text",onClick:i=>l.beforeOnRowConfig(a.row)},{default:s(()=>[L]),_:2},1032,["onClick"]),t(p,{size:"mini",type:"text",onClick:i=>l.onRowUnInstall(a.row)},{default:s(()=>[q]),_:2},1032,["onClick"])]),_:1})]),_:1},8,["data"]),t(m,{onSizeChange:l.onHandleSizeChange,onCurrentChange:l.onHandleCurrentChange,class:"mt15","pager-count":5,"page-sizes":[10,20,30],"current-page":e.tableData.param.pageNum,"onUpdate:current-page":o[0]||(o[0]=a=>e.tableData.param.pageNum=a),background:"","page-size":e.tableData.param.pageSize,"onUpdate:page-size":o[1]||(o[1]=a=>e.tableData.param.pageSize=a),layout:"total, sizes, prev, pager, next, jumper",total:e.tableData.total},null,8,["onSizeChange","onCurrentChange","current-page","page-size","total"])]),_:1}),t(C,{title:"\u63D2\u4EF6\u914D\u7F6E",fullscreen:"","model-value":e.dialogVisible,"before-close":l.handleClose,onOpened:l.onRowConfig},{default:s(()=>[z("iframe",{src:e.pluginConfigUrl,frameborder:"0",style:{width:"100%",height:"600px"},ref:"iframeRef"},null,8,G)]),_:1},8,["model-value","before-close","onOpened"])])}var X=R(A,[["render",J]]);export{X as default};
