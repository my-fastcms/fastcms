var x=Object.defineProperty;var C=Object.getOwnPropertySymbols;var H=Object.prototype.hasOwnProperty,F=Object.prototype.propertyIsEnumerable;var D=(e,a,t)=>a in e?x(e,a,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[a]=t,y=(e,a)=>{for(var t in a||(a={}))H.call(a,t)&&D(e,t,a[t]);if(C)for(var t of C(a))F.call(a,t)&&D(e,t,a[t]);return e};import{g as N}from"./index.4c52eba7.js";import{_ as T,S as A}from"./index.537f06e5.js";import j from"./detail.926d9e1f.js";import{r as w,j as R,o as B,t as I,l,m as u,z as s,A as r,b as p,D as i,Y as U,X as V,Q as m,p as _,y as L,L as M,x as P}from"./vendor.3e632ab1.js";const q={name:"attachManager",components:{Detail:j},setup(){const e=w(),a=R({fit:"fill",queryParams:{},showSearch:!0,limit:3,uploadUrl:"fastcms/api/admin/attachment/upload",headers:{Authorization:A.get("token")},tableData:{data:[],total:0,loading:!1,param:{pageNum:1,pageSize:10}}}),t=()=>{N(a.tableData.param).then(o=>{a.tableData.data=o.data.records,a.tableData.total=o.data.total})},n=o=>{o.code==200?t():p.error(o.message)},g=()=>{p.error("\u4E0A\u4F20\u6587\u4EF6\u6570\u91CF\u4E0D\u80FD\u8D85\u8FC7 "+a.limit+" \u4E2A!")},f=()=>{p.error("\u4E0A\u4F20\u5931\u8D25")};return B(()=>{t()}),y({detailRef:e,initTableData:t,onTableItemClick:o=>{e.value.openDialog(o.id)},onHandleSizeChange:o=>{a.tableData.param.pageSize=o,t()},onHandleCurrentChange:o=>{a.tableData.param.pageNum=o,t()},onHandleExceed:g,onHandleUploadError:f,uploadSuccess:n},I(a))}},Q={class:"list-adapt-container"},X=P("\u4E0A\u4F20\u9644\u4EF6"),Y={key:0},G=["src","fit"],J={style:{padding:"14px"}};function K(e,a,t,n,g,f){const h=l("el-button"),b=l("el-upload"),d=l("el-card"),o=l("el-col"),z=l("el-row"),E=l("el-empty"),k=l("el-pagination"),S=l("Detail");return i(),u("div",Q,[s(b,{class:"upload-btn",action:e.uploadUrl,name:"files",multiple:"",headers:e.headers,"show-file-list":!1,"on-success":n.uploadSuccess,"on-exceed":n.onHandleExceed,"on-error":n.onHandleUploadError,limit:e.limit},{default:r(()=>[s(h,{size:"small",type:"primary"},{default:r(()=>[X]),_:1})]),_:1},8,["action","headers","on-success","on-exceed","on-error","limit"]),s(d,{shadow:"hover"},{default:r(()=>[e.tableData.data.length>0?(i(),u("div",Y,[s(z,{gutter:15},{default:r(()=>[(i(!0),u(U,null,V(e.tableData.data,(c,v)=>(i(),m(o,{xs:24,sm:12,md:8,lg:6,xl:4,class:"mb15",key:v,onClick:O=>n.onTableItemClick(c)},{default:r(()=>[s(d,{"body-style":{padding:"0px"}},{default:r(()=>[_("img",{src:c.typePath,fit:e.fit,class:"image"},null,8,G),_("div",J,[_("span",null,L(c.fileName),1)])]),_:2},1024)]),_:2},1032,["onClick"]))),128))]),_:1})])):(i(),m(E,{key:1,description:"\u6682\u65E0\u6570\u636E"})),e.tableData.data.length>0?(i(),m(k,{key:2,style:{"text-align":"right"},background:"",onSizeChange:n.onHandleSizeChange,onCurrentChange:n.onHandleCurrentChange,"page-sizes":[10,20,30],"current-page":e.tableData.param.pageNum,"page-size":e.tableData.param.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.tableData.total},null,8,["onSizeChange","onCurrentChange","current-page","page-size","total"])):M("",!0)]),_:1}),s(S,{ref:"detailRef",onReloadTable:n.initTableData},null,8,["onReloadTable"])])}var te=T(q,[["render",K],["__scopeId","data-v-f6f1e594"]]);export{te as default};