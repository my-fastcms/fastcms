var x=Object.defineProperty;var b=Object.getOwnPropertySymbols;var H=Object.prototype.hasOwnProperty,w=Object.prototype.propertyIsEnumerable;var C=(e,a,t)=>a in e?x(e,a,{enumerable:!0,configurable:!0,writable:!0,value:t}):e[a]=t,D=(e,a)=>{for(var t in a||(a={}))H.call(a,t)&&C(e,t,a[t]);if(b)for(var t of b(a))w.call(a,t)&&C(e,t,a[t]);return e};import{g as F}from"./index.88f679e5.js";import{_ as N,S as T}from"./index.996e2d4a.js";import j from"./detail.990034c9.js";import{r as A,j as R,o as B,t as I,l,m as u,z as s,A as r,b as y,D as i,Z as U,Y as V,Q as p,p as m,y as L,L as M,x as q}from"./vendor.7efd8838.js";const P={name:"attachManager",components:{Detail:j},setup(){const e=A(),a=R({fit:"fill",queryParams:{},showSearch:!0,limit:3,uploadUrl:"https://www.xjd2020.com/fastcms/api/admin/attachment/upload",headers:{Authorization:T.get("token")},tableData:{data:[],total:99,loading:!1,param:{pageNum:1,pageSize:10}}}),t=()=>{F(a.tableData.param).then(n=>{a.tableData.data=n.data.records,a.tableData.total=n.data.total})},o=()=>{t()},_=()=>{y.error("\u4E0A\u4F20\u6587\u4EF6\u6570\u91CF\u4E0D\u80FD\u8D85\u8FC7 "+a.limit+" \u4E2A!")},g=()=>{y.error("\u4E0A\u4F20\u5931\u8D25")};return B(()=>{t()}),D({detailRef:e,initTableData:t,onTableItemClick:n=>{e.value.openDialog(n.id)},onHandleSizeChange:n=>{a.tableData.param.pageSize=n,t()},onHandleCurrentChange:n=>{a.tableData.param.pageNum=n,t()},onHandleExceed:_,onHandleUploadError:g,uploadSuccess:o},I(a))}},Q={class:"list-adapt-container"},Y=q("\u4E0A\u4F20\u9644\u4EF6"),Z={key:0},G=["src","fit"],J={style:{padding:"14px"}};function K(e,a,t,o,_,g){const h=l("el-button"),f=l("el-upload"),d=l("el-card"),n=l("el-col"),z=l("el-row"),E=l("el-empty"),k=l("el-pagination"),S=l("Detail");return i(),u("div",Q,[s(f,{class:"upload-btn",action:e.uploadUrl,name:"files",multiple:"",headers:e.headers,"show-file-list":!1,"on-success":o.uploadSuccess,"on-exceed":o.onHandleExceed,"on-error":o.onHandleUploadError,limit:e.limit},{default:r(()=>[s(h,{size:"small",type:"primary"},{default:r(()=>[Y]),_:1})]),_:1},8,["action","headers","on-success","on-exceed","on-error","limit"]),s(d,{shadow:"hover"},{default:r(()=>[e.tableData.data.length>0?(i(),u("div",Z,[s(z,{gutter:15},{default:r(()=>[(i(!0),u(U,null,V(e.tableData.data,(c,v)=>(i(),p(n,{xs:24,sm:12,md:8,lg:6,xl:4,class:"mb15",key:v,onClick:O=>o.onTableItemClick(c)},{default:r(()=>[s(d,{"body-style":{padding:"0px"}},{default:r(()=>[m("img",{src:c.path,fit:e.fit,class:"image"},null,8,G),m("div",J,[m("span",null,L(c.fileName),1)])]),_:2},1024)]),_:2},1032,["onClick"]))),128))]),_:1})])):(i(),p(E,{key:1,description:"\u6682\u65E0\u6570\u636E"})),e.tableData.data.length>0?(i(),p(k,{key:2,style:{"text-align":"right"},background:"",onSizeChange:o.onHandleSizeChange,onCurrentChange:o.onHandleCurrentChange,"page-sizes":[10,20,30],"current-page":e.tableData.param.pageNum,"page-size":e.tableData.param.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.tableData.total},null,8,["onSizeChange","onCurrentChange","current-page","page-size","total"])):M("",!0)]),_:1}),s(S,{ref:"detailRef",onReloadTable:o.initTableData},null,8,["onReloadTable"])])}var te=N(P,[["render",K],["__scopeId","data-v-9e8da1fc"]]);export{te as default};
